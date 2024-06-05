FROM openjdk:8-alpine

RUN apk update && \
    apk add --no-cache mysql mysql-client supervisor maven

RUN mkdir -p /var/lib/mysql /var/run/mysqld && \
    chown -R mysql:mysql /var/lib/mysql /var/run/mysqld && \
    mysql_install_db --user=mysql --datadir=/var/lib/mysql

COPY my.cnf /etc/mysql/my.cnf

WORKDIR /app
COPY . .

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 5050

EXPOSE 3306

ENTRYPOINT ["/entrypoint.sh"]