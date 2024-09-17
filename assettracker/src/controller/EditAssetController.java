package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Asset;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class EditAssetController implements Initializable{

    @FXML
    private ChoiceBox<String> CategorySelect;
    @FXML
    private DatePicker DatePurchased;
    @FXML
    private TextArea DescField;
    @FXML
    private ChoiceBox<String> LocationSelect;
    @FXML
    private TextField PurchValueEntry;
    @FXML
    private DatePicker WarrantyExp;
    @FXML
    private HBox assetBox;
    @FXML
    private TextField assetTextEntry;
    @FXML
    private Button saveButton;
    
    @FXML
    private Label feedbacklabel;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) { // Different than regular asset page
    	// Loads selected asset into all the boxes
    	System.out.println("Initialized edit asset page");
		
    	String loc_query = "SELECT loc_name FROM locations";
    	String cat_query = "SELECT cat_name FROM categories";
    	String locColName = "loc_name";
    	String catColName = "cat_name";
    	List<String> locNames = getNames(loc_query, locColName, feedbacklabel);
    	List<String> catNames = getNames(cat_query, catColName, feedbacklabel);
    	
		LocationSelect.getItems().addAll(locNames); // Change later
		CategorySelect.getItems().addAll(catNames);
		String updateStmt = "SELECT * FROM assets WHERE assetID LIKE 4"; // CHANGE THE INTEGER TO USER SELECTED ASSETID #
		try (Connection conn = DbUtil.connect()) {
			PreparedStatement pstmt = conn.prepareStatement(updateStmt);
			ResultSet resultStream = pstmt.executeQuery();
			Asset currentResult = new Asset(resultStream.getInt("assetID"),
													resultStream.getString("assetName"),
													resultStream.getString("assetCat"),
													resultStream.getString("assetLoc"),
													resultStream.getString("assetDate"),
													resultStream.getString("assetWarranty"),
													resultStream.getString("assetValue"),
													resultStream.getString("assetDesc"));
					System.out.println(currentResult.getAssetName());
					
					assetTextEntry.setText(currentResult.getAssetName());
					CategorySelect.setValue(currentResult.getAssetCategory());
					LocationSelect.setValue(currentResult.getAssetLocation());
					DatePurchased.setAccessibleText(currentResult.getAssetPurDate());
					WarrantyExp.setAccessibleText(currentResult.getAssetExpDate());
					DescField.setText(currentResult.getAssetValue());
					PurchValueEntry.setText(currentResult.getAssetDesc());
		}
		catch (Exception e) {
			
		}
    }
    
    public ArrayList<String> getNames(String query, String colname, Label saved) {
    	ArrayList<String> names = new ArrayList<>(); //
    	try (Connection conn = DbUtil.connect()) { //
			Statement stmt = conn.createStatement(); // 
    		ResultSet rs = stmt.executeQuery(query); 
    		while (rs.next()) {
                String name = rs.getString(colname);
                names.add(name);
            }
    		
    	} catch (SQLException e) {
            System.out.println(e.getMessage());
            feedbacklabel.setText("Database Error: Failed to query names for select field" + colname);
    	}
    	return names;
    }
    
    
    @FXML
    void save(ActionEvent event) {
    	// Using java bean instead. Moves dependency to interface?
    	Asset newAsset = new Asset(assetTextEntry.getText(), CategorySelect.getValue(), LocationSelect.getValue()
    			, getDate(DatePurchased), getDate(WarrantyExp), PurchValueEntry.getText()
    			, DescField.getText());
    	//String assetName = assetTextEntry.getText();
    	//String assetCat = CategorySelect.getValue();
    	//String assetLoc = LocationSelect.getValue();
    	//String assetDate = getDate(DatePurchased);
    	//String assetWarranty = getDate(WarrantyExp);
		//String assetDef = DescField.getText();
		//String assetValue = PurchValueEntry.getText();
    	Boolean validated = validateInput(newAsset); // OPT: Can move the method into if statement on the following line below
		if (!validated) { // Required boxes not filled
			feedbacklabel.setText("Please fill the required fields");
		}
		else {
			System.out.println("Storing the data into the database");
			String updateStmt = "UPDATE assets SET assetName=?, assetCat=?, assetLoc=?, assetDate=?, assetWarranty=?, "
					+ "assetValue=?, assetDesc=? WHERE assetID = 4"; // Append to string the row to change
			try (Connection conn = DbUtil.connect();
					PreparedStatement pstmt = conn.prepareStatement(updateStmt)) {
		            pstmt.setString(1, newAsset.getAssetName());
		            pstmt.setString(2, newAsset.getAssetCategory());
		            pstmt.setString(3, newAsset.getAssetLocation());
		            pstmt.setString(4, newAsset.getAssetPurDate());
		            pstmt.setString(5, newAsset.getAssetExpDate()); 
		            pstmt.setString(6, newAsset.getAssetValue());
		            pstmt.setString(7, newAsset.getAssetDesc());
		            pstmt.executeUpdate();
		            feedbacklabel.setText("Location successfully edited!"); // Redirect to homepage instead of displaying msg
			} catch (SQLException e) {
		            System.out.println(e.getMessage());
		            feedbacklabel.setText("Database Error: Failed to save location."); // ""
			}
		}
    }

    public String getDate(DatePicker inputDatePicker) {
    	LocalDate myDate = inputDatePicker.getValue();
    	if (myDate == null) {
    		return null;
    	}
    	String date = myDate.toString();
    	return date;
    }
    
    public  boolean validateInput(Asset asset) {
//    	if (name.isEmpty()) {
//    		feedbacklabel.setText("Asset name must not be blank.");
//    		return false;
//    	}
//    	if (loc == null) {
//    		feedbacklabel.setText("Location name must not be blank.");
//    		return false;
//    	}
//    	if (cat == null) {
//    		feedbacklabel.setText("Category name must not be blank.");
//    		return false;
//    	}
    	return !(asset.getAssetName().isEmpty() || asset.getAssetCategory().isEmpty() || asset.getAssetLocation().isEmpty());
    }
    
    @FXML
    void showHomePageOp(ActionEvent event) {
    	 URL url = getClass().getClassLoader().getResource("view/Main.fxml");
         try {
             HBox category = (HBox) FXMLLoader.load(url); // Adjusted cast to HBox
             assetBox.getChildren().clear(); // Clearing existing content (if you want to replace the content)
             assetBox.getChildren().add(category);
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
    

}
