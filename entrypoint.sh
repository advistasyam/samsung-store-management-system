#!/bin/sh

/usr/bin/mysqld &

# Wait for MySQL to start
sleep 10

# Create the database
mysql -u root -e "CREATE DATABASE IF NOT EXISTS samsung_store_management_system;"

# Run Liquibase update
mvn liquibase:update

# Compile the Java application
mvn clean install

# Run the Java application
mvn exec:java -Dexec.mainClass="com.samsung.Main"