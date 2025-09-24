package com.edumanage.util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {

    // Load .env variables
    private static final Dotenv dotenv = Dotenv.load();
    private static final String SENDER_EMAIL = dotenv.get("EDU_EMAIL");
    private static final String SENDER_APP_PASSWORD = dotenv.get("EDU_PASS");

    public static void sendCredentialsEmail(String recipientEmail, String username, String password) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_APP_PASSWORD);
            }
        };
        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your New EduManage Account Credentials");

            String emailBody = "Welcome to EduManage!\n\n"
                    + "Your account has been created successfully.\n\n"
                    + "Username: " + username + "\n"
                    + "Temporary Password: " + password + "\n\n"
                    + "Please change your password after your first login.\n\n"
                    + "Thank you,\nThe EduManage Team";

            message.setText(emailBody);
            Transport.send(message);

            System.out.println("✅ Credentials email sent successfully to " + recipientEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("❌ Failed to send credentials email to " + recipientEmail);
        }
    }
}
