# advertiser-api-client
A Java Client for Advertiser API

#Before using
To use the code for obtaining the data for your program you need to obtain the authentication information first:
* CONNECT_ID
* SECRET_KEY  
You can find your Connect ID and secret key in the zanox Marketplace.

#Build
mvn install

#Run
**Get report data:**<br />
java -jar target/advertiser-api-client-1.0-SNAPSHOT.jar  --[header|url] reportservice PROGRAM_ID CONNECT_ID SECRET_KEY GROUP_BY

e.g.
java -jar target/advertiser-api-client-1.0-SNAPSHOT.jar --header reportservice 1803 476F47B42F2819102E7A a9A63Af80b9D47+8ae84a3929776ba/9479fCE46 day


**Get partnercode details:**<br />
java -jar target/advertiser-api-client-1.0-SNAPSHOT.jar  --[header|url] partnercodeservice CONNECT_ID SECRET_KEY COMMA_SEPARATED_LIST_OF_PARTNERCODES

e.g.
java -jar target/advertiser-api-client-1.0-SNAPSHOT.jar --url partnercodeservice 476F47B42F2819102E7A a9A63Af80b9D47+8ae84a3929776ba/9479fCE46 39976328C1486151649T,232632221C1397895T,287457C42819842T

(Note: the Connect ID and secret key given above are for for illustrative purposes only)
