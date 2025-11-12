/*
 * This class is the interface for data access operations related to entities such as User, Product, Cart, and Order.
 * It defines methods for creating, reading, updating, and deleting these entities in the data storage system.
 */
package data_access;
import entity.Cart;
import entity.User;
import entity.Product;
import entity.Order;
import java.util.List;

public interface DataAccessInterface {
    // User-related methods
    void createUser(User user);
    User getUserByUsername(String username);
    void updateUser(User user);
    // void deleteUser(String username);

    // Product-related methods
    void createProduct(Product product);
    Product getProductById(int productId);
    void updateProduct(Product product);
    void deleteProduct(int productId);
    List<Product> getAllProducts();

    // Order-related methods
    Order getOrderById(int orderId);
    void updateOrder(Order order);
    void deleteOrder(int orderId);
    List<Order> getOrdersByUsername(String username);

    // Cart-related methods
    Cart getCartByUserId(int username);
    void updateCart(String username, List<Product> products);
    List<Product> getCartByUsername(String username);
}