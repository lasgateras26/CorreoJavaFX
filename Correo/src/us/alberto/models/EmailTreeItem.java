package us.alberto.models;

import javafx.scene.control.TreeItem;

import javax.mail.Folder;

public class EmailTreeItem extends TreeItem<String> {

    private String name;
    private Folder folder;
    private EmailAccount emailAccount;

    public EmailTreeItem(String name, Folder folder, EmailAccount emailAccount) {
        super(name);
        this.folder = folder;
        this.emailAccount = emailAccount;
    }
}
