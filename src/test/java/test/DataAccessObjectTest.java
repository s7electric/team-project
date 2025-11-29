package test;

import entity.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import data_access.*;

public class DataAccessObjectTest {
    public static void main(String[] args) {
        DataAccessObject dao = new DataAccessObject();

        Address address = makeAddress();
        User Jeffery = makeJeffery();
        makeProduct(Jeffery);

        System.out.println("Post Jeffery test:");
        if (dao.checkUserExists("Jeffery") == false) {
            dao.createUser(Jeffery);
            boolean created = dao.checkUserExists("Jeffery");
            System.out.println("User created successfully: " + created);
        } else {
            System.out.println("User already exists in the database.");
        }

        System.out.println("Post address test:");
        if (dao.checkIfAddressExists(address) == false) {
            boolean success = dao.postAddress(address);
            System.out.println("Address posted successfully: " + success);
        } else {
            System.out.println("Address already exists in the database.");
        }

        System.out.println("Get Jeffery test:");
        User retrievedUser = dao.getUser("Jeffery");
        if (retrievedUser != null) {
            System.out.println("Retrieved Jeffery: " + retrievedUser.getUsername());
        } else {
            System.out.println("User not found.");
        }

        System.out.println("Get address test:");
        HashSet<Address> retrievedAddresses = dao.getAddresses("Jeffery");
        if (retrievedAddresses != null) {
            for (Address retrievedAddress : retrievedAddresses) {
                System.out.println("Retrieved address: " + retrievedAddress.toSingleLine());
            }
        } else {
            System.out.println("No addresses found.");
        }

        System.out.println("Get product test:");
        Product retrievedProduct = dao.getProduct("b6e1362a-c189-4e81-baed-f5489a4210d6");
        if (retrievedProduct != null) {
            System.out.println("Retrieved product: " + retrievedProduct.getName());
        } else {
            System.out.println("Product not found.");
        }

        System.out.println("Get all products test:");
        List<Product> products = dao.getAllProducts();
        for (Product p : products) {
            System.out.println("Product: " + p.getName() + ", Price: " + p.getPrice());
        }

        System.out.println("Get cart test:");
        Cart cart = dao.getCart("82b086aa-e589-45df-9fd9-c446f1b25728");
        if (cart != null) {
            System.out.println("Retrieved cart for owner: " + cart.getOwnerName());
            for (CartItem item : cart.getProducts().values()) {
                System.out.println("Product: " + item.getProduct().getName() + ", Quantity: " + item.getQuantity());
            }
        } else {
            System.out.println("Cart not found.");
        }

        // System.out.println("Delete user test:");
        // try {Thread.sleep(20000);} catch (InterruptedException e) {}
        // System.out.println("User exists before deletion: " + dao.checkUserExists("Jeffery"));
        // dao.deleteUser("Jeffery");
        // boolean existsAfterDeletion = dao.checkUserExists("Jeffery");
        // System.out.println("User exists after deletion: " + existsAfterDeletion);

        System.out.println("Add to cart test:");
        User user = dao.getUser("Jim");
        System.out.println("Items in old cart: ");
        Cart oldCart = dao.getCartByUsername("Jim");
        if (oldCart != null) {
            for (CartItem item : oldCart.getProducts().values()) {
                System.out.println("Product: " + item.getProduct().getName() + ", Quantity: " + item.getQuantity());
            }
        } else {
            System.out.println("Cart not found.");
        }
        dao.addToCart(user, "b6e1362a-c189-4e81-baed-f5489a4210d6", 2);
        Cart updatedCart = dao.getCartByUsername("Jim");
        if (updatedCart != null) {
            System.out.println("Updated cart for owner: " + updatedCart.getOwnerName());
            for (CartItem item : updatedCart.getProducts().values()) {
                System.out.println("Product: " + item.getProduct().getName() + ", Quantity: " + item.getQuantity());
            }
        } else {
            System.out.println("Cart not found.");
        }
        
    }

    private static Product makeProduct(User Jeffery) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(5);
        Product product = new Product(
            "Sample Product",
            10.5,
            "b6e1362a-c189-4e81-baed-f5489a4210d6",
            "https://i.imgur.com/v2mZTR5.jpeg",
            Jeffery,
            "food",
            5.0,
            scores
        );
        return product;
    }

    private static User makeJeffery() {
        User Jeffery = new User(
            "Jeffery", 
        "johndoe@gmail.com",
        "12345",
        new Address(
            "Jeffery",
            "123 Main St",
            "Apt 4B",
            "New York",
            "NY",
            "10001",
            "USA",
            true,
            true
        ).toSingleLine()
        // "123 Main St, Apt 4B, New York, NY, 10001, USA"
        );
        return Jeffery;
    }

    private static Address makeAddress() {
        Address address = new Address(
            "Jeffery",
            "123 Main St",
            "Apt 4B",
            "New York",
            "NY",
            "10001",
            "USA",
            true,
            true
        );
        return address;
    }
}
