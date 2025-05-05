package uz.azamat.ui;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.Function;

public class UserUi implements UiService {
    public void runOnHasMessage(Message message, Function<Object, Integer> executeObject) {
        Long chatId = message.getChatId();
        String text = message.getText();


    }

    public void runOnHasCallbackQuery(CallbackQuery callbackQuery, Function<Object, Integer> executeObject) {
        Long chatId = callbackQuery.getFrom().getId();
        String callbackQueryData = callbackQuery.getData();
        Integer messageId = callbackQuery.getMessage().getMessageId();

    }
}
