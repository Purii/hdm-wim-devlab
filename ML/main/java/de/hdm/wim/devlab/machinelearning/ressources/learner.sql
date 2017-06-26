-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 22. Jun 2017 um 07:56
-- Server-Version: 10.1.16-MariaDB
-- PHP-Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `messages`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `testlearner`
--

CREATE TABLE `learner` (
  `learnerId` int(11) NOT NULL,
  `userId` varchar(255) NOT NULL,
  `documentId` varchar(255) NOT NULL,
  `message` varchar(255) NOT NULL,
  `documentAffiliation` varchar(255) NOT NULL,
  `rate` int(11) NOT NULL DEFAULT '1',
  `favor` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `testlearner`
--
ALTER TABLE `testlearner`
  ADD PRIMARY KEY (`learnerId`),
  ADD UNIQUE KEY `IDCombination` (`userId`,`documentId`) USING BTREE;

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `testlearner`
--
ALTER TABLE `testlearner`
  MODIFY `learnerId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
