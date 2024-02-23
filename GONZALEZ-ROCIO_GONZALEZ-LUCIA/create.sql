DROP TABLE IF EXISTS ODONTOLOGO;
CREATE TABLE ODONTOLOGO (ID INT AUTO_INCREMENT PRIMARY KEY, numeroMatricula INT NOT NULL, nombre VARCHAR(50) NOT NULL, apellido VARCHAR(50) NOT NULL);

-- para test --
INSERT
INTO ODONTOLOGO(numeroMatricula, nombre , apellido)
VALUES  (742, 'Pablo', 'Mármol'),
        (42, 'Bimba', ' y Lola');
