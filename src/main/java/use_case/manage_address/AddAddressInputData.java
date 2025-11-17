package use_case.manage_address;

/**
 * Request model for adding an address.
 */
public class AddAddressInputData {
    private final String username;
    private final String line1;
    private final String line2;
    private final String city;
    private final String provinceOrState;
    private final String postalCode;
    private final String country;
    private final boolean defaultShipping;
    private final boolean defaultBilling;

    public AddAddressInputData(String username,
                               String line1,
                               String line2,
                               String city,
                               String provinceOrState,
                               String postalCode,
                               String country,
                               boolean defaultShipping,
                               boolean defaultBilling) {
        this.username = username;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.provinceOrState = provinceOrState;
        this.postalCode = postalCode;
        this.country = country;
        this.defaultShipping = defaultShipping;
        this.defaultBilling = defaultBilling;
    }

    public String getUsername() { return username; }
    public String getLine1() { return line1; }
    public String getLine2() { return line2; }
    public String getCity() { return city; }
    public String getProvinceOrState() { return provinceOrState; }
    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }
    public boolean isDefaultShipping() { return defaultShipping; }
    public boolean isDefaultBilling() { return defaultBilling; }
}