-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 09, 2020 lúc 06:08 PM
-- Phiên bản máy phục vụ: 10.4.14-MariaDB
-- Phiên bản PHP: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `csdl_quanlysinhvien`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khoa`
--

CREATE TABLE `khoa` (
  `MaKhoa` int(100) UNSIGNED NOT NULL,
  `TenKhoa` varchar(100) COLLATE utf8_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `khoa`
--

INSERT INTO `khoa` (`MaKhoa`, `TenKhoa`) VALUES
(1, 'Sư phạm'),
(3, 'Kỹ thuật - công nghệ'),
(4, 'Kinh tế');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `lop`
--

CREATE TABLE `lop` (
  `MaLop` int(100) UNSIGNED NOT NULL,
  `MaKhoa` int(100) UNSIGNED NOT NULL,
  `TenLop` varchar(100) COLLATE utf8_vietnamese_ci NOT NULL,
  `SiSo` int(100) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `lop`
--

INSERT INTO `lop` (`MaLop`, `MaKhoa`, `TenLop`, `SiSo`) VALUES
(1, 3, 'DM18PM01', 1),
(2, 3, 'DM18PM02', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sinhvien`
--

CREATE TABLE `sinhvien` (
  `MSSV` varchar(20) COLLATE utf8_vietnamese_ci NOT NULL,
  `MaLop` int(100) UNSIGNED NOT NULL,
  `HoTen` varchar(100) COLLATE utf8_vietnamese_ci NOT NULL,
  `GioiTinh` varchar(5) COLLATE utf8_vietnamese_ci NOT NULL,
  `NgaySinh` date NOT NULL,
  `DiaChi` varchar(100) COLLATE utf8_vietnamese_ci NOT NULL,
  `Diem` int(10) UNSIGNED NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `sinhvien`
--

INSERT INTO `sinhvien` (`MSSV`, `MaLop`, `HoTen`, `GioiTinh`, `NgaySinh`, `DiaChi`, `Diem`) VALUES
('1', 1, 'Nguyễn Văn A', 'Nam', '2020-12-02', 'TP HCM', 19);

--
-- Bẫy `sinhvien`
--
DELIMITER $$
CREATE TRIGGER `siso` AFTER INSERT ON `sinhvien` FOR EACH ROW UPDATE lop SET SiSo = (SELECT COUNT(*) FROM sinhvien WHERE MaLop = NEW.MaLop) WHERE lop.MaLop = NEW.MaLop
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `siso2` AFTER DELETE ON `sinhvien` FOR EACH ROW UPDATE lop SET SiSo = (SELECT COUNT(*) FROM sinhvien WHERE MaLop = old.MaLop) WHERE lop.MaLop = old.MaLop
$$
DELIMITER ;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `khoa`
--
ALTER TABLE `khoa`
  ADD PRIMARY KEY (`MaKhoa`);

--
-- Chỉ mục cho bảng `lop`
--
ALTER TABLE `lop`
  ADD PRIMARY KEY (`MaLop`),
  ADD KEY `rb2` (`MaKhoa`);

--
-- Chỉ mục cho bảng `sinhvien`
--
ALTER TABLE `sinhvien`
  ADD PRIMARY KEY (`MSSV`),
  ADD KEY `rb` (`MaLop`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `khoa`
--
ALTER TABLE `khoa`
  MODIFY `MaKhoa` int(100) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `lop`
--
ALTER TABLE `lop`
  MODIFY `MaLop` int(100) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `lop`
--
ALTER TABLE `lop`
  ADD CONSTRAINT `rb2` FOREIGN KEY (`MaKhoa`) REFERENCES `khoa` (`MaKhoa`);

--
-- Các ràng buộc cho bảng `sinhvien`
--
ALTER TABLE `sinhvien`
  ADD CONSTRAINT `rb` FOREIGN KEY (`MaLop`) REFERENCES `lop` (`MaLop`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
