package us.alberto.models;

import javax.mail.Session;
import javax.mail.Store;

public class EmailAccount {

    private String email;
    private String password;
    private Store store;
    private Session session;

    public EmailAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
