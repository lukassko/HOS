DROP TABLE IF EXISTS finalised_connections;
DROP TABLE IF EXISTS date_time;

CREATE TABLE finalised_connections (id INT PRIMARY KEY NOT NULL,connection_time TEXT NOT NULL,end_connection_time TEXT,ip varchar(50) NOT NULL,remote_port int NOT NULL,device_id int NOT NULL);
CREATE TABLE date_time (id INT PRIMARY KEY NOT NULL,connection_time TEXT NOT NULL);