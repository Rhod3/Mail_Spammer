# Mail_Spammer
## Intoduction
Mail spammer java application developped for RES course @HEIG-VD

## What does it do ?
This little application has been made to automatically forge emails. It will first take a random subset of victims emails which are stored in a files and an other victims who will send the
forged email. Then, the application will create the forged email and send it to the subset of victims. The core of the forge email is randomly selected from on of the folder cointaining all the prefabricated messages. Each message is in one unique file.

## How to use it ?
To use it, you firstly need to have to different folders. 
The firt one will contain one .txt file with all the email-addresses of the different victims.
The second will have numerous .txt files, each of them containing a prefabricated message.

Then you will have to specify the numbers of groups that you want to generate. A group is composed of a random number of victims that will receive the forged email and an other victim that will send it.
You can set up the port number and the name of the mail server right in the main().

Then, for each group, 
