package entity;

import java.util.UUID;

/**
 * This class is an entity representing a postal address for a user
 * in the online store. It can be used as a billing address or
 * shipping address (or both).
 */
public class Address {

    private final String id;

    private String recipientName;
    private String line1;
    private String line2;
    private String city;
    private String provinceOrState;
    private String postalCode;
    private String country;

    private boolean defaultBilling;
    private boolean defaultShipping;

    /**
     * Full constructor including id. This is private so that we keep the
     * public API (two constructors) almost the same as before.
     */

    /* Do not use this constructor to create new addresses. Use only for formatting data. */
    public Address(String id,
                    String recipientName,
                    String line1,
                    String line2,
                    String city,
                    String provinceOrState,
                    String postalCode,
                    String country,
                    boolean defaultBilling,
                    boolean defaultShipping) {

        if (line1 == null || line1.isEmpty()) {
            throw new IllegalArgumentException("The first address line cannot be empty!");
        }
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("The city cannot be empty!");
        }
        if (provinceOrState == null || provinceOrState.isEmpty()) {
            throw new IllegalArgumentException("The province/state cannot be empty!");
        }
        if (postalCode == null || postalCode.isEmpty()) {
            throw new IllegalArgumentException("The postal code cannot be empty!");
        }
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("The country cannot be empty!");
        }

        this.id = id;

        this.recipientName = (recipientName == null) ? "" : recipientName;
        this.line1 = line1;
        this.line2 = (line2 == null) ? "" : line2;
        this.city = city;
        this.provinceOrState = provinceOrState;
        this.postalCode = postalCode;
        this.country = country;
        this.defaultBilling = defaultBilling;
        this.defaultShipping = defaultShipping;
    }

    /**
     * Creates a very simple address from a single line.
     *
     * @param fullAddress a single-line string representing the address
     * @throws IllegalArgumentException when fullAddress is empty
     */
    public Address(String fullAddress) {
        if (fullAddress == null || fullAddress.isEmpty()) {
            throw new IllegalArgumentException("The address cannot be empty!");
        }

        this.id = UUID.randomUUID().toString();

        this.line1 = fullAddress;
        this.recipientName = "";
        this.line2 = "";
        this.city = "";
        this.provinceOrState = "";
        this.postalCode = "";
        this.country = "";
        this.defaultBilling = false;
        this.defaultShipping = false;
    }

    /**
     * Creates a detailed address using separate fields.
     *
     * @param recipientName name of the person receiving the order
     * @param line1 first address line (street, number, etc.)
     * @param line2 second address line (apartment, unit, etc.) - can be empty
     * @param city city of the address
     * @param provinceOrState province or state
     * @param postalCode postal or ZIP code
     * @param country country of the address
     * @param defaultBilling whether this is the default billing address
     * @param defaultShipping whether this is the default shipping address
     * @throws IllegalArgumentException when required fields are empty
     */
    public Address(String recipientName, String line1, String line2,
                   String city, String provinceOrState, String postalCode,
                   String country, boolean defaultBilling, boolean defaultShipping) {

        // Delegate to the full constructor with a new random id
        this(
                UUID.randomUUID().toString(),  // NEW: auto-generated id
                recipientName,
                line1,
                line2,
                city,
                provinceOrState,
                postalCode,
                country,
                defaultBilling,
                defaultShipping
        );
    }

    /**
     * Returns the unique identifier of this address.
     */
    public String getId() {
        return id;
    }

    //Getters

    public String getRecipientName() {
        return recipientName;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getCity() {
        return city;
    }

    public String getProvinceOrState() {
        return provinceOrState;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public boolean isDefaultBilling() {
        return defaultBilling;
    }

    public boolean isDefaultShipping() {
        return defaultShipping;
    }

    //Setters

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public void setLine1(String line1) {
        if (line1 == null || line1.isEmpty()) {
            throw new IllegalArgumentException("The first address line cannot be empty!");
        }
        this.line1 = line1;
    }

    public void setLine2(String line2) {
        this.line2 = line2 == null ? "" : line2;
    }

    public void setCity(String city) {
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("The city cannot be empty!");
        }
        this.city = city;
    }

    public void setProvinceOrState(String provinceOrState) {
        if (provinceOrState == null || provinceOrState.isEmpty()) {
            throw new IllegalArgumentException("The province/state cannot be empty!");
        }
        this.provinceOrState = provinceOrState;
    }

    public void setPostalCode(String postalCode) {
        if (postalCode == null || postalCode.isEmpty()) {
            throw new IllegalArgumentException("The postal code cannot be empty!");
        }
        this.postalCode = postalCode;
    }

    public void setCountry(String country) {
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("The country cannot be empty!");
        }
        this.country = country;
    }

    public void setDefaultBilling(boolean defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    public void setDefaultShipping(boolean defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

    /**
     * Returns a single-line representation of this address.
     * Useful for display in the UI or logs.
     */
    public String toSingleLine() {
        if (city == null || city.isEmpty()) {
            return line1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(line1);
        if (line2 != null && !line2.isEmpty()) {
            sb.append(", ").append(line2);
        }
        sb.append(", ").append(city);
        sb.append(", ").append(provinceOrState);
        sb.append(", ").append(postalCode);
        sb.append(", ").append(country);
        return sb.toString();
    }

    @Override
    public String toString() {
        return toSingleLine();
    }
}