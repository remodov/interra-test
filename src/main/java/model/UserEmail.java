package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserEmail {

    private final String user;
    private final List<String> email;

    public UserEmail(String user, List<String> email) {
        if (email == null || user == null){
            throw new IllegalArgumentException("user and email can not be null");
        }
        this.user = user;
        this.email = email;
    }

    public UserEmail(String user) {
        if (user == null){
            throw new IllegalArgumentException("user and email can not be null");
        }
        this.user = user;
        this.email = new ArrayList<>();
    }

    public String getUser() {
        return user;
    }

    public List<String> getEmail() {
        return Collections.unmodifiableList(email);
    }

    @Override
    public String toString() {
        return "UserEmail{" +
                "user='" + user + '\'' +
                ", email=" + email +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmail userEmail = (UserEmail) o;
        return Objects.equals(user, userEmail.user) &&
                Objects.equals(email, userEmail.email);
    }

    public void addEmail(String email){
        this.getEmail().add(email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, email);
    }
}
