package DTO;

public class Lop {
	private int MaLop, MaKhoa, SiSo;
	private String TenLop;
	
	public Lop(int MaLop, int MaKhoa, String TenLop, int SiSo)
	{
		this.MaLop = MaLop;
		this.MaKhoa = MaKhoa;
		this.TenLop = TenLop;
		this.SiSo = SiSo;
	}

	public int getMaLop() {
		return MaLop;
	}

	public void setMaLop(int maLop) {
		MaLop = maLop;
	}

	public int getMaKhoa() {
		return MaKhoa;
	}

	public void setMaKhoa(int maKhoa) {
		MaKhoa = maKhoa;
	}

	public int getSiSo() {
		return SiSo;
	}

	public void setSiSo(int siSo) {
		SiSo = siSo;
	}

	public String getTenLop() {
		return TenLop;
	}

	public void setTenLop(String tenLop) {
		TenLop = tenLop;
	}
}
