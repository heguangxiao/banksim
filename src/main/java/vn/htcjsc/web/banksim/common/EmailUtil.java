/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htcjsc.web.banksim.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;

/**
 *
 * @author TUANPLA
 */
public class EmailUtil {

    static Logger logger = Logger.getLogger(EmailUtil.class);

    static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    /*
    
    IMAP uses port 143, but implicit SSL/TLS encrypted IMAP uses port 993.
    POP uses port 110, but implicit SSL/TLS encrypted POP uses port 995.
    SMTP uses port 25, but implicit SSL/TLS encrypted SMTP uses port 465.
    https://www.journaldev.com/2532/javamail-example-send-mail-in-java-smtp
     */
//    public static void main(String[] args) {
//        String[] toEmail = {"hr@gmail.com"};
//        testMail("hr@htcjsc.vn", "kd#ks89kjdAjd7", "mail.htcjsc.vn", "465", "SSL/TLS");
//    }
    /**
     * Utility method to send simple HTML email
     *
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    private static boolean sendEmail(String fromEmail, String fromName, Session session, String toEmail, String subject, String body) {
        boolean sended = false;
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(fromEmail, fromName, "UTF-8"));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);
            sended = true;
            System.out.println("EMail Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sended;
    }

    private static boolean simpleEmail(String fromEmail, String fromName, String smtpHostServer, String toEmail, String subject, String body) {
        System.out.println("SimpleEmail Start");
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpHostServer);
        props.put("mail.debug", "true");
        Session session = Session.getInstance(props, null);
        return sendEmail(fromEmail, fromName, session, toEmail, subject, body);
    }

    private static boolean emailTLS(String fromEmail, String password, String fromName, String smtpHostServer, String toEmail, String subject, String body) {
        /**
         * Outgoing Mail (SMTP) Server requires TLS or SSL: smtp.gmail.com (use
         * authentication) Use Authentication: Yes Port for TLS/STARTTLS: 587
         */
        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHostServer); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        //create Authenticator object to pass in Session.getInstance argument
        props.put("mail.debug", "true");
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        return EmailUtil.sendEmail(fromEmail, fromName, session, toEmail, subject, body);

    }

    private static boolean emailSSL(String fromEmail, String password, String fromName, String smtpHostServer, String toEmail, String subject, String body) {
        /**
         * Outgoing Mail (SMTP) Server requires TLS or SSL: smtp.gmail.com (use
         * authentication) Use Authentication: Yes Port for SSL: 465
         */
        System.out.println("SSLEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHostServer); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.smtp.port", "465"); //SMTP Port
        props.put("mail.debug", "true");
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        System.out.println("fromEmail:" + fromEmail);
        System.out.println("password:" + password);
        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");
        return EmailUtil.sendEmail(fromEmail, fromName, session, toEmail, subject, body);
//        EmailUtil.sendAttachmentEmail(session, toEmail, "SSLEmail Testing Subject with Attachment", "SSLEmail Testing Body with Attachment");
//        EmailUtil.sendImageEmail(session, toEmail, "SSLEmail Testing Subject with Image", "SSLEmail Testing Body with Image");

    }

    /**
     * Utility method to send image in email body
     *
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    private static void sendImageEmail(Session session, String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Second part is image attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "image.png";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            //Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "image_id");
            multipart.addBodyPart(messageBodyPart);
            //third part for displaying image in the email body
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<h1>Attached Image</h1>"
                    + "<img src='cid:image_id'>", "text/html");
            multipart.addBodyPart(messageBodyPart);
            //Set the multipart message to the email message
            msg.setContent(multipart);
            // Send message
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with image!!");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Utility method to send email with attachment
     *
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    private static void sendAttachmentEmail(Session session, String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            messageBodyPart.setText(body);
            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Second part is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "abc.txt";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            msg.setContent(multipart);
            // Send message
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with attachment!!");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static boolean testMail(String fromEmail, String fromPass, String fromName, String smtpHostServer, String port, String encrypt, String subject, String body) {
        boolean flag = false;
        try {
            if (encrypt.equalsIgnoreCase("None")) {
                flag = simpleEmail(fromEmail, fromName, smtpHostServer, fromEmail, subject, body);
            }
            // Use the following if you need SSL
            if (encrypt.equalsIgnoreCase("SSL/TLS") || encrypt.equalsIgnoreCase("STARTTLS")) {
                if (port.equals("587")) {
                    flag = emailTLS(fromEmail, fromPass, fromName, smtpHostServer, fromEmail, subject, body);
                }
                if (port.equals("465")) {
                    flag = emailSSL(fromEmail, fromPass, fromName, smtpHostServer, fromEmail, subject, body);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean sendEmail(String fromEmail, String fromPass, String fromName, String smtpHostServer, String port, String encrypt, String subject, String body) {
        boolean flag = false;
        try {
            if (encrypt.equalsIgnoreCase("None")) {
                flag = simpleEmail(fromEmail, fromName, smtpHostServer, fromEmail, subject, body);
            }
            // Use the following if you need SSL
            if (encrypt.equalsIgnoreCase("SSL/TLS") || encrypt.equalsIgnoreCase("STARTTLS")) {
                if (port.equals("587")) {
                    flag = emailTLS(fromEmail, fromPass, fromName, smtpHostServer, fromEmail, subject, body);
                }
                if (port.equals("465")) {
                    flag = emailSSL(fromEmail, fromPass, fromName, smtpHostServer, fromEmail, subject, body);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private static ArrayList<String> parseListMail(String input) {
        String[] arrMail = input.split(",|;|\\n|\\s");
        ArrayList<String> list = new ArrayList<>();
        for (String one : arrMail) {
            if (!Tool.checkNull(one) && one.contains("@")) {
                list.add(one.trim());
            }
        }
        return list;
    }

//    public static boolean sendMail(String fromEmail, String fromPassMail,
//            String subject, String content, ArrayList<String> toEmail, String fromName) {
//        boolean flag = false;
//        try {
//            Properties props = new Properties();
//            props.put("mail.smtp.host", MyConfig.MAIL_HOST); //MyContext.MAIL_HOST
//            props.put("mail.smtp.socketFactory.port", "465");
//            props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.ssl.enable", "true");
//            props.put("mail.smtp.port", "465");
//            props.put("mail.debug", false);
//            props.put("mail.smtp.starttls.enable", "true");
//            props.setProperty("mail.smtp.socketFactory.fallback", "false");
//            // Get the default Session object.
//            Session session = Session.getInstance(props);
//            // Create a default MimeMessage object.
//            MimeMessage messageSend = new MimeMessage(session);
//            // Set the RFC 822 "From" header field using the
//            // value of the InternetAddress.getLocalAddress method.
//            messageSend.setFrom(new InternetAddress(fromEmail, fromName));
//
//            Address[] addresses = new Address[toEmail.size()];
//            for (int i = 0; i < toEmail.size(); i++) {
//                Address address = new InternetAddress(toEmail.get(i));
//                addresses[i] = address;
//                // Add the given addresses to the specified recipient type.
//                messageSend.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail.get(i)));
//            }
//            // Set the "Subject" header field.
//            messageSend.setSubject(subject, "utf-8");
//            // Sets the given String as this part's content,
//            // with a MIME type of "text/plain".
//            Multipart mp = new MimeMultipart("alternative");
//            MimeBodyPart mbp = new MimeBodyPart();
//            mbp.setContent(content, "text/html;charset=utf-8");
//            mp.addBodyPart(mbp);
//            messageSend.setContent(mp);
//            messageSend.saveChanges();
//            // Send message
//            Transport transport = session.getTransport("smtp");
////            transport.connect(MyConfig.MAIL_HOST, MyConfig.SMTP_MAIL, MyConfig.SMTP_PASS);
//            transport.connect(fromEmail, fromPassMail);
//            transport.sendMessage(messageSend, addresses);
//            transport.close();
//            flag = true;
//        } catch (Exception e) {
//            logger.error(Tool.getLogMessage(e));
//            e.printStackTrace();
//        }
//        return flag;
//    }
}
