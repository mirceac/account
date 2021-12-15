# account

Store account:
curl   -X POST   -H "Content-Type: application/json"   "http://localhost:8080/account/store"   -d '{"id":1,"iban":"IBAN123321","currency":"EUR","balance":50,"lastUpdateDate":"2019-08-18T14:38:40.108Z"}'

Find account:
curl "http://localhost:8080/account/find/1"

Run app:
./mvnw spring-boot:run

Test app:
./mvnw test
