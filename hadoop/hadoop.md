1. create wdi_gs table

```sql
DROP TABLE IF EXISTS wdi_gs

CREATE EXTERNAL TABLE wdi_gs
(year INTEGER, countryName STRING, countryCode STRING, indicatorName STRING, indicatorCode STRING, indicatorValue FLOAT)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'
LOCATION 'gs://jarvis_data_eng_haotianzhu/dataset/wdi_2016'
TBLPROPERTIES ("skip.header.line.count"="1")
```

2. create wdi_csv_text table

```sql
DROP TABLE IF EXISTS wdi_csv_text

CREATE EXTERNAL TABLE wdi_csv_text
(year INTEGER, countryName STRING, countryCode STRING, indicatorName STRING, indicatorCode STRING, indicatorValue FLOAT)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'
LOCATION 'hdfs:///user/hive/wdi/wdi_csv_text'
```

3. insert data from wdi_gs into wdi_csv_text, data will be stored in `hdfs:///user/hive/wdi/wdi_csv_text`

```sql
INSERT OVERWRITE TABLE wdi_csv_text
SELECT * FROM wdi_gs
```



```


Beeline version 2.3.7 by Apache Hive
0: jdbc:hive2://cluster-c54d-m:10000> select count(*) from wdi_csv_text;
+-----------+
|    _c0    |
+-----------+
| 21759408  |
+-----------+
1 row selected (3.23 seconds)
0: jdbc:hive2://cluster-c54d-m:10000> select count(*) from wdi_csv_text;
+-----------+
|    _c0    |
+-----------+
| 21759408  |
+-----------+
1 row selected (0.39 seconds)


As we can see from above, the second query is faster than the first one because of the file system cache.

When we run the same query in the worker node, the speed is as fast as the second query in the master node.

After we clear the cache, we re-run the query, and the query has the same speed as the first one in the master node.

The worker and master nodes share the same cache.
```



```

cd ~
hdfs  dfs -get  hdfs:///user/hive/wdi/wdi_csv_text .
cd wdi_csv_text
#calculate current directory size
du -ch .
#1.8G	total

#clear fs cache
echo 3 | sudo tee /proc/sys/vm/drop_caches
#bash row count
date +%s && cat * | wc && date +%s
```



```

Discuss the performance result between the bash and Hive approaches.

The performance result with the Hive approach is much better than that with the bash approach
```



1. create a wdi_opencsv_gs table

```sql
DROP TABLE IF EXISTS wdi_opencsv_gs

CREATE EXTERNAL TABLE wdi_opencsv_gs 
(year INTEGER, countryName STRING, countryCode STRING, indicatorName STRING, indicatorCode STRING, indicatorValue FLOAT)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
LOCATION 'gs://jarvis_data_eng_haotianzhu/dataset/wdi_2016'
TBLPROPERTIES ("skip.header.line.count"="1")
```

2. create wdi_opencsv_text with OpenCSVSerde

```sql
DROP TABLE IF EXISTS wdi_opencsv_text
CREATE EXTERNAL TABLE wdi_opencsv_text
(year INTEGER, countryName STRING, countryCode STRING, indicatorName STRING, indicatorCode STRING, indicatorValue FLOAT)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
LOCATION 'hdfs:///user/hive/wdi/wdi_opencsv_text'
```

3. insert data from wdi_opencsv_gs  into wdi_opencsv_text, and data will be stored in `hdfs:///user/hive/wdi/wdi_opencsv_text`

```
INSERT OVERWRITE TABLE wdi_opencsv_text
SELECT * FROM wdi_opencsv_gs
```



1. create a view from wdi_csv_text

```sql
DROP VIEW IF EXISTS wdi_opencsv_text_view
CREATE VIEW IF NOT EXISTS wdi_opencsv_text_view
AS
SELECT year, countryName, countryCode, indicatorName, indicatorCode, Cast(indicatorValue AS FLOAT) AS indicatorValue
FROM wdi_csv_text
```

2. find the canada GDP growth in 2015 year

```sql
select  indicatorValue AS GDP_growth_value, year, countryName 
from wdi_opencsv_text_view
where indicatorname like "GDP growth (annual %)" and year = 2015 and countryName="Canada"
```

