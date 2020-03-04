package us.alberto.models;

import javafx.scene.control.TreeItem;
import javax.mail.Folder;

public class EmailTreeItem extends TreeItem<String> {

    private String nombre;
    private EmailAccount emailAccount;
    private Folder folder;

    public EmailTreeItem(String name, EmailAccount mailAccount, Folder folder){
        super(name);
        nombre = name;
        this.emailAccount = mailAccount;
        this.folder = folder;
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
