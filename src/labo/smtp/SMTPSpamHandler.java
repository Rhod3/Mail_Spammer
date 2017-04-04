package labo.smtp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Nicolas Rod
 * @author Basile Chatillon
 */
public class SMTPSpamHandler {

    public SMTPSpamHandler(int smtp_port, String mail_server, SMTPSpamGroup group, String message) {
        this.smtp_port = smtp_port;
        this.mail_server = mail_server;
        this.group = group;
        this.message = message;
    }

    private final int smtp_port;
    private final String mail_server;
    private final SMTPSpamGroup group;
    private final String message;

    public void spam() throws IOException, Exception {

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
                System.out.println("Sended MAIL FROM");

                response = in.readLine();
                System.out.println(response);

// Send RCPT TO command.
                command = "RCPT TO: " + receiver_email;
                out.print(command + "\r\n");
                out.flush();
                System.out.println("Sended RCPT TO");

                response = in.readLine();
                System.out.println(response);

// Send DATA command.
                command = "DATA";
                out.print(command + "\r\n");
                out.flush();
                System.out.println("Sended DATA");

                response = in.readLine();
                System.out.println(response);

// Send message data.
                command = message;
                out.print(command + "\r\n");
                out.flush();
                System.out.println("Sended MAIL CONTENT");

// End with line with a single period.
                command = ".";
                out.print(command + "\r\n");
                out.flush();
                System.out.println("Sended END PERIOD");

                response = in.readLine();
                System.out.println(response);

// Send QUIT command.
                command = "quit";
                out.print(command + "\r\n");
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
}
