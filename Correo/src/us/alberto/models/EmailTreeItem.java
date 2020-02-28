package us.alberto.models;

import javafx.scene.control.TreeItem;

import javax.mail.Folder;
import javax.mail.Store;

public class EmailTreeItem extends TreeItem<String> {

    private String name;
    private Folder folder;
    private EmailAccount emailAccount;
    private Store store;

    public EmailTreeItem(String name, EmailAccount emailAccount) {
        this.name = name;
        this.emailAccount = emailAccount;
    }

    public EmailTreeItem(String name,  EmailAccount emailAccount, Folder folder) {
        this.name = name;
        this.folder = folder;
        this.emailAccount = emailAccount;
    }

    public EmailTreeItem(String name, EmailAccount emailAccount, Folder folder, Store store) {
        super(name);
        this.name = name;
        this.emailAccount = emailAccount;
        this.folder = folder;
        this.store = store;
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
