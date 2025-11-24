package data_access;

import entity.Product;
import use_case.filter.FilterDataAccessInterface;
import use_case.open_product.OpenProductProductDataAccessInterface;
import use_case.search.SearchDataAccessInterface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Supabase-backed product gateway for list and get-by-id operations.
 * Expects a products table with columns: id (int), name, description, price (numeric), image_url, category, active.
 */
public class SupabaseProductGateway implements FilterDataAccessInterface, SearchDataAccessInterface, OpenProductProductDataAccessInterface {

    private final String baseUrl;
    private final String anonKey;
    private final SupabaseSession session;
    private final HttpClient client = HttpClient.newHttpClient();

    public SupabaseProductGateway(String baseUrl, String anonKey, SupabaseSession session) {
        this.baseUrl = baseUrl;
        this.anonKey = anonKey;
        this.session = session;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> result = new ArrayList<>();
        if (baseUrl == null || anonKey == null) {
            return result;
        }
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/rest/v1/products?select=id,name,price,image_url,category,description"))
                .header("apikey", anonKey)
                .build();
        try {
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
                result = parseProducts(resp.body());
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return result;
    }

    @Override
    public Product getProduct(int productId) {
        if (baseUrl == null || anonKey == null) {
            return null;
        }
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/rest/v1/products?id=eq." + productId + "&select=id,name,price,image_url,category,description"))
                .header("apikey", anonKey)
                .build();
        try {
            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
                List<Product> parsed = parseProducts(resp.body());
                if (!parsed.isEmpty()) {
                    return parsed.get(0);
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    private List<Product> parseProducts(String json) {
        List<Product> list = new ArrayList<>();
        // Tolerant parsing: iterate over each object and extract fields individually, allowing null/empty values.
        Pattern objPattern = Pattern.compile("\\{[^}]*\\}");
        Matcher objMatcher = objPattern.matcher(json);
        while (objMatcher.find()) {
            String obj = objMatcher.group();
            Integer id = extractInt(obj, "id");
            String name = extractString(obj, "name");
            Double price = extractDouble(obj, "price");
            String imageUrl = extractString(obj, "image_url");
            String category = extractString(obj, "category");
            String description = extractString(obj, "description");

            if (id == null || name == null || price == null) {
                continue;
            }
            if (imageUrl == null) imageUrl = "";
            if (category == null) category = "";
            Product product = new Product(name, price, id, imageUrl, null, category);
            list.add(product);
        }
        return list;
    }

    private String extractString(String obj, String key) {
        Pattern p = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*(\"([^\"]*)\"|null)", Pattern.DOTALL);
        Matcher m = p.matcher(obj);
        if (m.find()) {
            return m.group(2); // group 2 is inside quotes
        }
        return null;
    }

    private Integer extractInt(String obj, String key) {
        Pattern p = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*(\\d+)");
        Matcher m = p.matcher(obj);
        if (m.find()) {
            try {
                return Integer.parseInt(m.group(1));
            } catch (NumberFormatException ignored) {}
        }
        return null;
    }

    private Double extractDouble(String obj, String key) {
        Pattern p = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*([0-9eE+\\-\\.]+)");
        Matcher m = p.matcher(obj);
        if (m.find()) {
            try {
                return Double.parseDouble(m.group(1));
            } catch (NumberFormatException ignored) {}
        }
        return null;
    }
}
