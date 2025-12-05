package data_access;
import entity.*;
import java.util.List;

public interface DataAccessPlan {
    
    // User-related methods

    /*
     * Creates a new user in the data storage.
     * @param user the user to be created
     */
    void createUser(User user);

    /*
     * Retrieves a user by their username.
     * @param username the username of the user to retrieve
     * @return the User object if found, null otherwise
     */
    User getUserByUsername(String username);

    /*
     * Updates the information of an existing user.
     * @param user the user object with updated information
     * @param username the username of the user to update
     */
    void updateUser(User user, String username);

    /*
     * Deletes a user by their username.
     * @param username the username of the user to delete
     */
    void deleteUser(String username);

    /*
     * Retrieves all users from the data storage.
     * @return a list of all User objects
     */
    List<User> getAllUsers();


    // Product-related methods

    /*
     * Creates a new product in the data storage.
     * @param product the product to be created
     */
    void createProduct(Product product);

    /* Retrieves a product by its ID.
     * @param productId the ID of the product to retrieve
     * @return the Product object if found, null otherwise
     */
    Product getProductById(int productId);

    /* Updates the information of an existing product.
     * @param product the product object with updated information
     * @param productId the ID of the product to update
     */
    void updateProduct(Product product, int productId);

    /* Deletes a product by its ID.
     * @param productId the ID of the product to delete
     */
    void deleteProduct(int productId);

    /* Retrieves all products from the data storage.
     * @return a list of all Product objects
     */
    List<Product> getAllProducts();

    
    // Order-related methods

    /* Creates a new order in the data storage.
     * @param order the order to be created
     */
    void createOrder(Order order);

    /* Retrieves an order by its ID.
     * @param orderId the ID of the order to retrieve
     * @return the Order object if found, null otherwise
     */
    Order getOrderById(int orderId);

    /* Updates the information of an existing order.
     * @param order the order object with updated information
     * @param orderId the ID of the order to update
     */
    void updateOrder(Order order, int orderId);

    /* Deletes an order by its ID.
     * @param orderId the ID of the order to delete
     */
    void deleteOrder(int orderId);

    /* Retrieves all orders for a specific user by their username.
     * @param username the username of the user whose orders to retrieve
     * @return a list of Order objects associated with the user
     */
    List<Order> getOrdersByUsername(String username);


    // Cart-related methods

    /* Adds products to the cart for a specific user.
     * @param username the username of the user whose cart to update
     * @param products the list of products to set in the cart
     */
    void addToCart(String username, List<Product> products);

    /* Retrieves all products in the cart for a specific user by their username.
     * @param username the username of the user whose cart products to retrieve
     * @return a list of Product objects in the user's cart
     */
    Cart getCartByUsername(String username);

    /* Removes products from the cart for a specific user.
     * @param username the username of the user whose cart to update
     * @param products the list of products to remove from the cart
     */
    void removeFromCart(String username, List<Product> products);
}