package uz.azamat.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.azamat.entity.Homework;
import uz.azamat.util.GlobalStorage;

import java.util.ArrayList;
import java.util.List;

public class PaginationService {
    public static SendMessage getHomeworksList(Long chatId, int page) {
        List<Homework> homeworksSublist = getHomeworksSublist(page);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (isSublistEmpty(page)) {
            sendMessage.setText("Hali homeworklar mavjud emas!");
            return sendMessage;
        }

        String stringOfAllHomeworksName = getStringOfAllHomeworksName(page);
        InlineKeyboardMarkup inlineButtons = getInlineButtons(page);

        sendMessage.setText(stringOfAllHomeworksName);
        sendMessage.setReplyMarkup(inlineButtons);
        return sendMessage;
    }

    private static InlineKeyboardMarkup getInlineButtons(int page) {
        List<Homework> homeworksSublist = getHomeworksSublist(page);
        if (homeworksSublist.isEmpty()) {
            return new InlineKeyboardMarkup();
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (int i = 0; i < homeworksSublist.size(); i++) {
            if (i % 5 == 0) {
                rows.add(row);
                row = new ArrayList<>();
            }
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(String.valueOf(i + 1));
            inlineKeyboardButton.setCallbackData(String.valueOf(i + 1));
            row.add(inlineKeyboardButton);
        }
        rows.add(row);

        row = new ArrayList<>();
        if (page > 1) {
            InlineKeyboardButton previousButton = new InlineKeyboardButton();
            previousButton.setText("⬅\uFE0F");
            previousButton.setCallbackData("previous");
            row.add(previousButton);
        }
        InlineKeyboardButton deleteButton = new InlineKeyboardButton();
        deleteButton.setText("❌");
        deleteButton.setCallbackData("delete");
        row.add(deleteButton);
        if (page * 10 < GlobalStorage.getSizeOfHomeworks()) {
            InlineKeyboardButton nextButton = new InlineKeyboardButton();
            nextButton.setText("➡\uFE0F");
            nextButton.setCallbackData("next");
            row.add(nextButton);
        }
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }


    private static String getStringOfAllHomeworksName(int page) {
        List<Homework> homeworksList = getHomeworksSublist(page);
        StringBuilder sb = new StringBuilder();
        if (homeworksList.isEmpty()) {
            return "Homeworklar yo'q";
        }

        sb.append("Homeworks (").append((page - 1) * 10).append(" - ").append(page * 10).append(")").append("  | ").append(GlobalStorage.getSizeOfHomeworks()).append("\n");

        for (int i = 0; i < homeworksList.size(); i++) {
            sb.append(i + 1).append(")").append(homeworksList.get(i).getThemeOrDescription()).append(";\n");
        }
        sb.append("\n").append("Eslatma: Bu joyda tekshirilgan va tekshilmagan homeworklar bor!");
        return sb.toString();
    }

    private static List<Homework> getHomeworksSublist(int page) {
        int start = (page - 1) * 10;
        int end = page * 10;

        if (start < GlobalStorage.getSizeOfHomeworks()) {
            return GlobalStorage.homeworks.subList(start, Math.min(end, GlobalStorage.getSizeOfHomeworks()));
        } else {
            return new ArrayList<>();
        }
    }

    public static boolean isSublistEmpty(int page) {
        return getHomeworksSublist(page).isEmpty();
    }
}
