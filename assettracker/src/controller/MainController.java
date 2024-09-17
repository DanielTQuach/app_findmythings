package controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class MainController {

	@FXML HBox mainBox;

	@FXML public void showCategoryPageOp() {
	    URL url = getClass().getClassLoader().getResource("view/CategoryPage.fxml");
	    try {
	        HBox category = (HBox)FXMLLoader.load(url); // Adjusted cast to HBox
	        mainBox.getChildren().clear(); // Clearing existing content (if you want to replace the content)
	        mainBox.getChildren().add(category);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML public void showLocationPageOp() {
		URL url = getClass().getClassLoader().getResource("view/LocationPage.fxml");
	    try {
	        HBox location = (HBox)FXMLLoader.load(url); // Adjusted cast to HBox
	        mainBox.getChildren().clear(); // Clearing existing content (if you want to replace the content)
	        mainBox.getChildren().add(location);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML public void showAssetPageOp() {
		URL url = getClass().getClassLoader().getResource("view/AssetPage.fxml");
	    try {
	        HBox assetAdd = (HBox)FXMLLoader.load(url); // Adjusted cast to HBox
	        mainBox.getChildren().clear(); // Clearing existing content (if you want to replace the content)
	        mainBox.getChildren().add(assetAdd);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
	@FXML public void showViewAssetPageOp() {
		URL url = getClass().getClassLoader().getResource("view/ViewAssetPage.fxml");
	    try {
	        HBox assetView = (HBox)FXMLLoader.load(url); // Adjusted cast to HBox
	        mainBox.getChildren().clear(); // Clearing existing content (if you want to replace the content)
	        mainBox.getChildren().add(assetView);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
}
