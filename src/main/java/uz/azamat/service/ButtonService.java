package uz.azamat.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.azamat.util.ButtonNamesForAdmin;
import uz.azamat.util.LocalStorage;

import java.util.ArrayList;
import java.util.List;

public class ButtonService {
    public static SendMessage getMainMenuButtonsForAdmin() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();

        firstRow.add(ButtonNamesForAdmin.CHECK_HOMEWORK.getString());
        firstRow.add(ButtonNamesForAdmin.SHOW_OLD_HOMEWORKS.getString());

        rows.add(firstRow);
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(LocalStorage.getAdminChatId());
        sendMessage.setText("Kerakli bo'limni tanlang: ");
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }
}
