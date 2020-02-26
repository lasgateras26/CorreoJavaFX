package us.alberto.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import us.alberto.models.EmailAccount;
import us.alberto.models.EmailMessage;
import us.alberto.models.EmailTreeItem;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class Logica {

    private static Logica INSTANCE = null;

    private ObservableList<EmailMessage> listaMensajes = FXCollections.observableArrayList();
    private ObservableList<EmailAccount> listaCuentas = FXCollections.observableArrayList();

    private EmailAccount emailAccount;
    private Store store;
    private MimeMessage parser;

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

    public ObservableList getListaMensajes() {
        return listaMensajes;
    }

    public void añadirCuenta(EmailAccount cuenta) {
        listaCuentas.add(cuenta);
    }

    public ObservableList getListaCuentas() {
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

    public EmailTreeItem cargarCarpetas() throws MessagingException {
        EmailTreeItem nodoPadre = new EmailTreeItem(null, null, null, null);
        for (int i = 0; i < listaCuentas.size(); i++) {
            EmailTreeItem itemCuenta = new EmailTreeItem(listaCuentas.get(i).getEmail(), null, listaCuentas.get(i).getStore(), listaCuentas.get(i));
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

    public void rellenarCarpetas(Folder[] carpetas, EmailTreeItem itemCuenta, EmailAccount cuenta) throws MessagingException {
        for (Folder folder : carpetas) {
            EmailTreeItem item = new EmailTreeItem(folder.getName(), folder, cuenta.getStore(), cuenta);
            itemCuenta.getChildren().add(item);
            if (folder.list().length > 0) {
                rellenarCarpetas(folder.list(), itemCuenta, cuenta);
            }
        }
    }
}