DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    monto_inicial DECIMAL(10,2),
    monto_final DECIMAL(10,2),
    tipo_cambio DECIMAL(10,2),
    fecha_registro TIMESTAMP
);