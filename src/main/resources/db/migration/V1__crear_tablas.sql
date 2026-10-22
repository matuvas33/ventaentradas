CREATE TABLE evento (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    capacidad_total INT NOT NULL,
    capacidad_disponible INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

CREATE TABLE tickets (
id INT PRIMARY KEY AUTO_INCREMENT,
evento_id BIGINT NOT NULL,
email_comprador VARCHAR(255) NOT NULL,
cantidad INT NOT NULL,
fecha_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (evento_id) REFERENCES evento(id)
);