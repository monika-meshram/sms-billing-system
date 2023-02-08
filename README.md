# sms-billing-system
 
***Commands to Start the Application using Docker :***

Step 1 : To create a jar file BillingSystem-0.0.1-SNAPSHOT.jar

Command : mvn clean install -Dmaven.test.skip=true

Step 2 : To create a Docker Image named 'billing' using Dockerfile

Command : docker build <Path to Project>/BillingSystem -t billing

Step 3 : Run the container named 'billingContainer' using Image ID

Command : docker run -it -p 9550:9550 --name billingContainer billingsystem

----------------------------------------------------------------------------------------------------------

***Endpoints :***

// To send SMS for a Customer ID - 1
 

curl --location --request POST 'http://localhost:9550/billingSystem/message' \
--header 'Content-Type: application/json' \
--data-raw '{
    "customerId":"1",
    "message":"new msg new"
}'

// To Get the Total Bill Amount for current month for Customer ID - 1
 
 
curl --location --request GET 'http://localhost:9550/billingSystem/customer/1/bill'
