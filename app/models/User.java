package models;

import java.util.Date;

public class User {

    private String name;
    private Date joined = new Date();

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getJoined() {
        return joined.toString();
    }
}