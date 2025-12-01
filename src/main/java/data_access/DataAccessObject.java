package data_access;

import use_case.add_to_cart.AddToCartProductDataAccessInterface;
import use_case.add_to_cart.AddToCartUserDataAccessInterface;
import use_case.checkout.CheckoutDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.open_product.OpenProductProductDataAccessInterface;
import use_case.filter.FilterDataAccessInterface;
import use_case.homepage.AddFundsDataAccessInterface;
import use_case.manage_address.UserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.make_listing.MakeListingDataAccessInterface;
import use_case.search.SearchDataAccessInterface;
import use_case.sign_up.SignUpDataAccessInterface;
import entity.Address;
import entity.Cart;
import entity.Order;
import entity.Product;
import entity.User;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;

public class DataAccessObject implements
    AddToCartUserDataAccessInterface,
        AddToCartProductDataAccessInterface,
    FilterDataAccessInterface,
    LoginUserDataAccessInterface,
    UserDataAccessInterface,
    OpenProductProductDataAccessInterface,
    SearchDataAccessInterface,
    LogoutUserDataAccessInterface,
    SignUpDataAccessInterface, 
    AddFundsDataAccessInterface,
    MakeListingDataAccessInterface {

        private final String URL1 = "https://xlez-ocau-8ty9.n2.xano.io/api:BftqpNiF";
        private final String URL2 = "https://xlez-ocau-8ty9.n2.xano.io/api:vu2PKIfe";

        // Tracks who is currently logged in so other flows (logout, manage address, etc.) can gate correctly.
        private String currentUsername;

        /* Helper methods */

        public void deleteUser(String username) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(URL2 + "/user?username=" + username)
                .delete()
                .build();
            try {
                client.newCall(request).execute();
            }
            catch (IOException e) {
            }
        }

        /**
         * 
         * @param address Address object to be posted to the server
         * @return true if successful, false otherwise
         */
        public boolean postAddress(Address address) {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("recipientName", address.getRecipientName());
                jsonBody.put("line1", address.getLine1());
                jsonBody.put("line2", address.getLine2());
                jsonBody.put("city", address.getCity());
                jsonBody.put("provinceOrState", address.getProvinceOrState());
                jsonBody.put("postalCode", address.getPostalCode());
                jsonBody.put("country", address.getCountry());
                jsonBody.put("defaultBilling", address.isDefaultBilling());
                jsonBody.put("defaultShipping", address.isDefaultShipping());
                Request request = new Request.Builder()
                    .url(URL2 + "/address")
                    .post(okhttp3.RequestBody.create(jsonBody.toString(), okhttp3.MediaType.parse("application/json")))
                    .build();
                Response response = client.newCall(request).execute();
                return response.isSuccessful();
            }
            catch (IOException e) {
                return false;
                }
            catch (JSONException e) {
                return false;
            }
        
        }

        public HashSet<Address> getAddresses(String username) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(URL2 + "/get_address?username=" + username)
                .build();
            try {
                Response response = client.newCall(request).execute();
                JSONArray jsonArray = new JSONArray(response.body().string());
                HashSet<Address> addresses = new HashSet<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonAddress = jsonArray.getJSONObject(i);
                    Address address = new Address(
                        jsonAddress.getString("id"),
                        jsonAddress.getString("recipientName"),
                        jsonAddress.getString("line1"),
                        jsonAddress.getString("line2"),
                        jsonAddress.getString("city"),
                        jsonAddress.getString("provinceOrState"),
                        jsonAddress.getString("postalCode"),
                        jsonAddress.getString("country"),
                        jsonAddress.getBoolean("defaultBilling"),
                        jsonAddress.getBoolean("defaultShipping")
                    );
                    addresses.add(address);
                }
                return addresses;
            }
            catch (IOException e) {
                return null;
            }
            catch (JSONException e) {
                return null;
            }
        }

        public boolean checkIfAddressExists(Address address) {
            HashSet<Address> addresses = getAddresses(address.getRecipientName());
            for (Address addr : addresses) {
                if (addr.toSingleLine().equals(address.toSingleLine())) {
                    return true;
                }
            }
            return false;
        }

        public Cart getCart(String cartUUID) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(URL2 + "/get_cart?cartUUID=" + cartUUID)
                .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject jsonBody = new JSONObject(response.body().string());
                // System.out.println(jsonBody.toString());
                Cart cart = new Cart(
                    jsonBody.getString("owner"),
                    jsonBody.getString("id")
                );
                JSONArray products = jsonBody.getJSONObject("products_qty").getJSONArray("products");
                for (int i = 0; i < products.length(); i++) {
                    cart.addProduct(
                        getProduct(
                            products.getJSONObject(i).getString("productUUID")
                        ),
                        products.getJSONObject(i).getInt("quantity")
                    );
                }
                return cart;
            }
            catch (IOException e) {
                return null;
            }
            catch (JSONException e) {
                return null;
            }
        }

        public Cart getCartByUsername(String username) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(URL2 + "/get_cart_by_username?username=" + username)
                .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject jsonBody = new JSONObject(response.body().string());
                // System.out.println(jsonBody.toString());
                Cart cart = new Cart(
                    jsonBody.getString("owner"),
                    jsonBody.getString("id")
                );
                JSONArray products = jsonBody.getJSONObject("products_qty").getJSONArray("products");
                for (int i = 0; i < products.length(); i++) {
                    cart.addProduct(
                        getProduct(
                            products.getJSONObject(i).getString("productUUID")
                        ),
                        products.getJSONObject(i).getInt("quantity")
                    );
                }
                return cart;
            }
            catch (IOException e) {
                return null;
            }
            catch (JSONException e) {
                return null;
            }
        }

        public void deleteCart(String cartUUID) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(URL2 + "/cart?cartUUID=" + cartUUID)
                .delete()
                .build();
            try {
                client.newCall(request).execute();
            }
            catch (IOException e) {
            }
        }
        
        /* User related methods */
    
        @Override
        public User getUser(String username) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(URL2 + "/get_user?username=" + username)
                .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject jsonBody = new JSONObject(response.body().string());
                User user = new User(
                    jsonBody.getString("username"), 
                    jsonBody.getString("email"), 
                    jsonBody.getInt("hashedPassword"), 
                    jsonBody.getDouble("balance"), 
                    new HashSet<Address>(), 
                    new ArrayList<String>(),
                    getCart(jsonBody.getString("cartUUID"))
                );
                if (getAddresses(username) != null) {
                    for (Address address : getAddresses(username)) {
                        user.addAddress(address);
                    }
                }
                if (jsonBody.getJSONArray("previousPurchasesCategories").length() > 0) {
                    for (int i = 0; i < jsonBody.getJSONArray("previousPurchasesCategories").length(); i++) {
                        user.addCategory(jsonBody.getJSONArray("previousPurchasesCategories").getString(i));
                    }
                }

                return user;
            }
            catch (IOException e) {
                return null;
            }
            catch (JSONException e) {
                return null;
            }
        }

        @Override
        public User getUserData(String username) {
            return getUser(username);
        }

        @Override
        public void createUser(User user) {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("username", user.getUsername());
                jsonBody.put("email", user.getEmail());
                jsonBody.put("hashedPassword", user.getHashedPassword(56734822));
                jsonBody.put("balance", user.getBalance());
                JSONArray addressesArray = new JSONArray();

                for (Address address : user.getBillingAddresses()) {
                    if (!checkIfAddressExists(address)) {
                        postAddress(address);
                    }
                    addressesArray.put(address.getId());
                }
                jsonBody.put("billingAddresses", addressesArray);
                JSONArray categoriesArray = new JSONArray();
                for (String category : user.getPreviousPurchasesCategories()) {
                    categoriesArray.put(category);
                }
                jsonBody.put("previousPurchasesCategories", categoriesArray);
                jsonBody.put("cartUUID", user.getCart().getCartUUID());

                Request request = new Request.Builder()
                    .url(URL2 + "/user?username=" + user.getUsername())
                    .post(okhttp3.RequestBody.create(jsonBody.toString(), okhttp3.MediaType.parse("application/json")))
                    .build();
                client.newCall(request).execute();
            }
            catch (IOException e) {
            }
            catch (JSONException e) {
            }
        }

        @Override
        public boolean checkUserExists(String username) {
            if (getUser(username) != null) {
                return true;
            }
            return false;
        }

        @Override
        public User get(String username) {
            return getUser(username);
        }

        @Override
        public void setCurrentUsername(String username) {
            this.currentUsername = (username == null || username.isBlank()) ? null : username.trim();
        }

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }

        @Override
        public boolean existsByName(String username) {
            return checkUserExists(username);
        }

        @Override
        public void saveUser(User user) {
            deleteUser(user.getUsername());
            createUser(user);
        }

        /* Product related methods */

        // @Override
        public Product getProduct(String productUUID) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(URL1 + "/get_product?productUUID=" + productUUID)
                .build();
            // System.out.println(request.url());
            try {
                Response response = client.newCall(request).execute();
                JSONObject jsonBody = new JSONObject(response.body().string());
                // System.out.println(jsonBody.toString());
                Product product = new Product(
                    jsonBody.getString("name"), 
                    jsonBody.getDouble("price"), 
                    productUUID,
                    jsonBody.getString("image_base64"),
                    getUser(jsonBody.getString("seller_name")), 
                    jsonBody.getString("category"),
                    jsonBody.getDouble("average_review_score"),
                    new ArrayList<Integer>()
                );
                for (int i = 0; i < jsonBody.getJSONArray("review_scores").length(); i++) {
                    product.addScore(Integer.valueOf(
                        jsonBody.getJSONArray("review_scores").getInt(i)
                    ));
                }
                return product;
            }
            catch (IOException e) {
                return null;
            }
            catch (JSONException e) {
                return null;
            }
        }

        @Override
        public List<Product> getAllProducts() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(URL1 + "/product")
                .build();
            try {
                Response response = client.newCall(request).execute();
                JSONArray jsonArray = new JSONArray(response.body().string());
                List<Product> products = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonProduct = jsonArray.getJSONObject(i);
                    Product product = new Product(
                    jsonProduct.getString("name"), 
                    jsonProduct.getDouble("price"), 
                    jsonProduct.getString("id"),
                    jsonProduct.getString("image_base64"),
                    getUser(jsonProduct.getString("seller_name")), 
                    jsonProduct.getString("category"),
                    jsonProduct.getDouble("average_review_score"),
                    new ArrayList<Integer>()
                    );
                    for (int j = 0; j < jsonProduct.getJSONArray("review_scores").length(); j++) {
                        product.addScore(Integer.valueOf(
                            jsonProduct.getJSONArray("review_scores").getInt(j)
                        ));
                    }
                    products.add(product);
                }
                return products;
            }
            catch (IOException e) {
                return null;
            }
            catch (JSONException e) {
                return null;
            }
        }
        
        @Override
        public void postListing(Product product) {
            postProduct(product);
        }
        public void postProduct(Product product) {
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("name", product.getName());
                jsonBody.put("price", product.getPrice());
                jsonBody.put("image_base64", product.getimageBase64());
                jsonBody.put("seller_name", product.getUser().getUsername());
                jsonBody.put("category", product.getCategory());
                jsonBody.put("average_review_score", product.getAverageReviewScore());
                JSONArray scoresArray = new JSONArray();
                for (Integer score : product.getScores()) {
                    scoresArray.put(score);
                }
                jsonBody.put("review_scores", scoresArray);
                Request request = new Request.Builder()
                    .url(URL1 + "/product")
                    .post(okhttp3.RequestBody.create(jsonBody.toString(), okhttp3.MediaType.parse("application/json")))
                    .build();
                client.newCall(request).execute();
            }
            catch (IOException e) {
            }
            catch (JSONException e) {
            }
        }

        /* Cart related methods */

        // @Override
        public void addToCart(User user, String productUUID, Integer quantity) {
            Cart cart = getCartByUsername(user.getUsername());
            Product product = getProduct(productUUID);
            if (cart != null && product != null) {
                cart.addProduct(product, quantity);
            }
    
            OkHttpClient client = new OkHttpClient();
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("username", user.getUsername());
                jsonBody.put("cartUUID", cart.getCartUUID());
                JSONArray products = new JSONArray();
                for (String uuid : cart.getProducts().keySet()) {
                    products.put(new JSONObject()
                        .put("quantity", cart.getProducts().get(uuid))
                        .put("productUUID", uuid)
                    );
                }
                jsonBody.put("products_qty", new JSONObject().put("products", products));
                Request request = new Request.Builder()
                    .url(URL2 + "/cart")
                    .post(okhttp3.RequestBody.create(jsonBody.toString(), okhttp3.MediaType.parse("application/json")))
                    .build();
                client.newCall(request).execute();
            }
            catch (JSONException e) {
            }
            catch (IOException e) {
            }
        }

        @Override
        public void addFunds(String username, double amount) {
            User user = getUser(username);
            if (user != null) {
                user.addBalance(amount);
                saveUser(user);
            }
        }

//        @Override
//        public void saveOrder(Order order) {
//            // Not implemented
//            return;
//        }



}
