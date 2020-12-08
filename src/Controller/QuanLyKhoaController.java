package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import DTO.Khoa;
import DTO.SinhVien;
import connection.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuanLyKhoaController implements Initializable{

	
	JDBCConnection connectClass = new JDBCConnection();
	Connection connection = connectClass.getConnection();
	String query, name;
	int id = -1;
	
    @FXML
    private AnchorPane paneKhoa;

    @FXML
    private TableView<Khoa> tableKhoa;
    
    @FXML
    private TableColumn<Khoa, Integer> col_ID;

    @FXML
    private TableColumn<Khoa, String> col_Name;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnHome;
    
    ObservableList<Khoa> lstKhoa = FXCollections.observableArrayList();
    public void LoadKhoa()
    {
    	tableKhoa.getItems().clear();
    	query = "Select * From Khoa";
    	try {
			Statement statement =  (Statement) connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				//khoa.setiD(result.getInt("MaKhoa"));
				//khoa.setName(result.getNString("TenKhoa"));
				lstKhoa.add(new Khoa(result.getInt("MaKhoa"), result.getNString("TenKhoa")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
    	col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	tableKhoa.setItems(lstKhoa);
    }
    
    public void TableRowEnter(Event e)
    {
    	if (!tableKhoa.getItems().isEmpty())
    	{
    		btnDelete.setDisable(false);
	    	btnUpdate.setDisable(false);
	    	Khoa khoa = tableKhoa.getSelectionModel().getSelectedItem();
	    	if (khoa != null)
	    	{
	    		txtID.setText(String.valueOf(khoa.getId()));
	    		txtName.setText(khoa.getName());
	    	}
    	}
    	
    }
    
    public void Add(ActionEvent ae)
    {
    	name = txtName.getText();
    	if (name.isEmpty())
    	{
    		Alert err = new Alert(AlertType.ERROR);
			err.setTitle("Lỗi");
			err.setHeaderText("Vui lòng nhập tên khoa!");
			Stage stage = (Stage)(err.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
			err.show();
    	}
    	else
    	{
    		Stage stage;
    		query = "INSERT khoa VALUES (null, '"+name+"')";
			try {
				Statement statement = (Statement)connection.createStatement();
				statement.execute(query);
			} catch (SQLException e) {
				Alert err = new Alert(AlertType.ERROR);
				err.setTitle("Lỗi");
				err.setHeaderText("Có lỗi xảy ra. Vui lòng thử lại!");
				stage = (Stage)(err.getDialogPane().getScene().getWindow());
	    		stage.getIcons().add(new Image("/Icon/Warning.png"));
				err.show();
				e.printStackTrace();
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Thêm thành công!");
			stage = (Stage)(alert.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Bell.png"));
    		alert.show();
    		LoadKhoa();
    	}

    }
    
    public void Delete(ActionEvent ae)
    {
    	Khoa khoa = tableKhoa.getSelectionModel().getSelectedItem();
    	if (khoa == null)
    	{
    		Alert err = new Alert(AlertType.ERROR);
			err.setTitle("Lỗi");
			err.setHeaderText("Vui lòng chọn khoa cần xóa!");
			Stage stage = (Stage)(err.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
			err.show();
    	}
    	else
    	{
    		id = Integer.parseInt(txtID.getText());
    		Alert comfirm = new Alert(AlertType.CONFIRMATION);
        	comfirm.setTitle("Xác nhận");
        	comfirm.setHeaderText("Bạn có thật sự muốn xóa khoa "+ khoa.getName() +"?");
        	Stage stage = (Stage)(comfirm.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
    		Optional<ButtonType> result = comfirm.showAndWait();
    		if ( result.get() == ButtonType.OK)
    		{
    			int kq = -1;
    			query = "Delete From Khoa where MaKhoa = " + khoa.getId();
    			try {
    				Statement statement = (Statement)connection.createStatement();
    				kq = statement.executeUpdate(query);
    			} catch (SQLException e) {
    				Alert err = new Alert(AlertType.ERROR);
    				err.setTitle("Lỗi");
    				err.setHeaderText("Có lỗi xảy ra. Vui lòng thử lại!");
    				stage = (Stage)(err.getDialogPane().getScene().getWindow());
    	    		stage.getIcons().add(new Image("/Icon/Warning.png"));
    				err.show();
    			}
    			if (kq == 1)
    			{
    				Alert alert = new Alert(AlertType.INFORMATION);
	    			alert.setTitle("Thông báo");
	    			alert.setHeaderText("Xóa thành công!");
	    			stage = (Stage)(alert.getDialogPane().getScene().getWindow());
	        		stage.getIcons().add(new Image("/Icon/Bell.png"));
	        		alert.show();
	        		LoadKhoa();
    			}
    			
    		}
    		
    	}
    }
    
    public void Update(ActionEvent ae)
    {
    	Khoa khoa = tableKhoa.getSelectionModel().getSelectedItem();
    	Stage stage;
    	if (khoa == null)
    	{
    		Alert err = new Alert(AlertType.ERROR);
			err.setTitle("Lỗi");
			err.setHeaderText("Vui lòng chọn khoa cần cập nhật!");
			stage = (Stage)(err.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
			err.show();
    	}
    	else
    	{
    		name = txtName.getText();
        	if (name.isEmpty())
        	{
        		Alert err = new Alert(AlertType.ERROR);
    			err.setTitle("Lỗi");
    			err.setHeaderText("Vui lòng nhập tên khoa!");
    			stage = (Stage)(err.getDialogPane().getScene().getWindow());
        		stage.getIcons().add(new Image("/Icon/Warning.png"));
    			err.show();
        	}
        	else
        	{
        		Alert comfirm = new Alert(AlertType.CONFIRMATION);
        		comfirm.setTitle("Xác nhận");
        		comfirm.setHeaderText("Bạn có thật sự muốn thay đổi : " + khoa.getName() + " -> " + name + "?");
        		stage = (Stage)comfirm.getDialogPane().getScene().getWindow();
        		stage.getIcons().add(new Image("/Icon/Bell.png"));
        		Optional<ButtonType> result = comfirm.showAndWait();
        		if (result.get() == ButtonType.OK)
        		{
        			int kq = -1;
        			query = "Update Khoa Set TenKhoa = '"+ name + "'" + " Where MaKhoa = " + khoa.getId();
        			try {
    					Statement statement = connection.createStatement();
    					kq = statement.executeUpdate(query);
    				} catch (SQLException e) {
    					Alert err = new Alert(AlertType.ERROR);
        				err.setTitle("Lỗi");
        				err.setHeaderText("Có lỗi xảy ra. Vui lòng thử lại!");
        				stage = (Stage)(err.getDialogPane().getScene().getWindow());
        	    		stage.getIcons().add(new Image("/Icon/Warning.png"));
        				err.show();
    				}
        			if (kq == 1)
        			{
        				Alert alert = new Alert(AlertType.INFORMATION);
    	    			alert.setTitle("Thông báo");
    	    			alert.setHeaderText("Cập nhật thành công!");
    	    			stage = (Stage)(alert.getDialogPane().getScene().getWindow());
    	        		stage.getIcons().add(new Image("/Icon/Bell.png"));
    	        		alert.show();
    	        		LoadKhoa();
        			}
        		}
        	}
    	}
    	
    }
    
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
		// TODO Auto-generated method stub
		LoadKhoa();
		
	}

    
    
}
