DROP TABLE IF EXISTS date_time;
DROP TABLE IF EXISTS system_log;
DROP VIEW  IF EXISTS hr_system_log;

--CREATE TABLE finalised_connections (id INTEGER PRIMARY KEY AUTOINCREMENT,connection_time NUMERIC NOT NULL,end_connection_time NUMERIC,ip varchar(50) NOT NULL,remote_port int NOT NULL,device_id int NOT NULL);
CREATE TABLE date_time (id INT PRIMARY KEY NOT NULL,connection_time TEXT NOT NULL);
CREATE TABLE system_log (time NUMERIC, level TEXT,serial TEXT, message TEXT);
CREATE VIEW hr_system_log (time, level, serial,message) AS SELECT STRFTIME('%Y-%m-%d %H:%M:%f', time/1000.0, 'unixepoch', 'localtime'), level,serial, message FROM system_log