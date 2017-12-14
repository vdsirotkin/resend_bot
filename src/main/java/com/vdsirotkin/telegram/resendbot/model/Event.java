package com.vdsirotkin.telegram.resendbot.model;

import org.telegram.telegrambots.api.objects.Message;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by vitaly on 25.10.16.
 */
public class Event {

    private Integer gameId;
    private Integer messageId;
    private String eventText;
    private User parent;
    private Set<User> participants;
    private LocalDateTime creationDate = LocalDateTime.now();

    public Integer getGameId() {
        return gameId;
    }

    public Event setGameId(Integer gameId) {
        this.gameId = gameId;
        return this;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public Event setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public User getParent() {
        return parent;
    }

    public Event setParent(User parent) {
        this.parent = parent;
        return this;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Set<User> getParticipants() {
        if (participants == null) {
            participants = new TreeSet<>();
        }
        return participants;
    }

    public String getEventText() {
        return eventText;
    }

    public Event setEventText(String eventText) {
        this.eventText = eventText;
        return this;
    }

    @Override
    public String toString() {
        String basicText = eventText + "\n\n"
                + "Отправлено: " + parent.toString() + "\n";
        if (participants != null && !participants.isEmpty()) {
            basicText += "\nПрисоединились:\n";
            for (User participant : participants) {
                basicText += participant.toString() + "\n";
            }
        }
        return basicText;
    }
}
