package model;

public class PairUserMail {
    private String user;
    private String email;

    public PairUserMail(String user, String email) {
        this.user = user;
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
