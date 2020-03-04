package us.alberto.models;

import javafx.scene.control.TreeItem;
import javax.mail.Folder;

public class EmailTreeItem extends TreeItem<String> {

    private String name;
    private EmailAccount emailAccount;
    private Folder folder;

    public EmailTreeItem(String name, EmailAccount emailAccount, Folder folder) {
        this.name = name;
        this.emailAccount = emailAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public EmailAccount getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(EmailAccount emailAccount) {
        this.emailAccount = emailAccount;
    }
}
