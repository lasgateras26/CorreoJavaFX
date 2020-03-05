package us.alberto.models;

import javafx.scene.control.TreeItem;
import javax.mail.Folder;
import javax.mail.Store;

public class EmailTreeItem extends TreeItem<String> {

    private String nombre;
    private EmailAccount emailAccount;
    private Folder folder;
    private Store store;

    public EmailTreeItem(EmailAccount emailAccount, String nombre, Folder folder, Store store){
        super(nombre);
        this.emailAccount = emailAccount;
        this.nombre = nombre;
        this.folder = folder;
        this.store = store;
    }

    public Folder getFolder() {
        return folder;
    }
    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public EmailAccount getEmailAccount(){
        return emailAccount;
    }
}
