#! bin/bash

# # Script usage
# bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password

# # Example
# bash scripts/host_usage.sh localhost 5432 host_agent postgres password

PSQL_HOST=$1
PSQL_PORT=$2
DB_NAME=$3
PSQL_USER=$4


if [[ $5 ]];then
export PGPASSWORD=$5
fi

# checking if zero
if [[ -z $PSQL_HOST ]] || [[ -z $PSQL_PORT ]] || [[ -z $DB_NAME ]] || [[ -z $PSQL_USER ]] || [[ -z $PGPASSWORD ]]; then
  echo "./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password";
  exit 1;
fi


meminfo=$(cat /proc/meminfo)
hostname=$(hostname -f);

timestamp=$(vmstat -t | grep -o -e '[0-9]\{4\}\-.*' | xargs)
memory_free=$(echo "$meminfo" | egrep "^MemFree:" | awk '{print $2}' | xargs)
cpu_idle=$(vmstat -t | grep  -Po '\d+(?=\s+\d+\s+\d+\s+\d+\-.*)')
cpu_kernel=$(vmstat -t | grep  -Po '\d+(?=\s+\d+\s+\d+\s+\d+\s+\d+\-.*)')
disk_io=$(vmstat -d | grep  -Po '\d+(?=\s+\d+$)')
disk_available=$(df -BM / | grep  -Po '\d+(?=M\s+\d+%)' )

insert_stmt="WITH idt AS (SELECT id FROM host_info WHERE hostname='$hostname') INSERT INTO host_usage (\"timestamp\", \"cpu_idle\", \"cpu_kernel\", \"disk_io\", \"disk_available\",\"memory_free\", \"host_id\") VALUES ('$timestamp', $cpu_idle, $cpu_kernel, $disk_io, $disk_available, $memory_free, (select id from idt));"

psql -h $PSQL_HOST -p $PSQL_PORT  -d $DB_NAME -U $PSQL_USER  -c "$insert_stmt";

exit $?

