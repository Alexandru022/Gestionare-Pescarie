-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2025 at 06:54 PM
-- Server version: 8.0.40
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pescarie`
--

-- --------------------------------------------------------

--
-- Table structure for table `comenzi`
--

CREATE TABLE `comenzi` (
  `ID_Comanda` int NOT NULL,
  `Data_Comanda` date DEFAULT NULL,
  `Client` varchar(50) DEFAULT NULL,
  `ID_Produs` int DEFAULT NULL,
  `Cantitate` int DEFAULT NULL,
  `Pret_Total` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `furnizori`
--

CREATE TABLE `furnizori` (
  `ID_Furnizor` int NOT NULL,
  `Nume_Furnizor` varchar(50) NOT NULL,
  `Telefon` varchar(20) DEFAULT NULL,
  `Adresa` varchar(100) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `furnizori`
--

INSERT INTO `furnizori` (`ID_Furnizor`, `Nume_Furnizor`, `Telefon`, `Adresa`, `Email`) VALUES
(1, 'AQUA SRL', '0760234567', 'Bulevardul Unirii 12', 'AQUASRL@gmail.com'),
(2, 'SEAQ SRL', '0789213783', 'Reconstructiei 4', 'SEAQSRL@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `produse`
--

CREATE TABLE `produse` (
  `ID_Produs` int NOT NULL,
  `Nume_Produs` varchar(50) NOT NULL,
  `Categorie` varchar(50) DEFAULT NULL,
  `Pret_Unitar` decimal(10,2) DEFAULT NULL,
  `Stoc` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `produse`
--

INSERT INTO `produse` (`ID_Produs`, `Nume_Produs`, `Categorie`, `Pret_Unitar`, `Stoc`) VALUES
(4, 'Crap', 'Peste', 40.00, 340),
(5, 'Somon', 'Peste', 340.00, 87),
(6, 'Macrou', 'Peste', 30.00, 340),
(7, 'Pastrav', 'Peste', 50.00, 589);

-- --------------------------------------------------------

--
-- Table structure for table `vanzari`
--

CREATE TABLE `vanzari` (
  `ID_Vanzare` int NOT NULL,
  `Data_Vanzare` date DEFAULT NULL,
  `ID_Produs` int DEFAULT NULL,
  `Cantitate` int DEFAULT NULL,
  `Pret_Total` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `vanzari`
--

INSERT INTO `vanzari` (`ID_Vanzare`, `Data_Vanzare`, `ID_Produs`, `Cantitate`, `Pret_Total`) VALUES
(3, '2024-02-01', 6, 100, 3000.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comenzi`
--
ALTER TABLE `comenzi`
  ADD PRIMARY KEY (`ID_Comanda`),
  ADD KEY `ID_Produs` (`ID_Produs`);

--
-- Indexes for table `furnizori`
--
ALTER TABLE `furnizori`
  ADD PRIMARY KEY (`ID_Furnizor`);

--
-- Indexes for table `produse`
--
ALTER TABLE `produse`
  ADD PRIMARY KEY (`ID_Produs`);

--
-- Indexes for table `vanzari`
--
ALTER TABLE `vanzari`
  ADD PRIMARY KEY (`ID_Vanzare`),
  ADD KEY `ID_Produs` (`ID_Produs`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comenzi`
--
ALTER TABLE `comenzi`
  MODIFY `ID_Comanda` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `furnizori`
--
ALTER TABLE `furnizori`
  MODIFY `ID_Furnizor` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `produse`
--
ALTER TABLE `produse`
  MODIFY `ID_Produs` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `vanzari`
--
ALTER TABLE `vanzari`
  MODIFY `ID_Vanzare` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comenzi`
--
ALTER TABLE `comenzi`
  ADD CONSTRAINT `comenzi_ibfk_1` FOREIGN KEY (`ID_Produs`) REFERENCES `produse` (`ID_Produs`);

--
-- Constraints for table `vanzari`
--
ALTER TABLE `vanzari`
  ADD CONSTRAINT `vanzari_ibfk_1` FOREIGN KEY (`ID_Produs`) REFERENCES `produse` (`ID_Produs`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
