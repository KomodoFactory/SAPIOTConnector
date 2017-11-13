# Alexa Skill for SAP Connection and Banf Creation #

## What is this repository for? ##
This is the current version of the alexa skill and IoT button lambda for the creation of a BANF in SAP.
It includes the source material. A documentation in form of a bachelorswork and the test results.

## How do I get set up? ##

Prerequisites:

- Amazon Webservice (AWS) Account.
- Amazon Developer Account.
- IoT Button
- Amazon Echo (Alexa)
- Running SAP System

In order to get this project to run, there will be three parts required.

- The webservice setup on the SAP-Server
- The skill deployed on Alexa
- The source material setup on an AWS Lambda

This project can be deployed on any other server structure as far as the AWS Lambda is concerned. However the given code will probably need some changes in order to work.
The "SourceMaterial" directory includes four directorys. Each one is a separate part of this system.

### Setting up the SAP Webservice ###
Unless the already created webservice was deactivated, this should not be necesseary.

1. Create a webservice in SAP. Follow this tutorial https://ososoft.atlassian.net/wiki/spaces/KURSESAP/pages/2432638/Webservices+in+SAP .
2. When it comes to creating the handler . Use the code in the "SAPWebservice" directory. 
3. May have to change the name of the class.


### Setting up the Alexa Speechlet ###
Note that you may have to setup the Alexa Skill simultaniously. (Needed at step 4)

1. Login to your Amazon Web Service Account.
2. Create a AWS Lambda Function.
3. Use the Alexa Skill Kit or Alexa Smart Home for your AWS Lambda Function.
4. When the Alexa Smart Home trigger is used, use the identifier of your Alexa Skill for the specified trigger.
5. Do a single assembly Build of the Java Application using Maven.`mvn clean compile assembly:single`
7. Upload the resulting jar file into your Lambda Function.
8. Save your function.



### Setting up the Alexa Skill ###
Note that you may have to setup the Alexa Speechlet simultaniously. (Needed at step 6)

1. Login to your Amazon Developer Account.
2. Follow the instructions to create a Skill.
3. On the Interaction Model Tab choose the Intent Shema, not the Skill Builder.
4. Copy the IntentSchema.txt into the Intent Schema section. Do the Same for the Sampel Utterance Section and SampleUtterance.txt.
5. Setup the Custom Slot. Name it "Artikel" and Copy in the values from the Artikel.txt
6. Add the AWS Lambda Function Address in the Configuration Tab.

### Setting up the IoT Button ###

1. Check your IoT Buttons back for it's serialnumber.
2. Login to your Amazon Web Service Account.
3. Create an AWS Lambda Function.
4. Use the IoT trigger for your AWS Lambda Function.
5. Use the serialnumber of your IoT Button for the specified trigger.
6. Copy the Javascript File found in the "ButtonSAP" directory into your function. Make sure the function is set to Node.js.
7. Save your function.
8. You will now have to configure the IoT Button. Follow this guide http://docs.aws.amazon.com/iot/latest/developerguide/configure-iot.html .
