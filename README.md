Server backend for MSaas implemented as a Spring app with following dependencies:

Spring - Boot, Oauth2, JPA, JPA Rest, Security

Packaged with Gradle.

Can be built as:

gradle build

Then can be ran as:

java -jar msaas.jar



Example of authenticating a client with oauth:

GET a token:

curl -X POST -vu mSaasWebClient:jhfads07ay7qwhcrq6787436ghrc8q3746fgx8347fgj97634gfx9j3467fg927 http://localhost:7777/oauth/token -H "Accept: application/json" -d "password=cosmin&username=Cosmin&grant_type=password&scope=read%20write&client_secret=jhfads07ay7qwhcrq6787436ghrc8q3746fgx8347fgj97634gfx9j3467fg927&client_id=mSaasWebClient"

Then use the token to get the protected resource:

curl http://localhost:7777/server/customerDetails -H "Authorization: Bearer 91116c05-4c57-43fe-a18d-a2ecd775a362"


