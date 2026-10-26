CREATE TABLE devoluciones(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_compra BIGINT NOT NULL,
    cantidad INT NOT NULL,
    fecha_devolucion TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (id_compra) references tickets (id)
);