package us.alberto.models;

import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;

public class EmailMessage {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Message message;
    private MimeMessage parser;

    public EmailMessage(Message m) {
        this.message = m;
    }

    public Message getMensaje() {
        return message;
    }

    public String getAsunto() {
        try {
            return message.getSubject();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getContenido() throws Exception {
        String result = "";
        MimeMessageParser parser = new MimeMessageParser((MimeMessage) message);
        parser.parse();
        if (message.isMimeType("text/plain")) {
            result = parser.getPlainContent();
        }
        else if (message.isMimeType("multipart/*")) {
            result = parser.getHtmlContent();
        }
        return result;
    }

    public String getRemitente() {
        Address[] direccion = null;
        try {
            direccion = message.getFrom();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return String.valueOf(direccion[0]);
    }

    public String getFecha() {
        try {
            String fecha = sdf.format(message.getReceivedDate());
            return fecha;
        } catch (MessagingException e) {
            e.printStackTrace();
            return "";
        }
    }
}