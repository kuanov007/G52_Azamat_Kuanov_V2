package uz.azamat.ui;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.Function;

public interface UiService {
    void runOnHasMessage(Message message, Function<Object, Integer> executeObject);

    void runOnHasCallbackQuery(CallbackQuery callbackQuery, Function<Object, Integer> executeObject);
}
