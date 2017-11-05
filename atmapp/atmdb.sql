-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 03, 2014 at 06:28 PM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `atmdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `accountdetail`
--

CREATE TABLE IF NOT EXISTS `accountdetail` (
  `atmno` int(20) NOT NULL COMMENT 'atm card no',
  `accno` int(20) NOT NULL COMMENT 'accont no of atm card holder',
  `pinno` int(10) NOT NULL COMMENT 'pin no of atm card holder',
  `acctype` varchar(50) NOT NULL COMMENT 'account type',
  `name` varchar(100) NOT NULL COMMENT 'name of the atm card holder',
  `balance` float NOT NULL COMMENT 'available balance of atm card holder',
  `atmexpirydate` date NOT NULL COMMENT 'atm expiry date',
  PRIMARY KEY (`atmno`),
  UNIQUE KEY `accno` (`accno`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accountdetail`
--

INSERT INTO `accountdetail` (`atmno`, `accno`, `pinno`, `acctype`, `name`, `balance`, `atmexpirydate`) VALUES
(1000, 10001, 1111, 'saving', 'Akshay Mahadik', 85300, '2018-03-31'),
(2000, 20002, 2222, 'saving', 'Ganesh Mane', 1000, '2015-04-03'),
(4000, 40001, 4444, 'saving', 'Akash Mane', 22700, '2014-03-02'),
(5000, 50001, 5555, 'current', 'Kiran Marne', 5000, '2018-03-31'),
(100011, 100011, 1000, 'current', 'Mayur Kulkarni', 100000, '2014-03-17');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE IF NOT EXISTS `transaction` (
  `trid` int(10) NOT NULL AUTO_INCREMENT,
  `atmno` int(20) DEFAULT NULL,
  `accno` int(20) DEFAULT NULL,
  `depositamt` float DEFAULT NULL COMMENT 'deposit amount',
  `withamt` float DEFAULT NULL,
  `avbalance` float NOT NULL COMMENT 'available balance',
  `tdate` date DEFAULT NULL,
  PRIMARY KEY (`trid`),
  KEY `atmno` (`atmno`),
  KEY `accno` (`accno`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=65 ;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`trid`, `atmno`, `accno`, `depositamt`, `withamt`, `avbalance`, `tdate`) VALUES
(38, 1000, 10001, 0, 0, 61500, '2014-04-02'),
(39, 1000, 10001, 1000, 0, 62500, '2014-04-02'),
(40, 1000, 10001, 0, 100, 62400, '2014-04-02'),
(41, 1000, 10001, 10000, 0, 72400, '2014-04-02'),
(42, 1000, 10001, 0, 1000, 71400, '2014-04-03'),
(43, 1000, 10001, 0, 1000, 70400, '2014-04-03'),
(44, 1000, 10001, 10000, 0, 80400, '2014-04-03'),
(45, 1000, 10001, 1000, 0, 81400, '2014-04-03'),
(46, 1000, 10001, 2000, 0, 83400, '2014-04-03'),
(47, 1000, 10001, 0, 100, 83300, '2014-04-03'),
(48, 1000, 10001, 200, 0, 83500, '2014-04-03'),
(49, 1000, 10001, 0, 100, 83400, '2014-04-03'),
(50, 1000, 10001, 1000, 0, 84400, '2014-04-03'),
(51, 1000, 10001, 1000, 0, 85400, '2014-04-03'),
(52, 1000, 10001, 0, 100, 85300, '2014-04-03'),
(57, 2000, 20002, 0, 4000, 1000, '2014-04-03'),
(59, 5000, 50001, 0, 10000, 45000, '2014-04-03'),
(60, 5000, 50001, 0, 1000, 44000, '2014-04-03'),
(61, 5000, 50001, 0, 9000, 35000, '2014-04-03'),
(62, 5000, 50001, 0, 10000, 25000, '2014-04-03'),
(63, 5000, 50001, 0, 10000, 15000, '2014-04-03'),
(64, 5000, 50001, 0, 10000, 5000, '2014-04-03');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`atmno`) REFERENCES `accountdetail` (`atmno`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`accno`) REFERENCES `accountdetail` (`accno`);
