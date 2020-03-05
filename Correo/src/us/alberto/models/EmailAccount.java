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

    public Store getStore() {
        return store;
    }

    public Session getSession() {
        return session;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
