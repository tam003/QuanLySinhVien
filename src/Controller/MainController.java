package Controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {


    @FXML
    private BorderPane borderpane;
    
    @FXML
    private Pane panePage;
    
	@FXML
	private Pane paneStudent;

	@FXML
	private Pane paneClass;

	@FXML
	private Pane paneMajors;

	@FXML
	private Pane paneSetting;

	@FXML
	private Pane paneReport;

	@FXML
	private Pane paneInfomation;
	
	
	public void Student_Click(Event e) throws IOException
	{
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/FXML/QuanLySinhVien.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		borderpane.setCenter(root);
	}
	
	public void Class_Click(Event e)
	{
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/FXML/QuanLyLop.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		borderpane.setCenter(root);
	}
	
	public void Majors_Click(Event e)
	{
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/FXML/QuanLyKhoa.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		borderpane.setCenter(root);
	}
	
	public void Setting_Click(Event e)
	{
		Alert error = new Alert(AlertType.INFORMATION);
		error.setTitle("Thông báo");
		Stage stage = (Stage)(error.getDialogPane().getScene().getWindow());
		stage.getIcons().add(new Image("/Icon/Bell.png"));
		error.setHeaderText("Tính năng đang cập nhật!");
		//error.setContentText("Vui lòng mua bản Premium");
		stage.show();
	}
	
	public void Report_Click(Event e)
	{
		
		Alert error = new Alert(AlertType.INFORMATION);
		error.setTitle("Thông báo");
		Stage stage = (Stage)(error.getDialogPane().getScene().getWindow());
		stage.getIcons().add(new Image("/Icon/Bell.png"));
		error.setHeaderText("Tính năng đang cập nhật!");
		//error.setContentText("Vui lòng mua bản Premium");
		stage.show();
	}
	
	public void Infomation_Click(Event e)
	{
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/FXML/ThongTin.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		borderpane.setCenter(root);
	}
}
