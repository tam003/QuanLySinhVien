package DTO;

public class SinhVien { 

	private String MSSV, TenLop, HoTen, GT, DiaChi, NgaySinh;

	private int Diem;
	
	public SinhVien(String MSSV, String TenLop, String HoTen, String NgaySinh, String GT, String DiaChi, int Diem)
	{
		this.MSSV = MSSV;
		this.HoTen = HoTen;
		this.TenLop = TenLop;
		this.GT = GT;
		this.NgaySinh = NgaySinh;
		this.DiaChi = DiaChi;
		this.Diem = Diem;
	}
	
	public String getMSSV() {
		return MSSV;
	}
	public void setMSSV(String mSSV) {
		MSSV = mSSV;
	}
	public String getTenLop() {
		return TenLop;
	}

	public void setTenLop(String tenLop) {
		TenLop = tenLop;
	}
	public String getHoTen() {
		return HoTen;
	}
	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}
	public String getGT() {
		return GT;
	}
	public void setGT(String gT) {
		GT = gT;
	}
	public String getDiaChi() {
		return DiaChi;
	}
	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}
	public String getNgaySinh() {
		return NgaySinh;
	}
	public void setNgaySinh(String ngaySinh) {
		NgaySinh = ngaySinh;
	}
	public int getDiem() {
		return Diem;
	}
	public void setDiem(int diem) {
		Diem = diem;
	}
}
