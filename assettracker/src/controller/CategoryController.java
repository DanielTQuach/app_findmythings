package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import controller.DbUtil; // Ensure this import matches the package of your DbUtil class

public class CategoryController {

    @FXML
    HBox categoryBox;
    @FXML
    TextField categoryTextEntry;
    @FXML
    Button saveButton;
    @FXML
    Label savedInformation;
    String entry;

    @FXML
    public void showHomePageOp() {
        URL url = getClass().getClassLoader().getResource("view/Main.fxml");
        try {
            HBox category = (HBox) FXMLLoader.load(url); // Adjusted cast to HBox
            categoryBox.getChildren().clear(); // Clearing existing content (if you want to replace the content)
            categoryBox.getChildren().add(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void save(ActionEvent event) {
        entry = categoryTextEntry.getText();
        if (entry.isEmpty()) {
        	savedInformation.setText("Category name must not be blank.");
        } else {
	        String sql = "INSERT INTO categories(cat_name) VALUES(?)";
	        try (Connection conn = DbUtil.connect();
	        		PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, entry);
	            pstmt.executeUpdate();
	            savedInformation.setText("Category successfully saved!");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            savedInformation.setText("Database Error: Failed to save category.");
	        }
        }
    }

}
