#! bin/bash


# ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password

# # Example
# ./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"


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

total_mem=$(echo "$meminfo" | egrep "^MemTotal:" | awk '{print $2}' | xargs)

