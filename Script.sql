DROP DATABASE IF EXISTS Almacen;
CREATE DATABASE Almacen CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE Almacen;

CREATE TABLE trabajador (
    id_trabajador INT AUTO_INCREMENT PRIMARY KEY,
    DNI VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    rol VARCHAR(50),
    turno ENUM('Mañana', 'Tarde', 'Noche'),
    F_insercion DATE,
    f_nacimiento DATE NOT NULL,
    telefono VARCHAR(20),
    residencia VARCHAR(200),
    correo VARCHAR(100)
);

CREATE TABLE carretilla (
    id_vehiculo INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(20) UNIQUE,
    modelo VARCHAR(50),
    tipo VARCHAR(50),
    tamano VARCHAR(50),
    capacidad FLOAT,
    estado ENUM('optimo', 'bien', 'decente', 'revision'),
    descripcion VARCHAR(250)
);

CREATE TABLE habilitado (
    id_vehiculo INT,
    id_trabajador INT,
    PRIMARY KEY (id_vehiculo, id_trabajador),
    FOREIGN KEY (id_trabajador) REFERENCES trabajador(id_trabajador) ON DELETE CASCADE,
    FOREIGN KEY (id_vehiculo) REFERENCES carretilla(id_vehiculo) ON DELETE CASCADE
);

CREATE TABLE almacen (
    id_almacen INT AUTO_INCREMENT,
    id_trabajador INT,
    nombre VARCHAR(100) UNIQUE,
    descripcion VARCHAR(200),
    dimensiones VARCHAR(100),
    ocupacion INT DEFAULT 0,
    capacidad_de_pale INT,
    direccion VARCHAR(255),
    PRIMARY KEY (id_almacen, id_trabajador),
    FOREIGN KEY (id_trabajador) REFERENCES trabajador(id_trabajador)
);

CREATE TABLE categoria (
    nombre_categoria VARCHAR(100) PRIMARY KEY,
    descripcion VARCHAR(125)
);

CREATE TABLE seccion (
    id_seccion INT AUTO_INCREMENT,
    id_almacen INT,
    nombre_categoria VARCHAR(100),
    nombre VARCHAR(100) UNIQUE,
    descripcion VARCHAR(200),
    dimensiones INT,
    ocupacion INT,
    capacidad INT,
    capacidad_de_pale INT,
    PRIMARY KEY (id_seccion, id_almacen, nombre_categoria),
    FOREIGN KEY (id_almacen) REFERENCES almacen(id_almacen),
    FOREIGN KEY (nombre_categoria) REFERENCES categoria(nombre_categoria)
);

CREATE TABLE opera (
    id_seccion INT,
    id_trabajador INT,
    PRIMARY KEY (id_seccion, id_trabajador),
    FOREIGN KEY (id_trabajador) REFERENCES trabajador(id_trabajador) ON DELETE CASCADE,
    FOREIGN KEY (id_seccion) REFERENCES seccion(id_seccion) ON DELETE CASCADE
);

CREATE TABLE paquete (
    id_paquete INT AUTO_INCREMENT,
    id_seccion INT,
    nombre_categoria VARCHAR(100),
    embalaje VARCHAR(50),
    peso FLOAT,
    contenidos TEXT,
    localizacion VARCHAR(100),
    estado ENUM('bien', 'cuestionable', 'mal'),
    PRIMARY KEY (id_paquete, id_seccion),
    FOREIGN KEY (id_seccion) REFERENCES seccion(id_seccion)ON DELETE CASCADE,
    FOREIGN KEY (nombre_categoria) REFERENCES categoria(nombre_categoria)
);

-- TRIGGER: Impide registrar mas de un unico almacen en la base de datos

DELIMITER //

CREATE TRIGGER trg_unico_almacen
BEFORE INSERT ON almacen
FOR EACH ROW
BEGIN
    DECLARE total_almacenes INT;
    SELECT COUNT(*) INTO total_almacenes FROM almacen;
    
    IF total_almacenes >= 1 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Solo se permite registrar un unico almacen en el sistema.';
    END IF;
END //

-- PROCEDIMIENTO ALMACENADO: Elimina una seccion y resta sus dimensiones
-- a la ocupacion actual del almacen al que pertenecia

CREATE PROCEDURE sp_eliminar_seccion(IN p_id_seccion INT)
BEGIN
    DECLARE v_id_almacen INT;
    DECLARE v_dimensiones INT;

    -- Obtenemos el almacen y las dimensiones de la seccion antes de borrarla
    SELECT id_almacen, dimensiones INTO v_id_almacen, v_dimensiones
    FROM seccion
    WHERE id_seccion = p_id_seccion;

    -- Si la seccion existe, la eliminamos y actualizamos la ocupacion del almacén
    IF v_id_almacen IS NOT NULL THEN
        DELETE FROM seccion WHERE id_seccion = p_id_seccion;
        
        UPDATE almacen
        SET ocupacion = GREATEST(0, ocupacion - v_dimensiones)
        WHERE id_almacen = v_id_almacen;
    END IF;
END //

DELIMITER ;

INSERT INTO trabajador (DNI, nombre, apellidos, rol, turno, F_insercion, f_nacimiento, telefono, residencia, correo) VALUES
('12345678A', 'David', 'Navarro Reyes', 'Administrador', 'Mañana', '2025-01-10', '1990-05-15', '600111222', 'Madrid', 'david@almacen.com'),
('87654321B', 'Laura', 'Gómez Pérez', 'Operario', 'Tarde', '2025-02-01', '1995-08-20', '600333444', 'Getafe', 'laura@almacen.com');

INSERT INTO almacen (id_trabajador, nombre, descripcion, dimensiones, ocupacion, capacidad_de_pale, direccion) VALUES
(1, 'Almacén Central', 'Nave logística principal', '2000 m2', 300, 500, 'Polígono Industrial Sur, Nave 4');

INSERT INTO categoria (nombre_categoria, descripcion) VALUES
('Electrónica', 'Componentes y dispositivos electrónicos'),
('Alimentación', 'Productos envasados no perecederos');

INSERT INTO seccion (id_almacen, nombre_categoria, nombre, descripcion, dimensiones, ocupacion, capacidad, capacidad_de_pale) VALUES
(1, 'Electrónica', 'Zona A - Electrónica', 'Estanterías de alta seguridad', 300, 2, 100, 80);

INSERT INTO opera (id_seccion, id_trabajador) VALUES (1, 2);

INSERT INTO carretilla (matricula, modelo, tipo, tamano, capacidad, estado, descripcion) VALUES
('CAR-001', 'Toyota Traigo 48', 'Eléctrica', 'Mediana', 1500.0, 'optimo', 'Carretilla retráctil pasillo estrecho');

INSERT INTO habilitado (id_vehiculo, id_trabajador) VALUES (1, 2);

INSERT INTO paquete (id_seccion, nombre_categoria, embalaje, peso, contenidos, localizacion, estado) VALUES
(1, 'Electrónica', 'Caja reforzada', 12.5, 'Monitores 27 pulgadas', 'Pasillo A - Estante 3', 'bien');