package db;

import java.io.Serializable;

public class User implements Serializable {
    private final String userName;
    private final String password;
    private boolean isOldUser;
    private boolean isAuthorized = false;

    public User(String userName, String password, boolean isOldUser) {
        this.userName = userName;
        this.password = password;
        this.isOldUser = isOldUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isOldUser() {
        return isOldUser;
    }

    public void setOldUser(boolean oldUser) {
        isOldUser = oldUser;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }
}
