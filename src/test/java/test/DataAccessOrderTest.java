package test;

import java.util.ArrayList;

import data_access.DataAccessObject;
import entity.Address;
import entity.Order;
import entity.Product;

public class DataAccessOrderTest {
    public static void main(String[] args) {
        DataAccessObject dao = new DataAccessObject();
        Product cheese = dao.getProduct("b6e1362a-c189-4e81-baed-f5489a4210d6");
        dao.saveOrder(new Order(
            dao.getUser("Jeffery"),
            new Product[] {dao.getProduct(cheese.getProductUUID())},
            new ArrayList<Address>(dao.getAddresses("Jim")).get(0).toSingleLine(),
            cheese.getPrice()
        ));
            
    }
}
