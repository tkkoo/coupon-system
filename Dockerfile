# Choose the right version
FROM mysql:latest

# Environment variables
ENV MYSQL_ROOT_PASSWORD 1234
ENV MYSQL_DATABASE coupon_example

# Initialize the database
COPY ./sql-scripts/ /docker-entrypoint-initdb.d/
