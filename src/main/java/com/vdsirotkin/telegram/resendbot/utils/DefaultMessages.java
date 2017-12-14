package com.vdsirotkin.telegram.resendbot.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.Map;

/**
 * Created by vitalijsirotkin on 22.10.16.
 */
@Component
public class DefaultMessages {
    public SendMessage getCantFindCommand(String s) {
        return new SendMessage().setChatId(s).setText("Неизвестная команда, выберите нужную из списка команд.");
    }

    public SendMessage getCancelCommand(String s) {
        return new SendMessage().setText("Команда отменена.").setChatId(s);
    }

    public SendMessage getFirstStep(String chatId) {
        return new SendMessage().setChatId(chatId).setText("Отправьте мне сообщение, которое вы хотите видеть в LFG канале.");
    }

    public SendMessage getStartMessage(String chatId) {
        return new SendMessage().setChatId(chatId).setText("Привет! Отправь мне в ответ свой ник в PSN, чтобы я его запомнил и его не нужно было указывать каждый раз. А если он совпадает с твоим ником в телеграме - нажми /nick");
    }

    public SendMessage getSecondStep(String chatId) {
        return new SendMessage().setChatId(chatId).setText("Сообщение отправлено! Теперь ты и другие гардианы можете полюбоваться на него в канале: https://telegram.me/hsw_lfg_channel").disableWebPagePreview();
    }

    public SendMessage getThanksMessage(String chatId, String nickname) {
        return new SendMessage().setChatId(chatId).setText(String.format("Спасибо! Твой PSN ник - %s. Теперь можешь отправить мне /event чтобы начать работу. А если ты вдруг ошибся и написал что-то не то - нажми еще раз /start", nickname));
    }

    public SendMessage getAlreadyJoined(String chatId) {
        return new SendMessage().setChatId(chatId).setText("Вы уже присоединились к данному событию");
    }

    public SendMessage getSuccessJoin(String chatId) {
        return new SendMessage().setText("Вы успешно присоединилсь к событию").setChatId(chatId);
    }

    public SendMessage getMissedEvent(String chatId) {
        return new SendMessage().setText("Это событие уже закончилось").setChatId(chatId);
    }

    public SendMessage getJoinedYourEvent(String parentChatId, String fullNick) {
        return new SendMessage().setChatId(parentChatId).setText("К вашему событию присоединился " + fullNick);
    }

    public SendMessage getLeavedYourEvent(String parentChatId, String fullNick) {
        return new SendMessage().setChatId(parentChatId).setText("От вашего события отсоединился " + fullNick);
    }

    public SendMessage getWrongNickname(String chatId) {
        return new SendMessage(chatId, "К сожалению, у тебя нет ника в телеграме. Придется написать свой PSN вручную :(");
    }

    public SendMessage cannotFindNickname(String chatId) {
        return new SendMessage(chatId, "Не могу найти твой PSN ник в Destiny. Попробуй ввести его сейчас:");
    }

    public SendMessage timePlayed(String chatId, Integer hours, Integer minutes, Map<String, Integer> charactersLight) {
        String s = String.format("Время, потраченное на дестини: %d часов %d минут\n\n", hours, minutes);
        s += "Лайт твоих персонажей:\n";
        for (Map.Entry<String, Integer> entry : charactersLight.entrySet()) {
            s += String.format("*%s*: %d\n", entry.getKey(), entry.getValue());
        }
        return new SendMessage(chatId, s).setParseMode(ParseMode.MARKDOWN);
    }

    public SendMessage unknownError(String chatId) {
        return new SendMessage(chatId, "Сорри, произошла непредвиденная ошибка =( Обратись к @vdsirotkin");
    }

    public SendMessage serviceMessage(String text) {
        return new SendMessage(82224688L, text);
    }
}
