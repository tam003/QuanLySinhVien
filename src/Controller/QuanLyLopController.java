package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import DTO.Khoa;
import DTO.Lop;
import connection.JDBCConnection;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class QuanLyLopController implements Initializable{

	JDBCConnection connectClass = new JDBCConnection();
	Connection connection = connectClass.getConnection();
	
	String query;
	Stage stage;
	
    @FXML
    private ComboBox<Khoa> cbKhoa;

    @FXML
    private ComboBox<String> cbNienKhoa;

    @FXML
    private TableView<Lop> tableLop;
    
    @FXML
    private TableColumn<Lop, String> col_TenLop;

    @FXML
    private TableColumn<Lop, Integer> col_SiSo;

    @FXML
    private JFXTextField txtTenLop;

    @FXML
    private JFXTextField txtSiSo;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnClear;

    ObservableList<Khoa> lstKhoa = FXCollections.observableArrayList();
    ObservableList<Lop> lstLop = FXCollections.observableArrayList();
    
    public void LoadLop()
    {
    	tableLop.getItems().clear();
    	int MaKhoa = cbKhoa.getValue().getId();
    	query = "Select * From Lop Where MaKhoa =" + MaKhoa;
    	try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				lstLop.add(new Lop(rs.getInt("MaLop"), rs.getInt("MaKhoa"), rs.getNString("TenLop"), rs.getInt("SiSo")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	col_TenLop.setCellValueFactory(new PropertyValueFactory<>("TenLop"));
    	col_SiSo.setCellValueFactory(new PropertyValueFactory<>("SiSo"));
    	tableLop.setItems(lstLop);
    	/*TableColumn numberCol = new TableColumn("STT");
    	numberCol.setCellValueFactory(new Callback<CellDataFeatures<Lop, String>, ObservableValue<String>>() {
    	  @Override 
    	  public ObservableValue<String> call(CellDataFeatures<Lop, String> p) {
    	    return new ReadOnlyObjectWrapper(tableLop.getItems().indexOf(p.getValue()) + "");
    	  }
    	});   
    	numberCol.setSortable(false);
    	numberCol.setPrefWidth(50);
    	tableLop.getColumns().add(0, numberCol);*/
    }
    
    public void KhoaChanged(ActionEvent e)
    {
    	LoadLop();
    }
    
    public void TableRowEnter(Event e)
    {
    	if (!tableLop.getItems().isEmpty())
    	{
    		btnDelete.setDisable(false);
	    	btnUpdate.setDisable(false);
	    	Lop lop = tableLop.getSelectionModel().getSelectedItem();
	    	if (lop != null)
	    	{
	    		txtTenLop.setText(lop.getTenLop());
	    		txtSiSo.setText(String.valueOf(lop.getSiSo()));
	    	}
    	}
    	
    }
    
    public void Clear()
    {
    	txtTenLop.setText("");
    	txtSiSo.setText("");
    }
    
    public void Add(ActionEvent e)
    {
    	if (cbKhoa.getItems().isEmpty())
    	{
    		Alert err = new Alert(AlertType.WARNING);
    		err.setTitle("Thông báo");
    		err.setHeaderText("Chưa có khoa. Vui lòng thêm khoa trước khi thêm lớp!");
    		Stage stage = (Stage)(err.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
			err.show();
    	}
    	else
    	{
    		String ten = txtTenLop.getText();
	    	if(ten.isEmpty())
	    	{
	    		Alert err = new Alert(AlertType.ERROR);
				err.setTitle("Lỗi");
				err.setHeaderText("Vui lòng nhập tên lớp!");
				stage = (Stage)(err.getDialogPane().getScene().getWindow());
	    		stage.getIcons().add(new Image("/Icon/Warning.png"));
				err.show();
	    	}
	    	else
	    	{
	    		Khoa khoa = cbKhoa.getSelectionModel().getSelectedItem();
	    		query = "Insert Lop values(null, "+ khoa.getId() +",'"+ ten + "',0)";
	    		int kq = 0;
	    		try {
					Statement statement = connection.createStatement();
					kq = statement.executeUpdate(query);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		if (kq == 1)
	    		{
	    			Alert alert = new Alert(AlertType.INFORMATION);
	    			alert.setTitle("Thông báo");
	    			alert.setHeaderText("Thêm thành công!");
	    			stage = (Stage)(alert.getDialogPane().getScene().getWindow());
	        		stage.getIcons().add(new Image("/Icon/Bell.png"));
	        		alert.show();
	        		LoadLop();
	    		}
	    		
	    	}
    	}
    	
    }
    
    public void Delete(ActionEvent ae)
    {
    	String ten = txtTenLop.getText();
    	if (ten.isEmpty())
    	{
    		Alert err = new Alert(AlertType.ERROR);
			err.setTitle("Lỗi");
			err.setHeaderText("Vui lòng chọn lớp cần xóa!");
			Stage stage = (Stage)(err.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
			err.show();
    	}
    	else
    	{
    		Lop lop = tableLop.getSelectionModel().getSelectedItem();
    		Alert comfirm = new Alert(AlertType.CONFIRMATION);
        	comfirm.setTitle("Xác nhận");
        	comfirm.setHeaderText("Bạn có thật sự muốn xóa lớp "+ lop.getTenLop() +"?");
        	Stage stage = (Stage)(comfirm.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
    		Optional<ButtonType> result = comfirm.showAndWait();
    		if ( result.get() == ButtonType.OK)
    		{
    			int kq = -1;
    			query = "Delete From Lop where MaLop = '" + lop.getMaLop() + "'";
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
	        		LoadLop();
    			}
    			
    		}
    		
    	}
    }
    
    public void Update(ActionEvent event)
    {
    	Lop lop = tableLop.getSelectionModel().getSelectedItem();
    	if (lop == null)
    	{
    		Alert err = new Alert(AlertType.ERROR);
			err.setTitle("Lỗi");
			err.setHeaderText("Vui lòng chọn lớp cần cập nhật!");
			stage = (Stage)(err.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
			err.show();
    	}
    	else
    	{
    		String ten = txtTenLop.getText();
        	if (ten.isEmpty())
        	{
        		Alert err = new Alert(AlertType.ERROR);
    			err.setTitle("Lỗi");
    			err.setHeaderText("Vui lòng nhập tên lớp!");
    			stage = (Stage)(err.getDialogPane().getScene().getWindow());
        		stage.getIcons().add(new Image("/Icon/Warning.png"));
    			err.show();
        	}
        	else
        	{
        		Alert comfirm = new Alert(AlertType.CONFIRMATION);
        		comfirm.setTitle("Xác nhận");
        		comfirm.setHeaderText("Bạn có thật sự muốn thay đổi : " + lop.getTenLop() + " -> " + ten + "?");
        		stage = (Stage)comfirm.getDialogPane().getScene().getWindow();
        		stage.getIcons().add(new Image("/Icon/Bell.png"));
        		Optional<ButtonType> result = comfirm.showAndWait();
        		if (result.get() == ButtonType.OK)
        		{
        			int kq = -1;
        			query = "Update Lop Set TenLop = '"+ ten + "'" + " Where MaLop = " + lop.getMaLop();
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
    	        		LoadLop();
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
		
		//Load Khoa
    	query = "Select * From Khoa";
    	try {
			Statement statement =  (Statement) connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			if (result.wasNull())
				return;
			while (result.next()) {
				lstKhoa.add(new Khoa(result.getInt("MaKhoa"), result.getNString("TenKhoa")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	cbKhoa.setItems(lstKhoa);
    	if (!lstKhoa.isEmpty())
    		cbKhoa.setValue(lstKhoa.get(0));
    	//convert hiển thị ra combobox
    	cbKhoa.setConverter(new StringConverter<Khoa>() {

			@Override
			public Khoa fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Khoa object) {
				// TODO Auto-generated method stub
				return object.getName();
			}
    		
    	});
    	//set combobox chọn giá trị đầu tiên
    	if (!lstLop.isEmpty())
    	{
    		cbKhoa.setValue(lstKhoa.get(0));
    		LoadLop();
    	}
    		
	}
    
    

}
