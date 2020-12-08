package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import DTO.Khoa;
import DTO.Lop;
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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class QuanLySinhVienController implements Initializable{
	
	JDBCConnection connectClass = new JDBCConnection();
	Connection connection = connectClass.getConnection();
	
    String query;
	String MSSV, TenLop, HoTen, GT, DiaChi, NgaySinh;
	int Diem = 0, MaLop;

	@FXML
    private TableView<SinhVien> tableSV;

    @FXML
    private TableColumn<SinhVien, String> col_MSSV;

    @FXML
    private TableColumn<SinhVien, String> col_Lop;

    @FXML
    private TableColumn<SinhVien, String> col_HoTen;

    @FXML
    private TableColumn<SinhVien, String> col_GT;

    @FXML
    private TableColumn<SinhVien, String> col_NgaySinh;

    @FXML
    private TableColumn<SinhVien, String> col_DiaChi;

    @FXML
    private TableColumn<SinhVien, Integer> col_Diem;

    @FXML
    private JFXTextField txtMSSV;

    @FXML
    private JFXTextField txtHoTen;

    @FXML
    private JFXComboBox<Lop> cbLop;

    @FXML
    private JFXComboBox<String> cbGT;

    @FXML
    private JFXTextField txtDiem;

    @FXML
    private DatePicker dpkNgaySinh;

    @FXML
    private JFXTextArea txtDiaChi;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnExit;

    @FXML
    private JFXComboBox<Khoa> cbKhoa;
    
    
    ObservableList<String> listGT = FXCollections.observableArrayList("Nam", "Nữ");
    ObservableList<Lop> listLop = FXCollections.observableArrayList();
    ObservableList<SinhVien> listSV = FXCollections.observableArrayList();
    ObservableList<Khoa> listKhoa = FXCollections.observableArrayList();

    public void Delete(ActionEvent event) {
    	
    	MSSV = txtMSSV.getText();
    	Alert comfirm = new Alert(AlertType.CONFIRMATION);
    	comfirm.setTitle("Xác nhận");
    	comfirm.setHeaderText("Bạn có thật sự muốn xóa sinh viên với MSSV : " + MSSV);
    	Stage stage = (Stage)(comfirm.getDialogPane().getScene().getWindow());
		stage.getIcons().add(new Image("/Icon/Bell.png"));
		Optional<ButtonType> result = comfirm.showAndWait();
		if ( result.get() == ButtonType.OK)
		{
			query = "Delete From SinhVien Where MSSV='"+MSSV+"'";
	    	try {
				Statement statement = (Statement) connection.createStatement();
				statement.execute(query);
			} catch (Exception e) {

			}
			Lop_Change(event);
			Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("Thông báo");
		    alert.setHeaderText("Xóa thành công!");
		    Stage stage3 = (Stage)(alert.getDialogPane().getScene().getWindow());
			stage.getIcons().add(new Image("/Icon/Bell.png"));
			alert.show();	
			
		}
    	
    }

    public void Edit(ActionEvent event) {

    	MSSV = txtMSSV.getText();
    	MaLop = cbLop.getValue().getMaLop();
    	HoTen = txtHoTen.getText();
    	NgaySinh = dpkNgaySinh.getValue().toString();
    	DiaChi = txtDiaChi.getText();
    	Diem = Integer.parseInt(txtDiem.getText());
    	GT = cbGT.getValue();
    	if (MSSV.isEmpty() || MaLop == 0 || HoTen.isEmpty() || NgaySinh.isEmpty() || DiaChi.isEmpty() || txtDiem.getText() == "")
    	{
    		Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Lỗi");
			error.setHeaderText("Dữ liệu không hợp lệ!");
			error.setContentText("Vui lòng thử lại!");
			Stage stage = (Stage)(error.getDialogPane().getScene().getWindow());
			stage.getIcons().add(new Image("/Icon/Warning.png"));
			error.show();
    	}
    	else if (Diem < 0)
    	{
    		Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Lỗi");
			error.setHeaderText("Điểm không hợp lệ!");
			error.setContentText("Vui lòng thử lại!");
			Stage stage = (Stage)(error.getDialogPane().getScene().getWindow());
			stage.getIcons().add(new Image("/Icon/Warning.png"));
			error.show();
    	}
    	else
    	{
    		Alert comfirm = new Alert(AlertType.CONFIRMATION);
        	comfirm.setTitle("Xác nhận");
        	comfirm.setHeaderText("Bạn có thật sự muốn cập nhật?");
        	Stage stage = (Stage)(comfirm.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Bell.png"));
    		Optional<ButtonType> result = comfirm.showAndWait();
    		if ( result.get() == ButtonType.OK)
    		{
    			query = "Update SinhVien Set MaLop = '"+MaLop+"',HoTen = '"+HoTen+"', GioiTinh = '"+GT+"', NgaySinh = '"+NgaySinh+"', DiaChi = '"+DiaChi+"',Diem ="+Diem+" Where MSSV='"+ MSSV +"'";
        		try {
    				Statement statement = (Statement) connection.createStatement();
    				statement.execute(query);
    			} catch (Exception e) {
    				Alert err = new Alert(AlertType.ERROR);
    				err.setTitle("Lỗi");
    				err.setHeaderText("Thêm thất bại. MSSV đã tồn tại");
    				stage = (Stage)(err.getDialogPane().getScene().getWindow());
    	    		stage.getIcons().add(new Image("/Icon/Warning.png"));
    				err.show();
    				return;
    			}
        		Lop_Change(event);
        		Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Thông báo");
            	alert.setHeaderText("Cập nhật thành công!");
            	stage = (Stage)(alert.getDialogPane().getScene().getWindow());
        		stage.getIcons().add(new Image("/Icon/Bell.png"));
        		alert.show();
    		}
    		
    	}

    }

    public void Exit(ActionEvent event) {
    	
    	Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
		exit.setTitle("Xác nhận");
		exit.setHeaderText("Thoát chương trình?");
		Stage stage = (Stage)(exit.getDialogPane().getScene().getWindow());
		stage.getIcons().add(new Image("/Icon/Bell.png"));
		Optional<ButtonType> result = exit.showAndWait();
		if (result.get() == ButtonType.OK)
			System.exit(0);
		else
			event.consume();
    }

    public void Clear(ActionEvent event) {
    	txtMSSV.setText("");
    	txtHoTen.setText("");
    	txtDiaChi.setText("");
    	txtDiem.setText("");
    	
    }

    public void Add(ActionEvent event) throws InterruptedException {
    	MSSV = txtMSSV.getText();
    	HoTen = txtHoTen.getText();
    	NgaySinh = dpkNgaySinh.getValue().toString();
    	DiaChi = txtDiaChi.getText();
    	GT = cbGT.getValue();
    	if (MSSV.isEmpty() || HoTen.isEmpty() || NgaySinh.isEmpty() || DiaChi.isEmpty() || txtDiem.getText() == ""  || cbLop.getItems().isEmpty() )
    	{
    		Alert error = new Alert(AlertType.ERROR);
    		Stage stage = (Stage)(error.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
			error.setTitle("Lỗi");
			error.setHeaderText("Vui lòng nhập đầy đủ dữ liệu!");
			//error.setContentText("Vui lòng thử lại!");
			stage.show();
    	}
    	else if ((Diem = Integer.parseInt(txtDiem.getText())) < 0)
    	{
    		
    		Alert error = new Alert(AlertType.ERROR);
    		Stage stage = (Stage)(error.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
			error.setTitle("Lỗi");
			error.setHeaderText("Điểm không hợp lệ!");
			error.setContentText("Vui lòng thử lại!");
			error.show();
    	}
    	else
    	{
    		MaLop = cbLop.getValue().getMaLop();
    		query = "Insert SinhVien Values('"+ MSSV +"','"+MaLop+"','"+HoTen+"','"+GT+"','"+NgaySinh+"','"+DiaChi+"',"+Diem+")";
    		try {
				Statement statement = (Statement) connection.createStatement();
				statement.execute(query);
			} catch (Exception e) {
				Alert err = new Alert(AlertType.ERROR);
				err.setTitle("Lỗi");
				err.setHeaderText("Thêm thất bại. MSSV đã tồn tại");
				Stage stage = (Stage)(err.getDialogPane().getScene().getWindow());
	    		stage.getIcons().add(new Image("/Icon/Warning.png"));
				err.show();
				return;
			}

    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông báo");
			alert.setHeaderText("Thêm thành công!");
			Stage stage = (Stage)(alert.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Bell.png"));
    		alert.show();	    
    		Lop_Change(event);
    	}

    }

    public void KhoaChange(ActionEvent event)
    {
    	listLop.clear();
    	int MaKhoa = cbKhoa.getValue().getId();
    	query = "Select * From Lop Where MaKhoa =" + MaKhoa;
    	try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				listLop.add(new Lop(rs.getInt("MaLop"), rs.getInt("MaKhoa"), rs.getNString("TenLop"), rs.getInt("SiSo")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	cbLop.getItems().clear();
    	if (listLop.isEmpty())
    	{
    		Alert err = new Alert(AlertType.WARNING);
    		err.setTitle("Thông báo");
    		err.setHeaderText("Khoa chưa có lớp. Vui lòng thêm lớp trước khi thêm sinh viên!");
    		Stage stage = (Stage)(err.getDialogPane().getScene().getWindow());
    		stage.getIcons().add(new Image("/Icon/Warning.png"));
			err.show();    		
    	}
    	else
    	{
    		cbLop.getItems().addAll(listLop);
    		cbLop.setValue(listLop.get(0));
    	}
    	
    }
    
    public void Lop_Change(ActionEvent event){
    	if (listLop != null)
    	{
    		MaLop = cbLop.getValue().getMaLop();
	    	query = "Select MSSV, TenLop, Hoten, GioiTinh, DiaChi, NgaySinh, Diem From SinhVien, Lop Where Lop.MaLop =" + MaLop + " and SinhVien.MaLop = Lop.MaLop";
	    	tableSV.getItems().clear();
	    	try {
				Statement statement = (Statement) connection.createStatement();
				ResultSet result = statement.executeQuery(query);
				while (result.next()) {
					listSV.add(new SinhVien(result.getString("MSSV"), result.getString("TenLop"), result.getString("HoTen"), result.getString("NgaySinh"), result.getString("GioiTinh"), result.getString("DiaChi"), result.getInt("Diem")));
				}
			}
			catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
	    	//binding
	    	col_MSSV.setCellValueFactory(new PropertyValueFactory<>("MSSV"));
	    	col_Lop.setCellValueFactory(new PropertyValueFactory<>("TenLop"));
	    	col_HoTen.setCellValueFactory(new PropertyValueFactory<>("HoTen"));
	    	col_GT.setCellValueFactory(new PropertyValueFactory<>("GT"));
	    	col_DiaChi.setCellValueFactory(new PropertyValueFactory<>("DiaChi"));
	    	col_NgaySinh.setCellValueFactory(new PropertyValueFactory<>("NgaySinh"));
	    	col_Diem.setCellValueFactory(new PropertyValueFactory<>("Diem"));
	    	tableSV.setItems(listSV);
    	}
    	
    }
    
    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
    
    public void TableRowEnter(Event event)
    {
    	if (!tableSV.getItems().isEmpty())
    	{
    		btnDelete.setDisable(false);
	    	btnEdit.setDisable(false);
	    	SinhVien svm = tableSV.getSelectionModel().getSelectedItem();
	    	if (svm != null)
	    	{
	    		txtMSSV.setText(svm.getMSSV());
		    	txtHoTen.setText(svm.getHoTen());
		    	txtDiem.setText(String.valueOf(svm.getDiem()));
		    	dpkNgaySinh.setValue(LOCAL_DATE(svm.getNgaySinh()));
		    	cbGT.setValue(svm.getGT());
		    	txtDiaChi.setText(svm.getDiaChi());
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
    public void SetButton(Event e)
    {
    	MSSV = txtMSSV.getText();
    	if(MSSV.isEmpty())
    	{
        	btnDelete.setDisable(true);
    		btnEdit.setDisable(true);
    	}
    	else
    	{
    		btnDelete.setDisable(false);
        	btnEdit.setDisable(false);
    	}
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		cbGT.getItems().addAll(listGT); // thêm vào combobox
		cbGT.setValue(listGT.get(0)); // chọn mặc định là nam
		dpkNgaySinh.setValue(LocalDate.now()); // mặc định ngày hiện tại
		
		final char seperatorChar = '.';
		final Pattern p = Pattern.compile("[0-9" + seperatorChar + "]*");
		//Không cho nhập ký tự
		txtDiem.setTextFormatter(new TextFormatter<>(c -> {
		    if (!c.isContentChange()) {
		        return c; // no need for modification, if only the selection changes
		    }
		    String newText = c.getControlNewText();
		    if (newText.isEmpty()) {
		        return c;
		    }
		    if (!p.matcher(newText).matches()) {
		        return null; // invalid change
		    }
			return c;
		}));
		
		txtMSSV.setTextFormatter(new TextFormatter<>(c -> {
		    if (!c.isContentChange()) {
		        return c; // no need for modification, if only the selection changes
		    }
		    String newText = c.getControlNewText();
		    if (newText.isEmpty()) {
		        return c;
		    }
		    if (!p.matcher(newText).matches()) {
		        return null; // invalid change
		    }
			return c;
		}));
		//convert định dạng ngày
		dpkNgaySinh.setConverter(new StringConverter<LocalDate>() {
			 String pattern = "yyyy-MM-dd";
			 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			 {
			     dpkNgaySinh.setPromptText(pattern.toLowerCase());
			 }

			 @Override public String toString(LocalDate date) {
			     if (date != null) {
			         return dateFormatter.format(date);
			     } else {
			         return "";
			     }
			 }

			 @Override public LocalDate fromString(String string) {
			     if (string != null && !string.isEmpty()) {
			         return LocalDate.parse(string, dateFormatter);
			     } else {
			         return null;
			     }
			 }
			});
		//Load Khoa
    	query = "Select * From Khoa";
    	try {
			Statement statement =  (Statement) connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			if (result.wasNull())
				return;
			while (result.next()) {
				listKhoa.add(new Khoa(result.getInt("MaKhoa"), result.getNString("TenKhoa")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	cbKhoa.setItems(listKhoa);
    	if (!listKhoa.isEmpty())
    		cbKhoa.setValue(listKhoa.get(0));
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
    	if (!listKhoa.isEmpty())
    			cbKhoa.setValue(listKhoa.get(0));
    	//convert combox lớp
    	cbLop.setConverter(new StringConverter<Lop>() {
			
			@Override
			public String toString(Lop object) {
				// TODO Auto-generated method stub
				return object.getTenLop();
			}
			
			@Override
			public Lop fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}
		});
    	// Load lớp
    	if (!listKhoa.isEmpty())
    	{
    		Khoa k = cbKhoa.getSelectionModel().getSelectedItem();
	    	query = "Select * From Lop Where MaKhoa =" + k.getId();
	    	try {
	    		Statement statement = (Statement) connection.createStatement();
	    		ResultSet result = statement.executeQuery(query);
	    		if (!result.wasNull())
	    			return;
	    		while (result.next()) {
	    			listLop.add(new Lop(result.getInt("MaLop"), result.getInt("MaKhoa"), result.getNString("TenLop"), result.getInt("SiSo")));
	    		}
	    	}
	    	catch (SQLException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	cbLop.getItems().addAll(listLop); // đem list add vào combobox
	    	if (!listLop.isEmpty())
	    	{
	    		cbLop.setValue(listLop.get(0)); // chọn mặc định là cái đầu tiên
	    		Lop_Change(null);
	    	}
	    	
    	}
    			
		
	}
	
	
}
