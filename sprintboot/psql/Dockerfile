FROM postgres:9.6-alpine

ADD schema.sql /docker-entrypoint-initdb.d/

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["postgres"]