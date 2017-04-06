package labo.smtp;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Basile Chatillon
 * @author Nicolas Rod
 */
public class SMTPSpamHandler {

    public static void spam(int numberOfGroup) throws IOException, Exception {
        
        // Files opening
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

        // Constuction of the mail body array
        for (int i = 0; i < emailMessageFiles.length; i++) {
            String tmp = "";
            Scanner scanner = new Scanner(emailMessageFiles[i]);

            while (scanner.hasNextLine()) {
                tmp += scanner.nextLine() + "\n";
                System.out.println(tmp);
            }
            messages[i] = tmp;
        }

        // Groups construction
        System.out.println("Constructing groups...");
        LinkedList<SMTPSpamGroup> groups = new SMTPSpamGroupGenerator(emailAdressesFile).generate(numberOfGroup);

        // For each group, we sendSpam them with a random message :)
        System.out.println("Sending spam");
        Random randomGenerator = new Random();

        for (SMTPSpamGroup group : groups) {
            int randomInt = randomGenerator.nextInt(messages.length);
            System.out.println(randomInt);
            SMTPSpamServerHandler s = new SMTPSpamServerHandler(group, messages[randomInt]);
            s.sendSpam();
        }
    }
}
