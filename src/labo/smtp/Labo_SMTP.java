package labo.smtp;

import java.io.File;
import java.util.LinkedList;

public class Labo_SMTP {

    private final static int SMTP_PORT = 25000;
    private final static String MAIL_SERVER = "localhost";
    private final static String SENDER_EMAIL = "feur@heig-vd.ch";
    private final static String RECEIVER_EMAIL = "nicolas.rod@heig-vd.ch";
    private final static String EMAIL_MESSAGE = "From: bob@areyousure.com\n"
            + "To: nicolas.rod@heig-vd.ch\n"
            + "Subject: Demo\n\n"
            + "THIS IS A TEST FEUR !";

    public static void main(String[] args) throws Exception {

        // Lecture du fichier
        System.out.println("Lecture");
        File f = new File(new File(".").getCanonicalPath() + File.separator
                + "src" + File.separator
                + "labo" + File.separator
                + "email_adresses" + File.separator
                + "email_adresses.txt" + File.separator);

        // Construction des groupes
        System.out.println("Construction");
        LinkedList<SMTPSpamGroup> groups = new SMTPSpamGroupGenerator(f).generate(2);

        // itération sur chaque groupe
        System.out.println("Itération");
        for (Object o : groups) {
            SMTPSpamGroup group = (SMTPSpamGroup)o;
            SMTPSpamHandler s = new SMTPSpamHandler(SMTP_PORT, MAIL_SERVER, group, EMAIL_MESSAGE);
            s.spam();
        }
    }
}
