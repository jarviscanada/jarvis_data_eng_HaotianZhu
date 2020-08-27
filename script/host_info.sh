#! bin/bash

lscpu_out=$(lscpu)
meminfo=$(cat /proc/meminfo)


hostname=$(hostname -f)

cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)

cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)

cpu_model_str=$(echo "$lscpu_out"  | egrep "Model name:")
cpu_model_substr=${cpu_model_str:11}
cpu_model=$(echo "$cpu_model_substr"| xargs)

cpu_mhz=$(echo "$lscpu_out"  | egrep "CPU MHz:" | awk '{print $3}' | xargs)

l2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}' | xargs)




total_mem=$(echo "$meminfo" | egrep "^MemTotal:" | awk '{print $2}' | argx)

timestamp=$(vmstat -t | grep -o -e '[0-9]\{4\}\-.*' | argx)

#usage
memory_free=$(echo "$meminfo" | egrep "^MemFree:" | awk '{print $2}' | argx)
cpu_idle=$(vmstat -t | grep  -Po '\d+(?=\s+\d+\s+\d+\s+\d+\-.*)')
cpu_kernel=$(vmstat -t | grep  -Po '\d+(?=\s+\d+\s+\d+\s+\d+\s+\d+\-.*)')
disk_io=$(vmstat -d | grep  -Po '\d+(?=\s+\d+$)')
disk_available=$(df -BM / | grep  -Po '\d+(?=M\s+\d+%)' )


echo "$hostname|$cpu_number|$cpu_architecture|$cpu_model|$cpu_mhz|$l2_cache|$cpu_idle|$cpu_kernel|$disk_io|$disk_available"

