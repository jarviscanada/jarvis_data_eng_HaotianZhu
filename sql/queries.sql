-- Group hosts by hardware info

SELECT cpu_number, id AS "host_id",total_mem
FROM host_info 
ORDER BY cpu_number DESC, total_mem DESC;


-- Sample query output
-- host_id, host_name, timestamp,avg_used_mem_percentage

-- https://wiki.postgresql.org/wiki/Round_time

CREATE OR REPLACE FUNCTION round_time(timestamp with time zone) 
RETURNS timestamp with time zone AS $$ 
  SELECT date_trunc('hour', $1) + interval '5 min' * round(date_part('minute', $1) / 5.0) 
$$ LANGUAGE sql;



WITH used_mem_t("id", "used_mem", "ts", "host_name") AS (
	SELECT host_info.id AS id, 100.0*(total_mem - memory_free)/total_mem AS used_mem, round_time(host_usage.timestamp) AS ts, host_info.hostname AS host_name
	FROM host_info, host_usage
	WHERE host_info.id = host_usage.host_id 
	ORDER BY id, ts
) 
SELECT id, host_name, ts as timestamp, to_char(SUM(used_mem)/5.0,'990D99%') AS avg_used_mem_percentage
FROM used_mem_t
GROUP BY id, host_name, ts
ORDER BY id, ts;





WITH check_t("id", "ts", "nums") AS (
	SELECT host_info.id AS id, round_time(host_usage.timestamp) AS "ts", COUNT(*)
	FROM host_info, host_usage
	WHERE host_info.id = host_usage.host_id 
	GROUP BY id, ts
	ORDER BY id, ts
) 
SELECT *
FROM check_t
WHERE nums < 5;