3. create wdi_opencsv_text_partitions table which is partitioned by year

```sql
DROP TABLE IF EXISTS wdi_opencsv_text_partitions
CREATE EXTERNAL TABLE wdi_opencsv_text_partitions 
(year INTEGER, countryName STRING, countryCode STRING, indicatorName STRING, indicatorCode STRING, indicatorValue FLOAT)
PARTITIONED by(y STRING)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
LOCATION 'hdfs:///user/hive/wdi/wdi_opencsv_text
```

4. insert data from wdi_opencsv_text into wdi_opencsv_text_partitions indexed by year.

```sql
set hive.exec.dynamic.partition.mode=nonstrict
set hive.exec.dynamic.partition=true

INSERT OVERWRITE TABLE wdi_opencsv_text_partitions PARTITION(y)
SELECT *, year as y FROM wdi_opencsv_text
```

```bash
testfreegcp@cluster-c54d-m:~$ hdfs dfs -ls /user/hive/wdi/wdi_opencsv_text
Found 62 items
-drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1977
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1978
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1979
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1980
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1981
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1982
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1983
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1984
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1985
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1986
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1987
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1988
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1989
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1990
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1991
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1992
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1993
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1994
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1995
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1996
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1997
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1998
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=1999
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2000
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2001
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2002
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2003
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2004
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2005
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2006
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2007
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2008
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2009
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2010
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2011
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2012
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2013
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2014
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2015
drwxrwxrwt   - gpadmin hadoop          0 2020-12-03 07:20 /user/hive/wdi/wdi_opencsv_text/y=2016
```

5. find the canada GDP growth in 2015 using wdi_opencsv_text_partitions table.

```sql
select  indicatorValue AS GDP_growth_value, year, countryName 
from wdi_opencsv_text_partitions
where indicatorname like "GDP growth (annual %)" and y = 2015 and countryName="Canada"
```

1. create a wdi_csv_parquet table which is stored as PARQUET

```sql
DROP TABLE IF EXISTS wdi_csv_parquet
CREATE EXTERNAL TABLE wdi_csv_parquet 
(year INTEGER, countryName STRING, countryCode STRING, indicatorName STRING, indicatorCode STRING, indicatorValue STRING)
STORED AS PARQUET
LOCATION 'hdfs:///user/hive/wdi/wdi_csv_parquet'
```

2. insert data

```
INSERT OVERWRITE TABLE wdi_csv_parquet 
SELECT * FROM wdi_opencsv_gs
```

3. compare the file size and check if wdi_csv_parquet has all data entry

```bash
testfreegcp@cluster-c54d-m:~$ hdfs dfs -du -s -h /user/hive/wdi/wdi_csv_parquet
227.3 M  /user/hive/wdi/wdi_csv_parquet
testfreegcp@cluster-c54d-m:~$ hdfs dfs -du -s -h /user/hive/wdi/wdi_csv_text
1.7 G  /user/hive/wdi/wdi_csv_text
```

```sql
SELECT count(countryName) FROM wdi_csv_parquet
SELECT count(countryName) FROM wdi_csv_textwdi_csv_text
```

4. compare running costs by running the same query with wdi_csv_parquet

```sql
select  indicatorValue AS GDP_growth_value, year, countryName 
from wdi_csv_parquet
where indicatorname like "GDP growth (annual %)" and year = 2015 and countryName="Canada"
```

```sql
select  indicatorValue AS GDP_growth_value, year, countryName 
from wdi_csv_text
where indicatorname like "GDP growth (annual %)" and year = 2015 and countryName="Canada"
```



1. find the highest GDP growth year for each country

```sql
select v.countryName, v.indicatorValue, min(v.year)
from (select countryName, max(indicatorValue) as indicatorValue
from wdi_opencsv_text_view
where indicatorname like "GDP growth (annual %)"
group by countryName
order by countryName) AS t, wdi_opencsv_text_view v
where v.countryName=t.countryName AND v.indicatorValue=t.indicatorValue
group by v.countryName, v.indicatorValue
order by v.countryName
```

2. find GDP gowth for all countries and sort them by name and year.

```sql
select countryName, year, indicatorValue
from wdi_opencsv_text_view
where indicatorname like "GDP growth (annual %)"
order by countryName ASC, year ASC
```

