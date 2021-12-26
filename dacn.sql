-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 19, 2021 lúc 09:24 AM
-- Phiên bản máy phục vụ: 10.4.22-MariaDB
-- Phiên bản PHP: 7.3.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `dacn`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `ID` int(11) NOT NULL,
  `category` varchar(45) COLLATE utf8_vietnamese_ci NOT NULL,
  `picture` varchar(100) COLLATE utf8_vietnamese_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `categories`
--

INSERT INTO `categories` (`ID`, `category`, `picture`) VALUES
(17, 'Burger', NULL),
(18, 'Pizza', NULL),
(22, 'Lẩu', ''),
(23, 'Nước', NULL),
(24, 'Món Chiên', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `foods`
--

CREATE TABLE `foods` (
  `id` int(11) NOT NULL,
  `namefood` varchar(50) COLLATE utf8_vietnamese_ci NOT NULL,
  `price` float NOT NULL,
  `discount` int(11) NOT NULL,
  `picture` varchar(100) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `category` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `foods`
--

INSERT INTO `foods` (`id`, `namefood`, `price`, `discount`, `picture`, `category`) VALUES
(1, 'Coca-Cola', 12000, 0, NULL, 23),
(2, 'Number 1', 12000, 0, NULL, 23),
(4, 'Burger bò', 30000, 0, NULL, 17),
(5, 'Buger gà', 30000, 28000, NULL, 17),
(6, 'Pizza hải sản', 200000, 190000, NULL, 18),
(7, 'Pizza Cheese Lover', 180000, 0, NULL, 18),
(8, 'Lầu bò', 150000, 0, NULL, 22),
(9, 'Đùi gà chiên', 35000, 30000, NULL, 24),
(10, 'Cách gà chiên', 30000, 0, NULL, 24),
(11, 'Burger trứng', 25000, 0, '', 17),
(12, 'Lẩu cá', 160000, 150000, '', 22),
(13, 'Pizza xúc xích', 210000, 0, '', 18),
(14, 'Pepsi', 12000, 0, '', 23);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `accounttype` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`username`, `pass`, `accounttype`) VALUES
('admin', 'admin', 0),
('staff1', 'staff1', 1),
('staff2', 'staff2', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`ID`);

--
-- Chỉ mục cho bảng `foods`
--
ALTER TABLE `foods`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Food_Category_FK` (`category`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT cho bảng `foods`
--
ALTER TABLE `foods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `foods`
--
ALTER TABLE `foods`
  ADD CONSTRAINT `Food_Category_FK` FOREIGN KEY (`category`) REFERENCES `categories` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
