# Mail_Spammer
## Intoduction
Mail spammer java application developped for RES course @HEIG-VD

## What does it do ?
This little application has been made to automatically send spam emails. It will construct a user determined number of groups from a file containing all the victims email adresses. A group consist of a sender and a bunch of victims.

The application will then create the forged email and send it to the subset of victims. The mail body is randomly selected from one of the folder cointaining all the spam messages. Each message is in his file.

## How to use it ?
By default, the application will try to communicate with local SMTP server (localhost) at the port 25000. If you want, you can modify the SMTP server by modifying the config file accordingly.

If you want to change the pool of victims email adresses, you can modify the email_adresses.txt file accordingly.
If you want to change the messages sent, you can modify them in the email_messages folder.

When the app is runned, you will be asked to enter a number of group. Then, this number of group will be randomly generated and a mail will be send to every victims from those groups.
