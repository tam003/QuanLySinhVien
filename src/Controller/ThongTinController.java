package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ThongTinController implements Initializable{
	
	//@FXML
    //private ImageView image;
    
	public void Back_Home(ActionEvent ae) throws IOException
	{
		Stage stage = (Stage)((Node) ae.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/FXML/Main.fxml"));
		Parent mainParent = loader.load();
		Scene scene = new  Scene(mainParent);
		scene.getStylesheets().add(getClass().getResource("/CSS/application.css").toExternalForm());
		//stage.hide();
		stage.setScene(scene);
		//stage.show();
	}
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//image.setImage(new Image("/Image/Team.jpg"));
		
	}
	

}
