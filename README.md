Server backend for MSaas implemented as a Spring app with following dependencies:

Spring - Boot, Oauth2, JPA, JPA Rest, Security

Packaged with Gradle.

Can be built as:

gradle build

Then can be ran as:

java -jar msaas.jar



Example of authenticating a client with oauth:

GET a token:

curl -X POST -vu clientapp:123456 http://localhost:7777/oauth/token -H "Accept: application/json" -d "password=mypassw&username=Cosmin&grant_type=password&scope=read%20write&client_secret=123456&client_id=clientapp"

Then use the token to get the protected resource:

curl http://localhost:8080/greeting -H "Authorization: Bearer token_received_previously"


