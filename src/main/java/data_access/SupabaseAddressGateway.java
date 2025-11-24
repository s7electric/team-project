package data_access;

import entity.Address;
import entity.User;
import use_case.manage_address.UserDataAccessInterface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Supabase-backed address gateway. Stores and fetches addresses via REST and maps them onto the User entity.
 * Notes:
 * - Expects a Supabase table "addresses" with columns (id, user_id uuid, line1, line2, city, state, zip, country, is_default bool).
 */
public class SupabaseAddressGateway implements UserDataAccessInterface {

    private final String baseUrl;
    private final String anonKey;
    private final SupabaseSession session;
    private final HttpClient client = HttpClient.newHttpClient();

    public SupabaseAddressGateway(String baseUrl, String anonKey, SupabaseSession session) {
        this.baseUrl = baseUrl;
        this.anonKey = anonKey;
        this.session = session;
    }

    @Override
    public User getUser(String username) {
        // Fetch addresses for the current user; we ignore the username param and use the session user id.
        String userId = session.getCurrentUserId();
        if (userId == null || baseUrl == null || anonKey == null) {
            return null;
        }
        List<Address> addresses = fetchAddresses(userId);
        // We do not have full user fields here; return a minimal User with addresses set.
        User user = new User(username, username, "placeholder", "placeholder");
        user.getBillingAddresses().clear();
        for (Address a : addresses) {
            user.addAddress(a);
        }
        return user;
    }

    ///  Currently wrong
    @Override
    public void saveUser(User user) {
        // Upsert all addresses for the current user. This simplistic implementation deletes all then inserts new ones.
        String userId = session.getCurrentUserId();
        if (user == null || userId == null || baseUrl == null || anonKey == null) {
            return;
        }
        deleteAllAddresses(userId);
        for (Address a : user.getBillingAddresses()) {
            insertAddress(userId, a);
        }
    }

    private List<Address> fetchAddresses(String userId) {
        List<Address> result = new ArrayList<>();
        String url = baseUrl + "/rest/v1/addresses?user_id=eq." + userId + "&select=id,line1,line2,city,state,zip,country,is_default";
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", anonKey)
                .header("Authorization", "Bearer " + session.getAccessToken())
                .build();
        try {
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
                String body = resp.body();
                // Very simple parsing: look for occurrences of "line1":"...","city":"..."
                Pattern p = Pattern.compile("\"id\"\\s*:\\s*\"([^\"]+)\".*?\"line1\"\\s*:\\s*\"([^\"]*)\".*?\"line2\"\\s*:\\s*\"([^\"]*)\".*?\"city\"\\s*:\\s*\"([^\"]*)\".*?\"state\"\\s*:\\s*\"([^\"]*)\".*?\"zip\"\\s*:\\s*\"([^\"]*)\".*?\"country\"\\s*:\\s*\"([^\"]*)\".*?\"is_default\"\\s*:\\s*(true|false)", Pattern.DOTALL);
                Matcher m = p.matcher(body);
                while (m.find()) {
                    String id = m.group(1);
                    String line1 = m.group(2);
                    String line2 = m.group(3);
                    String city = m.group(4);
                    String state = m.group(5);
                    String zip = m.group(6);
                    String country = m.group(7);
                    boolean isDefault = Boolean.parseBoolean(m.group(8));
                    Address a = new Address(id, line1, line2, city, state, zip, country, isDefault, isDefault);
                    result.add(a);
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return result;
    }

    private void deleteAllAddresses(String userId) {
        String url = baseUrl + "/rest/v1/addresses?user_id=eq." + userId;
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", anonKey)
                .header("Authorization", "Bearer " + session.getAccessToken())
                .header("Prefer", "return=representation")
                .DELETE()
                .build();
        try {
            client.send(req, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void insertAddress(String userId, Address address) {
        String url = baseUrl + "/rest/v1/addresses";
        String body = "{\"user_id\":\"" + escape(userId) + "\"," +
                "\"line1\":\"" + escape(address.getLine1()) + "\"," +
                "\"line2\":\"" + escape(address.getLine2()) + "\"," +
                "\"city\":\"" + escape(address.getCity()) + "\"," +
                "\"provinceOrState\":\"" + escape(address.getProvinceOrState()) + "\"," +
                "\"zip\":\"" + escape(address.getPostalCode()) + "\"," +
                "\"country\":\"" + escape(address.getCountry()) + "\"," +
                "\"is_default\":" + address.isDefaultShipping() + "}";
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", anonKey)
                .header("Authorization", "Bearer " + session.getAccessToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();
        try {
            client.send(req, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String escape(String val) {
        return val.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
