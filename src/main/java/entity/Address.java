package entity;

import java.util.Objects;
import java.util.UUID;

/**
 * Entity: Address.
 * Represents a saved shipping/billing address for one user.
 */
public class Address {

    private final String id;          // unique identifier (UUID string)
    private String name;
    private String line1;
    private String line2;
    private String city;
    private String provinceOrState;
    private String postalCode;
    private String country;
    private String phone;
    private boolean defaultShipping;
    private boolean defaultBilling;

    /**
     * Full constructor used by the interactor/DAO when re-creating an Address.
     */
    public Address(String id,
                   String name,
                   String line1,
                   String line2,
                   String city,
                   String provinceOrState,
                   String postalCode,
                   String country,
                   String phone,
                   boolean defaultShipping,
                   boolean defaultBilling) {

        // If id is null or blank, generate a new UUID.
        if (id == null || id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
        this.name = name;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.provinceOrState = provinceOrState;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
        this.defaultShipping = defaultShipping;
        this.defaultBilling = defaultBilling;
    }

    /**
     * Convenience factory for creating a brand-new Address with no id yet.
     */
    public static Address newAddress(String name,
                                     String line1,
                                     String line2,
                                     String city,
                                     String provinceOrState,
                                     String postalCode,
                                     String country,
                                     String phone,
                                     boolean defaultShipping,
                                     boolean defaultBilling) {
        return new Address(null, name, line1, line2, city, provinceOrState,
                postalCode, country, phone, defaultShipping, defaultBilling);
    }

    //Getters

    public String getId() { return id; }

    public String getName() { return name; }

    public String getLine1() { return line1; }

    public String getLine2() { return line2; }

    public String getCity() { return city; }

    public String getProvinceOrState() { return provinceOrState; }

    public String getPostalCode() { return postalCode; }

    public String getCountry() { return country; }

    public String getPhone() { return phone; }

    public boolean isDefaultShipping() { return defaultShipping; }

    public boolean isDefaultBilling() { return defaultBilling; }

    //Setters

    public void setName(String name) { this.name = name; }

    public void setLine1(String line1) { this.line1 = line1; }

    public void setLine2(String line2) { this.line2 = line2; }

    public void setCity(String city) { this.city = city; }

    public void setProvinceOrState(String provinceOrState) { this.provinceOrState = provinceOrState; }

    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public void setCountry(String country) { this.country = country; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setDefaultShipping(boolean defaultShipping) { this.defaultShipping = defaultShipping; }

    public void setDefaultBilling(boolean defaultBilling) { this.defaultBilling = defaultBilling; }

    //Entity equality by id

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}