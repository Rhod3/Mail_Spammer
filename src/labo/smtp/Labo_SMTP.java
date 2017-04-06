package labo.smtp;

import java.util.Scanner;

public class Labo_SMTP {



    public static void main(String[] args) throws Exception {

        System.out.println("How many spam groups would you like to spam ? :)");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        
        SMTPSpamHandler.spam(num);
    }
}
