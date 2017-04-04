package labo.smtp;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Labo_SMTP {

    private final static int SMTP_PORT = 25000;
    private final static String MAIL_SERVER = "localhost";

    public static void main(String[] args) throws Exception {

        // Lecture du fichier
        System.out.println("Reading email adresses...");
        File emailAdressesFile = new File(new File(".").getCanonicalPath() + File.separator
                + "src" + File.separator
                + "labo" + File.separator
                + "email_adresses" + File.separator
                + "email_adresses.txt" + File.separator);
        
        File[] emailMessageFiles = new File(new File(".").getCanonicalPath() + File.separator
                + "src" + File.separator
                + "labo" + File.separator
                + "email_messages" + File.separator).listFiles();
        
        
        
        String[] messages = new String[emailMessageFiles.length];
        
        for (int i = 0; i < emailMessageFiles.length; i++){
            String tmp = "";
            Scanner scanner = new Scanner(emailMessageFiles[i]);

            while (scanner.hasNextLine()) {
                tmp += scanner.nextLine() + "\n";
            }
            messages[i] = tmp;
        }
        
        System.out.println(messages[0]);
        System.out.println(messages[1]);

        // Construction des groupes
        System.out.println("Constructing groups...");
        LinkedList<SMTPSpamGroup> groups = new SMTPSpamGroupGenerator(emailAdressesFile).generate(2);

        // Itération sur chaque groupe
        System.out.println("Sending spam");
        Random randomGenerator = new Random();
        
        for (Object o : groups) {
            SMTPSpamGroup group = (SMTPSpamGroup)o;
            int randomInt = randomGenerator.nextInt(messages.length);
            System.out.println(randomInt);
            SMTPSpamHandler s = new SMTPSpamHandler(SMTP_PORT, MAIL_SERVER, group, messages[randomInt]);
            s.spam();
        }
    }
}
