#! bin/bash

action=$1

#./scripts/psql_docker.sh start|stop|create [db_username][db_password]
if [[ "$(systemctl is-active docker)" = "inactive" ]];
then
  echo "starting docker..";
  systemctl start docker;
fi

if [[ $1 = "create" ]];
then
# check if jrvs-psql contain is created
# https://docs.docker.com/engine/reference/commandline/ps/
  if [[ $(docker ps -a --filter "name=jrvs-psql" | grep jrvs-psql) ]];
  then
    echo "jrvs-psql container already exists";
    echo "./scripts/psql_docker.sh start|stop"
    exit 1;
  else
    echo "creating jrvs-psql container with psql database ...";
   
    if [[ -z $2 ]] || [[ -z $3 ]];
    then
      echo "database name/password is Empty;"
      echo "./scripts/psql_docker.sh create [db_username] [db_password]"
      exit 1;
    fi

    docker volume create pgdata;
    docker run --name jrvs-psql -e POSTGRES_USER=$2 -e POSTGRES_PASSWORD=$3  -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres;
    exit $?;
  fi
fi

if [[ ! $(docker ps -a --filter "name=jrvs-psql" | grep jrvs-psql) ]];
then
  echo "please create container first;"
  echo "./scripts/psql_docker.sh create [db_username] [db_password]";
  exit 1;
fi


if [[ $1 = "start" ]];
then
  docker container start jrvs-psql
  exit $?;
elif [[ $1 = "stop" ]];
then
  docker container stop jrvs-psql
  exit $?;
else
  echo "unrecognizable;"
  echo "./scripts/psql_docker.sh start|stop|create [db_username][db_password]"
  exit 1;
fi






