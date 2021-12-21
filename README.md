# account

Prerequisites:

Use DOCKER container for Oracle DB:

-pull Oracle Enterprise image:\
  docker pull store/oracle/database-enterprise:12.2.0.1\
-run Oracle container:\
   docker run -d -it --name OracleDB -P store/oracle/database-enterprise:12.2.0.1\

-check docker mapped port and use it for outside docker container connection, examplle:\
   docker port OracleDB 1521/tcp -> 0.0.0.0:32771\

-create tnsnames.ora configuration file where TNS_ADMIN env variable points to\
  export TNS_ADMIN=/u01/app/product/12.2.0.1/dbhome_1/network/admin\
  sudo mkdir -p /u01/app/product/12.2.0.1/dbhome_1/network/admin\

create file /u01/app/product/12.2.0.1/dbhome_1/network/admin/tnsnames.ora with content:\

ORCLCDB=(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=0.0.0.0)(PORT=32771))\
    (CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=ORCLCDB.localdomain)))\
ORCLPDB1=(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=0.0.0.0)(PORT=32771))\
    (CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=ORCLPDB1.localdomain)))\

default user/password are: sys/Oradoc_db1\

connect with sqlplus client:\
sqlplus sys/Oradoc_db1@ORCLCDB as sysdba\

Store account:\
curl   -X POST   -H "Content-Type: application/json"   "http://localhost:8080/account/store"   -d '{"iban":"IBAN123321","currency":"EUR","balance":50,"lastUpdateDate":"2019-08-18T14:38:40.108Z"}'

Find account:\
curl "http://localhost:8080/account/find/1"

Run app:\
./mvnw spring-boot:run

Test app:\
./mvnw test
