package uz.azamat;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import uz.azamat.util.LocalStorage;

import java.util.function.Function;

public class HomeworkCheckerBot extends TelegramLongPollingBot {
    private final String username;

    public HomeworkCheckerBot(DefaultBotOptions options, String botToken, String username) {
        super(options, botToken);
        this.username = username;
    }

    public HomeworkCheckerBot(String botToken, String username) {
        super(botToken);
        this.username = username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();

            if (LocalStorage.getAdminChatId().equals(String.valueOf(chatId))) {
                LocalStorage.getAdminUiInstance().runOnHasMessage(update.getMessage(), executeObject);
                return;
            }

            LocalStorage.getUserUiInstance().runOnHasMessage(update.getMessage(), executeObject);
        } else if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getFrom().getId();

            if (LocalStorage.getAdminChatId().equals(String.valueOf(chatId))) {
                LocalStorage.getAdminUiInstance().runOnHasCallbackQuery(update.getCallbackQuery(), executeObject);
                return;
            }

            LocalStorage.getUserUiInstance().runOnHasCallbackQuery(update.getCallbackQuery(), executeObject);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    private final Function<Object, Integer> executeObject = (object -> {
        try {
            if (object instanceof SendMessage sendMessage) {
                return execute(sendMessage).getMessageId();
            } else if (object instanceof SendPhoto sendPhoto) {
                return execute(sendPhoto).getMessageId();
            } else if (object instanceof SendDocument sendDocument) {
                return execute(sendDocument).getMessageId();
            } else if (object instanceof SendAudio sendAudio) {
                return execute(sendAudio).getMessageId();
            } else if (object instanceof SendVideo sendVideo) {
                return execute(sendVideo).getMessageId();
            } else if (object instanceof SendVoice sendVoice) {
                return execute(sendVoice).getMessageId();
            } else if (object instanceof SendContact sendContact) {
                return execute(sendContact).getMessageId();
            } else if (object instanceof SendAnimation sendAnimation) {
                return execute(sendAnimation).getMessageId();
            } else if (object instanceof EditMessageText editMessageText) {
                execute(editMessageText);
            } else if (object instanceof DeleteMessage deleteMessage) {
                execute(deleteMessage);
            }
            return null;
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    });
}
