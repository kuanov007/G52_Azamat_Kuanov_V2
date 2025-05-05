package uz.azamat;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.azamat.util.LocalStorage;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi bot = new TelegramBotsApi(DefaultBotSession.class);
            bot.registerBot(new HomeworkCheckerBot(LocalStorage.getBotToken(), LocalStorage.getBotUsername()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}