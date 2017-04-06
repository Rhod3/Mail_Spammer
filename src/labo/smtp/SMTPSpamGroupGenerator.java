package labo.smtp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
     * Generate a number i of group from the email adresses given in the constructor
     * @param i Number of group to generate
     * @return A LinkedList of SMTPSpamGroup
     */
    public LinkedList<SMTPSpamGroup> generate(int i) {
        LinkedList<SMTPSpamGroup> result = new LinkedList<>();
        Random randomGenerator = new Random();

        int j = 0;
        // Group construction
        while (j < i) {
            // Sender selection
            int idOfSender = randomGenerator.nextInt(emailAdresses.size());
            String sender = emailAdresses.get(idOfSender);
            
            int numberOfReceivers = randomGenerator.nextInt(emailAdresses.size() - 3) + 2;
            int k = 0;
            LinkedList<String> receivers = new LinkedList<>();
            
            // Receiver selection
            while (k < numberOfReceivers){
                int m = randomGenerator.nextInt(emailAdresses.size() - 1);
                if (!receivers.contains(emailAdresses.get(m)) && !emailAdresses.get(m).equals(sender)){
                    receivers.add(emailAdresses.get(m));
                    k++;
                }
            }
            result.add(new SMTPSpamGroup(sender, receivers));
            j++;
        }

        return result;
    }
}
