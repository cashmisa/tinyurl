# tinyurl

A simple app built with Spring Boot + Vue + MySQL

**what it does**

- takes a full length url and convert into a short url
- takes the converted short url and turn it back to the original url
- redirect to original url when user access the converted url

**run backend**

- `cd tinyurl/tinyurl`
- `mvn spring-boot:run`
- ports are available by now
- create a schema called tinyurl
- you can then access the backend and packaged frontend at http://localhost:8899/

**run frontend separately**

- `cd tinyurl/tinyurl-f`
- `npm install`
- `quasar dev`
- you can then access the frontend which calls backend at port 8899
