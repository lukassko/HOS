DROP TABLE IF EXISTS system_log;
DROP VIEW  IF EXISTS hr_system_log;

CREATE TABLE system_log (time NUMERIC, level TEXT, message TEXT);
CREATE VIEW hr_system_log (time, level, message) AS SELECT STRFTIME('%Y-%m-%d %H:%M:%f', time/1000.0, 'unixepoch', 'localtime'), level, message FROM system_log