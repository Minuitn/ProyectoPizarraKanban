USE pizarrakanbandb;


SHOW DATABASES;
USE pizarra_kanban;
SHOW TABLES;

DESCRIBE usuarios;
DESCRIBE tareas;

SELECT * FROM usuarios;
SELECT * FROM tareas;
SELECT id, username, rol FROM usuarios;

CREATE TABLE chat_mensajes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
