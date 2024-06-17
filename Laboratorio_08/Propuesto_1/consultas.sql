--- Sentencias preparadas
PREPARE insertar_departamento FROM 'INSERT INTO `Propuesto_1`.`Departamento` (`IDDpto`, `Nombre`, `Telefono`, `Fax`) VALUES (?, ?, ?, ?)';
PREPARE insertar_proyecto FROM 'INSERT INTO `Propuesto_1`.`Proyecto` (`IDProy`, `Nombre`, `Fec_Inicio`, `Fec_Termino`, `IDDpto`) VALUES (?, ?, ?, ?, ?)';
PREPARE insertar_ingeniero FROM 'INSERT INTO `Propuesto_1`.`Ingeniero` (`IDIng`, `Nombre`, `Especialidad`, `Cargo`, `IDProy`) VALUES (?, ?, ?, ?, ?)';

---Procedimientos almacenados.
DELIMITER //
CREATE PROCEDURE `Propuesto_1`.`insertarDepartamento` (IN p_IDDpto INT, IN p_Nombre VARCHAR(100), IN p_Telefono VARCHAR(20), IN p_Fax VARCHAR(20))
BEGIN
    INSERT INTO `Propuesto_1`.`Departamento` (`IDDpto`, `Nombre`, `Telefono`, `Fax`) VALUES (p_IDDpto, p_Nombre, p_Telefono, p_Fax);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `Propuesto_1`.`insertarProyecto` (IN p_IDProy INT, IN p_Nombre VARCHAR(100), IN p_Fec_Inicio DATE, IN p_Fec_Termino DATE, IN p_IDDpto INT)
BEGIN
    INSERT INTO `Propuesto_1`.`Proyecto` (`IDProy`, `Nombre`, `Fec_Inicio`, `Fec_Termino`, `IDDpto`) VALUES (p_IDProy, p_Nombre, p_Fec_Inicio, p_Fec_Termino, p_IDDpto);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `Propuesto_1`.`insertarIngeniero` (IN p_IDIng INT, IN p_Nombre VARCHAR(100), IN p_Especialidad VARCHAR(100), IN p_Cargo VARCHAR(50), IN p_IDProy INT)
BEGIN
    INSERT INTO `Propuesto_1`.`Ingeniero` (`IDIng`, `Nombre`, `Especialidad`, `Cargo`, `IDProy`) VALUES (p_IDIng, p_Nombre, p_Especialidad, p_Cargo, p_IDProy);
END //
DELIMITER ;


---Restricciones
ALTER TABLE Propuesto_1.Proyecto
ADD CONSTRAINT chk_fechas CHECK (Fec_Termino >= Fec_Inicio);

-- Ejemplo de transacción
BEGIN;

INSERT INTO Propuesto_1.Departamento (IDDpto, Nombre, Telefono, Fax) VALUES (1, 'Recursos Humanos', '123456789', '987654321');
INSERT INTO Propuesto_1.Proyecto (IDProy, Nombre, Fec_Inicio, Fec_Termino, IDDpto) VALUES (1, 'Proyecto Alpha', '2023-01-01', '2023-12-31', 1);
INSERT INTO Propuesto_1.Ingeniero (IDIng, Nombre, Especialidad, Cargo, IDProy) VALUES (1, 'Juan Perez', 'Ingeniería de Software', 'Jefe de Proyecto', 1);

COMMIT;

-- En caso de error
ROLLBACK;
