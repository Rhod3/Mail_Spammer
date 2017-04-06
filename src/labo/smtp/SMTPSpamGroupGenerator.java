package labo.smtp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Rhod3
 */
public class SMTPSpamGroupGenerator {

    private final ArrayList<String> emailAdresses;

    /**
     * Construct a array of all the adresses to be ready to generate a given number
     * of SMTPSpamGroup
     * @param email_adresses File that contains all the adresses, one by line
     */
    public SMTPSpamGroupGenerator(File email_adresses) throws FileNotFoundException {
        emailAdresses = new ArrayList<>();
        Scanner scanner = new Scanner(email_adresses);

        while (scanner.hasNextLine()) {
            emailAdresses.add(scanner.nextLine());
        }
    }

    /**
     * Generate a number of groups from the email adresses given in the constructor
     * @param numberOfGroup Number of group to generate
     * @return A LinkedList of SMTPSpamGroup
     */
    public LinkedList<SMTPSpamGroup> generate(int numberOfGroup) {
        LinkedList<SMTPSpamGroup> result = new LinkedList<>();
        Random randomGenerator = new Random();

        int j = 0;
        // Group construction
        while (j < numberOfGroup) {
            
            Collections.shuffle(emailAdresses);
            String sender = emailAdresses.get(0);
            
            int numberOfReceivers = randomGenerator.nextInt(emailAdresses.size() - 3) + 2;
            LinkedList<String> receivers = new LinkedList<>();
            
            for (int k = 1; k < numberOfReceivers; k++){
                receivers.add(emailAdresses.get(k));
            }
            
            result.add(new SMTPSpamGroup(sender, receivers));
            j++;
        }

        return result;
    }
}
