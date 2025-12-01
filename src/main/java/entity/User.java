package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This class is an entity representing a customer in the online store.
 * User has username, email, hashed password, balance, billing addresses,
 * previous purchases categories, and a cart.
 */
public class User {
    private String username;
    private String email;
    private int hashedPassword;
    private double balance;
    private int pointsBalance;

    private HashSet<Address> billingAddresses;
    private List<String> previousPurchasesCategories;
    private Cart cart;

    /**
     * Creates a new user when signing up.
     * @param username the name of the user
     * @param email the email of the user
     * @param password the password of the user
     * @param billingAddress the first billing address of the user (single-line)
     * @throws IllegalArgumentException when arguments are incorrect
     */
    public User(String username, String email, String password, String billingAddress){
        if (username == null || username.isEmpty() || username.contains(" ")) {
            throw new IllegalArgumentException("The username cannot be empty or contain spaces!");
        }
        this.username = username;

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("The email cannot be empty!");
        }
        this.email = email;

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("The password cannot be empty!");
        }
        this.hashedPassword = hashPassword(password);

        if (billingAddress == null || billingAddress.isEmpty()) {
            throw new IllegalArgumentException("The billing address cannot be empty!");
        }

        this.billingAddresses = new HashSet<>();
        this.previousPurchasesCategories = new ArrayList<>();

        this.balance = 0;
        this.pointsBalance = 0;

        this.billingAddresses.add(new Address(billingAddress));

        this.cart = new Cart(this.getUsername());
    }

    /**
     * Creates a new user when loading the users data from the database.
     * @param username the name of the user
     * @param email the email of the user
     * @param hashedPassword the hashed password of the user
     * @param balance the balance of the user
     * @param billingAddresses the list of billing addresses of the user
     * @param previousPurchasesCategories the list of categories of the user's previous purchases
     * @param cart the user's cart stored in the database
     * @throws IllegalArgumentException when arguments are incorrect
     */
    public User(String username, String email, int hashedPassword, double balance,
                HashSet<Address> billingAddresses,
                List<String> previousPurchasesCategories,
                Cart cart){

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("The username cannot be empty!");
        }
        this.username = username;

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("The email cannot be empty!");
        }
        this.email = email;

        if (balance < 0) {
            throw new IllegalArgumentException("The balance cannot be negative!");
        }
        this.balance = balance;

        this.hashedPassword = hashedPassword;

        this.billingAddresses = (billingAddresses == null) ? new HashSet<>() : billingAddresses;
        this.previousPurchasesCategories =
                (previousPurchasesCategories == null) ? new ArrayList<>() : previousPurchasesCategories;

        this.pointsBalance = 0;
        this.cart = cart;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    /**
     * Gets the list of billing addresses of the user.
     * The list elements are Address entities, not plain strings.
     */
    public List<Address> getBillingAddresses(){
        return new ArrayList<>(this.billingAddresses);
    }

    public List<String> getPreviousPurchasesCategories(){
        return this.previousPurchasesCategories;
    }

    public double getBalance(){
        return this.balance;
    }

    public int getPointsBalance(){
        return this.pointsBalance;
    }

    public Cart getCart(){
        return this.cart;
    }

    /**
     * Gets the hashed password of the user when prompted by a security code
     * @param securityCode the security code needed to access the hashed password of the user
     * @throws IllegalArgumentException when the security code is incorrect
     * @return hashedPassword of the user
     */
    public int getHashedPassword(int securityCode) throws IllegalArgumentException{
        if (securityCode != 56734822) {
            throw new IllegalArgumentException("The security code is invalid!");
        }
        return this.hashedPassword;
    }

    /**
     * Adds the new purchase category to the list of the categories of the user's previous purchases
     * @param category the category of the user's purchase
     * @throws IllegalArgumentException when the category is empty
     */
    public void addCategory(String category){
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("The category cannot be empty");
        }
        this.previousPurchasesCategories.add(category);
    }

    /**
     * Adds a new address (as a String) to the list of the user's billing addresses.
     * Internally this is wrapped into an Address entity.
     * @param address the new billing address of the user
     * @throws IllegalArgumentException when the address is empty
     */
    public void addAddress(String address){
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("The address cannot be empty");
        }
        this.billingAddresses.add(new Address(address));
    }

    /**
     * Adds a new Address entity directly to the list of billing addresses.
     * This overload is useful when use cases already work with Address objects.
     * @param address the new billing address of the user
     * @throws IllegalArgumentException when address is null
     */
    public void addAddress(Address address){
        if (address == null) {
            throw new IllegalArgumentException("The address cannot be null");
        }
        this.billingAddresses.add(address);
    }

    /**
     * Adds an amount of money to the balance
     * @param amount the amount of money to be added to the balance
     * @throws IllegalArgumentException when the amount is negative
     */
    public void addBalance(double amount) throws IllegalArgumentException{
        if (amount < 0) {
            throw new IllegalArgumentException("The amount cannot be negative!");
        }
        this.balance += amount;
    }

    /**
     * Removes an amount of money from the balance
     * @param amount the amount of money to be removed from the balance
     * @throws IllegalArgumentException when the amount is negative
     */
    public void removeBalance(double amount) throws IllegalArgumentException{
        if (amount < 0) {
            throw new IllegalArgumentException("The amount cannot be negative!");
        }
        this.balance -= amount;
    }

    /**
     * Adds an amount of points to the user's account.
     * @param amount the amount of points to be added to the balance
     * @throws IllegalArgumentException when the amount is negative
     */
    public void addPointsBalance(int amount) throws IllegalArgumentException{
        if (amount < 0) {
            throw new IllegalArgumentException("The amount cannot be negative!");
        }
        this.pointsBalance += amount;
    }

    /**
     * Removes an amount of points from the user's account.
     * @param amount the amount of points to be removed from the balance
     * @throws IllegalArgumentException when the amount is negative
     */
    public void removePointsBalance(int amount) throws IllegalArgumentException{
        if (amount < 0) {
            throw new IllegalArgumentException("The amount cannot be negative!");
        }
        this.pointsBalance -= amount;
    }

    /**
     * Checks the password of the user and another password to see whether they are equal or not
     * @param password the other password that the password of the user is going to be compared with
     * @throws IllegalArgumentException when the input password is empty
     */
    public boolean checkPassword(String password) throws IllegalArgumentException{
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("The password cannot be empty!");
        }
        return this.hashedPassword == hashPassword(password);
    }

    private int hashPassword(String password){
        return password.hashCode();
    }

    public boolean removeAddressById(String addressId) {
        if (addressId == null) {
            return false;
        }

        return billingAddresses.removeIf(a -> addressId.equals(a.getId()));
    }
}