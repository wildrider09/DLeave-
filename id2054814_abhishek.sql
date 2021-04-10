-- phpMyAdmin SQL Dump
-- version 4.7.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 08, 2018 at 04:43 PM
-- Server version: 10.1.29-MariaDB
-- PHP Version: 7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id2054814_abhishek`
--

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE `devices` (
  `id` int(11) NOT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `token` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `devices`
--

INSERT INTO `devices` (`id`, `email`, `token`) VALUES
(1, 'emp101', 'c-sKd6tzceI:APA91bGlMsvDfRe5BNBQHFyA0Ml8l3gSo3fexTOIry37zPC7gDOu3mWCSoLQFVQk5E7z7BQ9tRCA74K_7IDSWlNaMLdY7uE4LizVrwmPXR9w7-AxRLVV10nOVbfbYu05SSG-ivDTlev0'),
(2, 'emp102', 'c-sKd6tzceI:APA91bGlMsvDfRe5BNBQHFyA0Ml8l3gSo3fexTOIry37zPC7gDOu3mWCSoLQFVQk5E7z7BQ9tRCA74K_7IDSWlNaMLdY7uE4LizVrwmPXR9w7-AxRLVV10nOVbfbYu05SSG-ivDTlev0');

-- --------------------------------------------------------

--
-- Table structure for table `leaves`
--

CREATE TABLE `leaves` (
  `empid` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `startdate` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `enddate` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `reason` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `days` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `manager` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `leaves`
--

INSERT INTO `leaves` (`empid`, `startdate`, `enddate`, `reason`, `days`, `status`, `manager`) VALUES
('emp101', '24/2/2018', '28/2/2018', 'ahaj', '9', 'allow', 'emp102');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `empid` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `mail` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `manager` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `mname` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`empid`, `name`, `mail`, `type`, `password`, `manager`, `mname`) VALUES
('emp101', 'Abhishek', 'abhi@gmail.com', 'Employee', '123456789', 'emp102', 'Suresh'),
('emp102', 'Suresh', 'kabhi@gmail.com', 'Manager', '987654321', 'emp103', 'Kishore'),
('emp103', 'Kishore', 'kishore90@gmail.com', 'Manager', '1468', '-', '-'),
('emp104', 'Ramesh', 'ramesh90@gmail.com', 'Employee', '123456789', 'emp102', 'Suresh'),
('emp105', 'Ayush', 'ayush90@gmail.com', 'Employee', '123456789', 'emp102', 'Suresh'),
('emp106', 'Prashant', 'prnst0@gmail.com', 'Employee', '123456789', 'emp102', 'Suresh'),
('emp107', 'Hemant', 'hemu89@gmail.com', 'Employee', '123456789', 'emp102', 'Suresh');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `devices`
--
ALTER TABLE `devices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`empid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `devices`
--
ALTER TABLE `devices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
