#! bin/bash



# # Script usage
# bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password

# # Example
# bash scripts/host_usage.sh localhost 5432 host_agent postgres password

meminfo=$(cat /proc/meminfo)


timestamp=$(vmstat -t | grep -o -e '[0-9]\{4\}\-.*' | xargs)

#usage
memory_free=$(echo "$meminfo" | egrep "^MemFree:" | awk '{print $2}' | xargs)
cpu_idle=$(vmstat -t | grep  -Po '\d+(?=\s+\d+\s+\d+\s+\d+\-.*)')
cpu_kernel=$(vmstat -t | grep  -Po '\d+(?=\s+\d+\s+\d+\s+\d+\s+\d+\-.*)')
disk_io=$(vmstat -d | grep  -Po '\d+(?=\s+\d+$)')
disk_available=$(df -BM / | grep  -Po '\d+(?=M\s+\d+%)' )

# testing
#echo "$hostname|$cpu_number|$cpu_architecture|$cpu_model|$cpu_mhz|$l2_cache|$cpu_idle|$cpu_kernel|$disk_io|$disk_available"
