package com.vdsirotkin.telegram.resendbot.model;

import java.util.Date;

/**
 * Created by vitalijsirotkin on 25.03.17.
 */
public class User implements Comparable<User> {

    private String chatId;
    private String name;
    private String psnNick;
    private Date creationDate = new Date();

    public User() {
    }

    public User(String chatId, String name, String psnNick) {
        this.chatId = chatId;
        this.name = name;
        this.psnNick = psnNick;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPsnNick() {
        return psnNick;
    }

    public User setPsnNick(String psnNick) {
        this.psnNick = psnNick;
        return this;
    }

    public String getChatId() {
        return chatId;
    }

    public User setChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    @Override
    public String toString() {
        return this.name + " (PSN: " + this.psnNick + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (chatId != null ? !chatId.equals(user.chatId) : user.chatId != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return psnNick != null ? psnNick.equals(user.psnNick) : user.psnNick == null;

    }

    @Override
    public int hashCode() {
        int result = chatId != null ? chatId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (psnNick != null ? psnNick.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(User o) {
        return this.creationDate.compareTo(o.creationDate);
    }
}
