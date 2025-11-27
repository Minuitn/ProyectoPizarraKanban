CREATE DATABASE PizarraKanbanDB;
USE PizarraKanbanDB;
CREATE TABLE tareas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    prioridad VARCHAR(50) NOT NULL,
    responsable VARCHAR(100) NOT NULL
);
SHOW TABLES;
DESCRIBE tareas;
SELECT * FROM tareas;




