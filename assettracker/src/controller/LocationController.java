package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LocationController {


	@FXML
	HBox locationBox;
	@FXML
	TextArea locationDefinition;
	@FXML
	TextField locationTextEntry;
	@FXML
	Button saveLocation;
	@FXML
	Label savedLocation;



	@FXML
	public void showHomePageOp() {
		URL url = getClass().getClassLoader().getResource("view/Main.fxml");
		try {
			HBox category = (HBox) FXMLLoader.load(url); // Adjusted cast to HBox
			locationBox.getChildren().clear(); // Clearing existing content (if you want to replace the content)
			locationBox.getChildren().add(category);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void saveLocation(ActionEvent event) {
		String locName = locationTextEntry.getText();
		String locDef = locationDefinition.getText();
		if (locName.isEmpty()) { // Stop here
			savedLocation.setText("Location name must not be blank.");
		}
		else { // Not empty keep going
			String updateStmt = "INSERT INTO locations(loc_name) VALUES(?, ?)";
			try (Connection conn = DbUtil.connect();
					PreparedStatement pstmt = conn.prepareStatement(updateStmt)) {
		            pstmt.setString(1, locName);
		            pstmt.setString(2, locDef);
		            pstmt.executeUpdate();
		    		savedLocation.setText("Location successfully saved!");
			} catch (SQLException e) {
		            System.out.println(e.getMessage());
		    		savedLocation.setText("Database Error: Failed to save location.");
			}
		}
	}
	
}
