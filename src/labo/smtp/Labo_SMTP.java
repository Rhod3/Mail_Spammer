package labo.smtp;

import java.io.*;
import java.net.*;

public class Labo_SMTP {

    private final static int SMTP_PORT = 25;
    private final static String MAIL_SERVER = "mailcl0.heig-vd.ch";
    private final static String SENDER_EMAIL = "feur@heig-vd.ch";
    private final static String RECEIVER_EMAIL = "nicolas.rod@heig-vd.ch";
    private final static String EMAIL_MESSAGE = "From: bob@areyousure.com\n"
            + "To: nicolas.rod@heig-vd.ch\n"
            + "Subject: Demo\n\n"
            + "THIS IS A TEST FEUR !";

    public static void main(String[] args) throws Exception {

        Socket socket = null;

        try {

// Establish a TCP connection with the mail server.
            System.out.println("Connecting socket...");
            socket = new Socket(MAIL_SERVER, SMTP_PORT);

// Create a BufferedReader to read a line at a time.
            System.out.println("Creating BufferedReader and PrintWriter...");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream os = socket.getOutputStream();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));

// Read greeting from the server.
            String response = in.readLine();
            System.out.println(response);
            if (!response.startsWith("220")) {
                throw new Exception("220 reply not received from server.");
            }

// Send HELO command and get server response.
            String command = "EHLO mycompany.com";
            out.println(command);
            out.flush();
            System.out.println("Sended EHLO");
            
            while (!(response = in.readLine()).equals("250 HELP")){
                System.out.println(response);
            }
            System.out.println(response);
            
            if (!response.startsWith("250")) {
                throw new Exception("250 reply not received from server.");
            }
// Send MAIL FROM command.
            command = "MAIL FROM: " + SENDER_EMAIL;
            out.println(command);
            out.flush();
            System.out.println("Sended MAIL FROM");
            
            response = in.readLine();
            System.out.println(response);

// Send RCPT TO command.
            command = "RCPT TO: " + RECEIVER_EMAIL;
            out.println(command);
            out.flush();
            System.out.println("Sended RCPT TO");
            
            response = in.readLine();
            System.out.println(response);

// Send DATA command.
            command = "DATA";
            out.println(command);
            out.flush();
            System.out.println("Sended DATA");
            
            response = in.readLine();
            System.out.println(response);

// Send message data.
            command = EMAIL_MESSAGE;
            out.println(command);
            out.flush();
            System.out.println("Sended MAIL CONTENT");

// End with line with a single period.
            command = ".";
            out.println(command);
            out.flush();
            System.out.println("Sended END PERIOD");
            
            response = in.readLine();
            System.out.println(response);

// Send QUIT command.
            command = "quit";
            out.println(command);
            out.flush();
            System.out.println("Sended QUIT");
            
            response = in.readLine();
            System.out.println(response);
        } finally {
// close the socket
            if (socket != null) {
                socket.close();
            }
        }
    }
}
