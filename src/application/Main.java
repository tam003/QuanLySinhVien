package application;
	
import java.util.Optional;

import javafx.application.Application;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));
			Scene scene = new Scene(root,1080,520);
			scene.getStylesheets().add(getClass().getResource("/CSS/application.css").toExternalForm());
			scene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("/Icon/Icon.png"));
			primaryStage.setTitle("Quản lý sinh viên");
			//primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			primaryStage.setOnCloseRequest(e -> WindowClosing(e, primaryStage)); // set event formClosing cho Stage
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void WindowClosing(Event e, Stage primaryStage) 
	{
		Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
		Stage stage = (Stage)(exit.getDialogPane().getScene().getWindow());
		stage.getIcons().add(new Image("/Icon/Bell.png"));
		exit.setTitle("Xác nhận");
		exit.setHeaderText("Thoát chương trình?");
		Optional<ButtonType> result = exit.showAndWait();
		if (result.get() == ButtonType.OK)
			primaryStage.close();
		else
			e.consume();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
