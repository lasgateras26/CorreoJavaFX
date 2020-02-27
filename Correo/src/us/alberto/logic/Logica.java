package us.alberto.logic;

import com.sun.mail.util.MailSSLSocketFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.web.HTMLEditor;
import us.alberto.models.EmailAccount;
import us.alberto.models.EmailMessage;
import us.alberto.models.EmailTreeItem;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class Logica {

    private static Logica INSTANCE = null;

    private ObservableList<EmailMessage> listaMensajes = FXCollections.observableArrayList();
    private ObservableList<EmailAccount> listaCuentas = FXCollections.observableArrayList();

    private Logica() {
    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();
        return INSTANCE;
    }

    public void getMessage(String email, String password) {
        Properties prop = new Properties();
        prop.setProperty("mail.store.protocol", "imaps");
        Session sesion = Session.getInstance(prop);
        Store store;
        Folder folder = null;
        Message[] mens;

        try {
            store = sesion.getStore("imaps");
            store.connect("imap.googlemail.com", email, password);
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            mens = folder.getMessages();

            for (int i = 0; i < mens.length; i++) {
                EmailMessage m = new EmailMessage(mens[i]);
                listaMensajes.add(m);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<EmailMessage> cargarMensajes(Folder folder) {
        listaMensajes.clear();
        try {
            if (folder != null && folder.getType() == 3) {
                if (!folder.isOpen())
                    folder.open(Folder.READ_WRITE);

                Message[] vectorMensajes = folder.getMessages();
                for (int i = 0; i < vectorMensajes.length; i++) {
                    EmailMessage mensaje = new EmailMessage(vectorMensajes[i]);
                    listaMensajes.add(mensaje);
                }
            }
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
        return listaMensajes;
    }

    public ObservableList<EmailMessage> getListaMensajes() {
        return listaMensajes;
    }

    public void añadirCuenta(EmailAccount cuenta) {
        listaCuentas.add(cuenta);
    }

    public ObservableList<EmailAccount> getListaCuentas() {
        return listaCuentas;
    }

    public String getMessageContent(EmailMessage correo) throws MessagingException {
        Message message = correo.getMensaje();
        try {
            Object content = message.getContent();
            if (content instanceof Multipart) {
                StringBuffer messageContent = new StringBuffer();
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    Part part = multipart.getBodyPart(i);
                    if (part.isMimeType("text/plain")) {
                        messageContent.append(part.getContent().toString());
                    }
                }
                return messageContent.toString();
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public EmailTreeItem cargarCarpetas() {
        EmailTreeItem nodoPadre = new EmailTreeItem(null, null, null, null);
        for (int i = 0; i < listaCuentas.size(); i++) {
            EmailTreeItem itemCuenta = new EmailTreeItem(listaCuentas.get(i).getEmail(), listaCuentas.get(i), null, listaCuentas.get(i).getStore());
            nodoPadre.getChildren().add(itemCuenta);
            try {
                Folder[] vectorCarpetas = listaCuentas.get(i).getStore().getDefaultFolder().list();
                itemCuenta.setExpanded(true);
                rellenarCarpetas(vectorCarpetas, itemCuenta, listaCuentas.get(i));
            } catch (MessagingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return nodoPadre;
    }

    public void rellenarCarpetas(Folder[] vectorCarpetas, EmailTreeItem itemCuenta, EmailAccount emailAccount) throws MessagingException {
        for (Folder folder : vectorCarpetas) {
            EmailTreeItem item = new EmailTreeItem(folder.getName(), emailAccount, folder, emailAccount.getStore());
            itemCuenta.getChildren().add(item);
            if (folder.list().length > 0) {
                rellenarCarpetas(folder.list(), itemCuenta, emailAccount);
            }
        }
    }

    public void escribirCorreo(EmailAccount emailAccount, String emisor, String receptor, String asunto, HTMLEditor mensaje){
        for(int i = 0; i < listaCuentas.size(); i++){
            if(listaCuentas.get(i).getEmail().equals(emisor)){
                emailAccount = getListaCuentas().get(i);
            }
        }
        try{
            Session session = getSession(emailAccount);
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(emisor));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            mimeMessage.setSubject(asunto);
            mimeMessage.setContent(mensaje.getHtmlText(), "text/html");
            Transport.send(mimeMessage);
        }
        catch(MessagingException e){
            e.printStackTrace();
        }
    }

    public Session getSession(EmailAccount emailAccount) {
        Properties properties = new Properties();
        MailSSLSocketFactory msf = null;
        try{
            msf = new MailSSLSocketFactory();
        }
        catch(GeneralSecurityException e){
            e.printStackTrace();
        }
        msf.setTrustAllHosts(true);
        properties.put("mail.imaps.ssl.trust", "*");
        properties.put("mail.imaps.ssl.socketFactory", msf);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String email = emailAccount.getEmail();
        String password = emailAccount.getPassword();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        return session;
    }
}