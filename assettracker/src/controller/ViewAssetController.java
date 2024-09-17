package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import application.Asset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;

public class ViewAssetController {

    @FXML
    private TableView<Asset> AssetTableView;
    
    @FXML
    private TableColumn<Asset, Number> idColumn;
    
    @FXML
    private TableColumn<Asset, String> nameColumn;
    
    @FXML
    private TableColumn<Asset, String> locationColumn;
    
    @FXML
    private TableColumn<Asset, String> categoryColumn;

    @FXML
    private TextField SearchField;

    @FXML
    private HBox viewAssetPage;
    
    @FXML
    private VBox assetManipBox;
    
    @FXML
    void showHomePageOp(ActionEvent event) {
    	 URL url = getClass().getClassLoader().getResource("view/Main.fxml");
         try {
             HBox main = (HBox) FXMLLoader.load(url); // Adjusted cast to HBox
             viewAssetPage.getChildren().clear(); // Clearing existing content (if you want to replace the content)
             viewAssetPage.getChildren().add(main);
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
    
    @FXML
    public void initialize() {
    	//VBox temp = new VBox(AssetTableView);
    	//assetManipBox.getChildren();
    	TableColumn<Asset, Number> idColumn =(TableColumn<Asset, Number>) AssetTableView.getColumns().get(0);
    	TableColumn<Asset, Number> nameColumn =(TableColumn<Asset, Number>) AssetTableView.getColumns().get(1);
    	TableColumn<Asset, Number> locationColumn =(TableColumn<Asset, Number>) AssetTableView.getColumns().get(2);
    	TableColumn<Asset, Number> categoryColumn =(TableColumn<Asset, Number>) AssetTableView.getColumns().get(3); 
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("assetID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("assetLocation"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("assetCategory"));

        // Below call fills table to default: includes all assets
        search(null);
    }
    
    @FXML
    void search(ActionEvent event) {
    	String searchSubstr = SearchField.getText();
    	ObservableList<Asset> results = FXCollections.observableArrayList();
    	String updateStmt = "SELECT * FROM assets WHERE assetName LIKE ?";
		try (Connection conn = DbUtil.connect();
				PreparedStatement pstmt = conn.prepareStatement(updateStmt)) {
				pstmt.setString(1,  "%" + searchSubstr + "%");
				ResultSet resultStream = pstmt.executeQuery();
				while(resultStream.next()) {
					Asset currentResult = new Asset(resultStream.getInt("assetID"),
													resultStream.getString("assetName"),
													resultStream.getString("assetCat"),
													resultStream.getString("assetLoc"),
													resultStream.getString("assetDate"),
													resultStream.getString("assetWarranty"),
													resultStream.getString("assetValue"),
													resultStream.getString("assetDesc"));
					results.add(currentResult);
				}
				// Display search results to table
				AssetTableView.setItems(results);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }
    @FXML
    void delete(ActionEvent event) {
    	System.out.println("Delete called");
        int selectedIndex = AssetTableView.getSelectionModel().getSelectedIndex() + 1;
        System.out.println(selectedIndex);
        String query = "DELETE FROM assets WHERE assetID = " + String.valueOf(selectedIndex);
		try (Connection conn = DbUtil.connect();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.executeUpdate();
				// Display search results to table
				search(null);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }
    @FXML
    void edit(ActionEvent event) {
    	// Similar display functionality as the search method
		System.out.println("Opening edit asset page");

    	// Will reuse the AssetCreation Page
    	int selectedIndex = AssetTableView.getSelectionModel().getSelectedIndex();
    	System.out.println(selectedIndex);
    	if (!(selectedIndex == -1)) { // Prevents opening page when nothing is selected
    		showEditAssetPage();
    		
    		// Following lines load date to see
    	}
    	
    }
	@FXML public void showEditAssetPage() {
		URL url = getClass().getClassLoader().getResource("view/EditAssetPage.fxml");
	    try {
	        HBox location = (HBox)FXMLLoader.load(url); // Adjusted cast to HBox
	        viewAssetPage.getChildren().clear(); // Clearing existing content (if you want to replace the content)
	        viewAssetPage.getChildren().add(location);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
