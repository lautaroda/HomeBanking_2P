-- Crear la tabla para usuarios
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

ALTER TABLE usuarios ADD COLUMN role TEXT;


-- Crear la tabla para cuentas
CREATE TABLE cuentas (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(255) NOT NULL,
    saldo DOUBLE PRECISION NOT NULL,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Crear la tabla para tarjetas
CREATE TABLE tarjetas (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(255) NOT NULL,
    limite DOUBLE PRECISION NOT NULL,
    saldo_a_pagar DOUBLE PRECISION NOT NULL,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Crear la tabla para transacciones
CREATE TABLE transacciones (
    id SERIAL PRIMARY KEY,
    id_emisor INT NOT NULL,
    id_receptor INT NOT NULL,
    monto DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (id_emisor) REFERENCES usuarios(id),
    FOREIGN KEY (id_receptor) REFERENCES usuarios(id)
);