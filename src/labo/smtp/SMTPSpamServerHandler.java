package labo.smtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Nicolas Rod
 * @author Basile Chatillon
 */
public class SMTPSpamServerHandler {
    
    private final int smtp_port;
    private final String mail_server;
    private final SMTPSpamGroup group;
    private final String message;

    public SMTPSpamServerHandler(SMTPSpamGroup group, String message) throws IOException {
        this.group = group;
        this.message = message;
        
        File configFile = new File(new File(".").getCanonicalPath() + File.separator
                + "src" + File.separator
                + "labo" + File.separator
                + "config" + File.separator
                + "config.txt" + File.separator);
        
        try (Scanner scanner = new Scanner(configFile)) {
            smtp_port = scanner.nextInt();
            mail_server = scanner.nextLine();
        }
        
    }

    public void sendSpam() throws IOException, Exception {

        for (String receiver_email : group.getReceivers()) {

            Socket socket = null;

            try {

                // Establish a TCP connection with the mail server.
                System.out.println("Connecting socket...");
                socket = new Socket(mail_server, smtp_port);

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
                out.print(command + "\r\n");
                out.flush();
                System.out.println("Sended EHLO");

                while (!(response = in.readLine()).startsWith("250 ")) {
                    System.out.println(response);
                }
                System.out.println(response);

                if (!response.startsWith("250")) {
                    throw new Exception("250 reply not received from server.");
                }
                
                // Send MAIL FROM command.
                command = "MAIL FROM: " + group.getSender();
                out.print(command + "\r\n");
                out.flush();
                System.out.println("To server: " + command);

                response = in.readLine();
                System.out.println(response);

                // Send RCPT TO command.
                command = "RCPT TO: " + receiver_email;
                out.print(command + "\r\n");
                out.flush();
                System.out.println("To server: " + command);

                response = in.readLine();
                System.out.println(response);

                // Send DATA command.
                command = "DATA";
                out.print(command + "\r\n");
                out.flush();
                System.out.println("To server: " + command);

                response = in.readLine();
                System.out.println(response);

                // Send message data.
                out.print("From: " + group.getSender() + "\r\n");
                out.flush();
                out.print("To: " + receiver_email + "\r\n");
                out.flush();
                command = message;
                out.print(command + "\r\n");
                out.flush();
                System.out.println("To server: " + command);

                // End with line with a single period.
                command = ".";
                out.print(command + "\r\n");
                out.flush();
                System.out.println("To server: " + command);

                response = in.readLine();
                System.out.println(response);

                // Send QUIT command.
                command = "quit";
                out.print(command + "\r\n");
                out.flush();
                System.out.println("To server: " + command);

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
}
