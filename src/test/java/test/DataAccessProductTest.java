package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import javax.swing.JFileChooser;

import data_access.DataAccessObject;
import entity.Product;

public class DataAccessProductTest {
    
    public static void main(String[] args) {
        DataAccessObject dao = new DataAccessObject();

        String selectedFilePath = null;
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
        }
        Product Chair;
        try {
            Chair = new Product(
                "Chair",
                100.0,
                UUID.randomUUID().toString(),
                // Base64.getEncoder().encodeToString(
                //         Files.readAllBytes(Paths.get(selectedFilePath))
                // ),
                "chairbase64test",
                dao.getUser("Chair1"),
                "Office"
            );
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
            
        }
        dao.postProduct(Chair);
    }
}
