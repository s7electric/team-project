package data_access;

import use_case.login.LoginUserDataAccessInterface;
import use_case.sign_up.SignUpDataAccessInterface;
import entity.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Supabase-backed authentication gateway for login.
 * Uses Supabase email/password auth and stores the resulting access token in the shared session.
 */
public class SupabaseAuthGateway implements LoginUserDataAccessInterface, SignUpDataAccessInterface {

    private final String baseUrl;
    private final String anonKey;
    private final SupabaseSession session;
    private final HttpClient client = HttpClient.newHttpClient();

    public SupabaseAuthGateway(String baseUrl, String anonKey, SupabaseSession session) {
        this.baseUrl = baseUrl;
        this.anonKey = anonKey;
        this.session = session;
    }

    @Override
    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        if (baseUrl == null || baseUrl.isBlank() || anonKey == null || anonKey.isBlank()) {
            return false;
        }

        String body = "{\"email\":\"" + escape(username) + "\",\"password\":\"" + escape(password) + "\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/auth/v1/token?grant_type=password"))
                .header("apikey", anonKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                String token = extractField(response.body(), "access_token");
                String userId = extractUserId(response.body());
                if (token != null) {
                    session.setAccessToken(token);
                    // Try to fetch profile username; fallback to email input.
                    String profileUsername = fetchProfileUsername(token, userId);
                    session.setCurrentUsername(profileUsername != null ? profileUsername : username);
                    session.setCurrentUserId(userId);
                    return true;
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        return false;
    }

    @Override
    public String getCurrentUsername() {
        return session.getCurrentUsername();
    }

    @Override
    public void createUser(User user, String rawPassword) {
        if (user == null || rawPassword == null || baseUrl == null || anonKey == null) {
            return;
        }
        String body = "{\"email\":\"" + escape(user.getEmail()) + "\",\"password\":\"" + escape(rawPassword) + "\",\"data\":{\"username\":\"" + escape(user.getUsername()) + "\"}}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/auth/v1/signup"))
                .header("apikey", anonKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                String token = extractField(response.body(), "access_token");
                String userId = extractUserId(response.body());
                session.setAccessToken(token);
                session.setCurrentUsername(user.getUsername());
                session.setCurrentUserId(userId);
                createProfile(userId, user.getUsername(), token);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean checkUserExists(String username) {
        // Supabase auth does not expose a simple "exists" check via REST without RLS tweaks.
        // To keep flow moving, we conservatively return false (try to create) and rely on signup error handling.
        return false;
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    /**
     * Minimal JSON extractor for flat string fields using regex.
     */
    private String extractField(String json, String key) {
        Pattern p = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    private String extractUserId(String json) {
        Pattern p = Pattern.compile("\"user\"\\s*:\\s*\\{[^}]*\"id\"\\s*:\\s*\"([^\"]+)\"");
        Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    /**
     * Inserts a row into the profiles table for the newly created user.
     */
    private void createProfile(String userId, String username, String token) {
        if (userId == null || token == null) {
            return;
        }
        String url = baseUrl + "/rest/v1/profiles";
        String body = "{\"id\":\"" + escape(userId) + "\",\"username\":\"" + escape(username) + "\",\"balance\":0,\"points_balance\":0}";
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", anonKey)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
        try {
            client.send(req, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String fetchProfileUsername(String token, String userId) {
        if (userId == null || token == null) {
            return null;
        }
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/rest/v1/profiles?select=username&id=eq." + userId))
                    .header("apikey", anonKey)
                    .header("Authorization", "Bearer " + token)
                    .build();
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
                Pattern p = Pattern.compile("\"username\"\\s*:\\s*\"([^\"]+)\"");
                Matcher m = p.matcher(resp.body());
                if (m.find()) {
                    return m.group(1);
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
