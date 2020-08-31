#! bin/bash


# ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password

# # Example
# ./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"


PSQL_HOST=$1
PSQL_PORT=$2
DB_NAME=$3
PSQL_USER=$4
PSQL_PASSWORD=$5

# checking if zero
if [[ -z $PSQL_HOST ]] || [[ -z $PSQL_PORT ]] || [[ -z $DB_NAME ]] \
  || [[ -z $PSQL_USER ]] || [[ -z $PSQL_PASSWORD ]];
  echo "./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password";
  exit 1;
fi

lscpu_out=$(lscpu);
meminfo=$(cat /proc/meminfo);

hostname=$(hostname -f);

cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs);

cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs);

cpu_model_str=$(echo "$lscpu_out"  | egrep "Model name:");
cpu_model_substr=${cpu_model_str:11};
cpu_model=$(echo "$cpu_model_substr"| xargs);

cpu_mhz=$(echo "$lscpu_out"  | egrep "CPU MHz:" | awk '{print $3}' | xargs);

l2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}' | xargs);

total_mem=$(echo "$meminfo" | egrep "^MemTotal:" | awk '{print $2}' | xargs);


insert_stmt="INSERT INTO host_info (total_mem, hostname, L2_cache, cpu_mhz,
 cpu_model, cpu_architecture, cpu_number) VALUES (
   $total_mem, $hostname, $l2_cache, $cpu_mhz, $cpu_model, $cpu_architecture, $cpu_number
 );"

echo "$PSQL_PASSWORD" | psql -h $PSQL_HOST -p $PSQL_PORT  -d $DB_NAME -U $PSQL_USER -c $insert_stmt;

exit $?

