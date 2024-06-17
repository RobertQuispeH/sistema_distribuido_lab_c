-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3310
-- Tiempo de generación: 13-06-2024 a las 06:52:56
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

CREATE DATABASE IF NOT EXISTS `propuesto_1`;
USE `propuesto_1`;


SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `propuesto_1`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarDepartamento` (IN `p_IDDpto` INT, IN `p_Nombre` VARCHAR(100), IN `p_Telefono` VARCHAR(20), IN `p_Fax` VARCHAR(20))   BEGIN
    INSERT INTO `Propuesto_1`.`Departamento` (`IDDpto`, `Nombre`, `Telefono`, `Fax`) VALUES (p_IDDpto, p_Nombre, p_Telefono, p_Fax);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarIngeniero` (IN `p_IDIng` INT, IN `p_Nombre` VARCHAR(100), IN `p_Especialidad` VARCHAR(100), IN `p_Cargo` VARCHAR(50), IN `p_IDProy` INT)   BEGIN
    INSERT INTO `Propuesto_1`.`Ingeniero` (`IDIng`, `Nombre`, `Especialidad`, `Cargo`, `IDProy`) VALUES (p_IDIng, p_Nombre, p_Especialidad, p_Cargo, p_IDProy);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarProyecto` (IN `p_IDProy` INT, IN `p_Nombre` VARCHAR(100), IN `p_Fec_Inicio` DATE, IN `p_Fec_Termino` DATE, IN `p_IDDpto` INT)   BEGIN
    INSERT INTO `Propuesto_1`.`Proyecto` (`IDProy`, `Nombre`, `Fec_Inicio`, `Fec_Termino`, `IDDpto`) VALUES (p_IDProy, p_Nombre, p_Fec_Inicio, p_Fec_Termino, p_IDDpto);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE `departamento` (
  `IDDpto` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Fax` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `departamento`
--

INSERT INTO `departamento` (`IDDpto`, `Nombre`, `Telefono`, `Fax`) VALUES
(1, 'Recursos Humanos', '123456789', '987654321');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingeniero`
--

CREATE TABLE `ingeniero` (
  `IDIng` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Especialidad` varchar(100) DEFAULT NULL,
  `Cargo` varchar(50) DEFAULT NULL,
  `IDProy` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `ingeniero`
--

INSERT INTO `ingeniero` (`IDIng`, `Nombre`, `Especialidad`, `Cargo`, `IDProy`) VALUES
(1, 'Juan Perez', 'Ingeniería de Software', 'Jefe de Proyecto', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyecto`
--

CREATE TABLE `proyecto` (
  `IDProy` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Fec_Inicio` date DEFAULT NULL,
  `Fec_Termino` date DEFAULT NULL,
  `IDDpto` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `proyecto`
--

INSERT INTO `proyecto` (`IDProy`, `Nombre`, `Fec_Inicio`, `Fec_Termino`, `IDDpto`) VALUES
(1, 'Proyecto Alpha', '2023-01-01', '2023-12-31', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD PRIMARY KEY (`IDDpto`);

--
-- Indices de la tabla `ingeniero`
--
ALTER TABLE `ingeniero`
  ADD PRIMARY KEY (`IDIng`),
  ADD KEY `fk_Ingeniero_Proyecto_idx` (`IDProy`);

--
-- Indices de la tabla `proyecto`
--
ALTER TABLE `proyecto`
  ADD PRIMARY KEY (`IDProy`),
  ADD KEY `fk_Proyecto_Departamento1_idx` (`IDDpto`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ingeniero`
--
ALTER TABLE `ingeniero`
  ADD CONSTRAINT `fk_Ingeniero_Proyecto` FOREIGN KEY (`IDProy`) REFERENCES `proyecto` (`IDProy`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `proyecto`
--
ALTER TABLE `proyecto`
  ADD CONSTRAINT `fk_Proyecto_Departamento1` FOREIGN KEY (`IDDpto`) REFERENCES `departamento` (`IDDpto`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
