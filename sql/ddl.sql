


CREATE TABLE IF NOT EXISTS  PUBLIC.host_info 
  ( 
  	"id"  SERIAL PRIMARY KEY NOT NULL,
     "timestamp"  TIMESTAMP NOT NULL, 
     "total_mem"  INT NOT NULL,
     "L2_cache" INT NOT NULL,
     "cpu_mhz" INT NOT NULL,
     "cpu_model" VARCHAR(50) NOT NULL,
     "cpu_architecture" VARCHAR(20) NOT NULL,
     "cpu_number" INT NOT NULL,
     "hostname" VARCHAR(100) NOT NULL,
     UNIQUE("hostname")

  ); 


CREATE TABLE IF NOT EXISTS  PUBLIC.host_usage 
  ( 
  	 "host_id"  INT references host_info(id) NOT NULL,
     "timestamp"  TIMESTAMP NOT NULL, 
     "memory_free"  INT NOT NULL,
     "cpu_idle" INT NOT NULL,
     "cpu_kernel" INT NOT NULL,
     "disk_io" INT NOT NULL,
     "disk_available" INT NOT NULL

  ); 
