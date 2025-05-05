package uz.azamat.ui;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.azamat.entity.Homework;
import uz.azamat.entity.PaginationHistory;
import uz.azamat.service.ButtonService;
import uz.azamat.service.PaginationService;
import uz.azamat.util.AdminState;
import uz.azamat.util.ButtonNamesForAdmin;
import uz.azamat.util.GlobalStorage;
import uz.azamat.util.LocalStorage;

import java.util.Arrays;
import java.util.function.Function;

public class AdminUi implements UiService {
    public void runOnHasMessage(Message message, Function<Object, Integer> executeObject) {
        String text = message.getText();

        if (text.equals("/start")) {
            executeObject.apply(ButtonService.getMainMenuButtonsForAdmin());
        } else {
            if (isAnyButtonClicked(text)) {
                if (LocalStorage.getAdminState() == AdminState.MAIN) {
                    if (text.equals(ButtonNamesForAdmin.CHECK_HOMEWORK.getString())) {
                        LocalStorage.setAdminState(AdminState.SHOW_HOMEWORK_LIST_IN_CHECKING);
                    } else if (text.equals(ButtonNamesForAdmin.SHOW_OLD_HOMEWORKS.getString())) {
                        LocalStorage.setAdminState(AdminState.SHOW_HOMEWORK_LIST_IN_OLD);
                    }
                }
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(LocalStorage.getAdminChatId());
                sendMessage.setText("Iltimos pastdagi tugmalardan foydalaning!");
                executeObject.apply(sendMessage);
                return;
            }
            switch (LocalStorage.getAdminState()) {
                case SHOW_HOMEWORK_LIST_IN_OLD, SHOW_HOMEWORK_LIST_IN_CHECKING -> {
                    SendMessage sendMessageWithHomeworkList = PaginationService.getHomeworksList(Long.parseLong(LocalStorage.getAdminChatId()), 1);
                    Integer messageId = executeObject.apply(sendMessageWithHomeworkList);

                    if (!PaginationService.isSublistEmpty(1)) {
                        PaginationHistory paginationHistory = new PaginationHistory();
                        paginationHistory.setChatId(Long.parseLong(LocalStorage.getAdminChatId()));
                        paginationHistory.setMessageId(messageId);
                        paginationHistory.setPage(1);
                        GlobalStorage.addPaginationHistory(paginationHistory);
                    }
                }
                case SET_A_MARK -> {
                    executeObject.apply(new SendMessage(LocalStorage.getAdminChatId(), "Baho qo'yish uchun pastdagi tugmalardan foydalanishingizni mumkin!"));
                }
                case ENTER_FEEDBACK -> {
                    //TODO
                }
            }
        }
    }

    public void runOnHasCallbackQuery(CallbackQuery callbackQuery, Function<Object, Integer> executeObject) {
        String callbackQueryData = callbackQuery.getData();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        if (isAnyIndexOfHomework(callbackQueryData)) {
            PaginationHistory paginationHistory = GlobalStorage.getPaginationHistory(Long.parseLong(LocalStorage.getAdminChatId()), messageId);

            Homework homework = GlobalStorage.getHomeworks().get((paginationHistory.getPage() - 1) * 10 + Integer.parseInt(callbackQueryData) - 1);
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(LocalStorage.getAdminChatId());
            sendDocument.setDocument(new InputFile(homework.getZipFileId()));

            StringBuilder stringBuilder = new StringBuilder();
            switch (LocalStorage.getAdminState()) {
                case SHOW_HOMEWORK_LIST_IN_OLD -> {
                    stringBuilder.append("Homework: ").append(homework.getThemeOrDescription()).append("\n")
                            .append("Student: ").append(GlobalStorage.getUserByChatId(homework.getUserChatId()).get().getUsername()).append("\n")
                            .append("Sent at: ").append(homework.getSendTimeByUser()).append("\n")
                            .append("----------------------\n");
                    sendDocument.setCaption(stringBuilder.toString());
                    executeObject.apply(sendDocument);
                    LocalStorage.setAdminState(AdminState.MAIN);
                }
                case SHOW_HOMEWORK_LIST_IN_CHECKING -> {
                    stringBuilder.append("Homework: ").append(homework.getThemeOrDescription()).append("\n")
                            .append("Student: ").append(GlobalStorage.getUserByChatId(homework.getUserChatId()).get().getUsername()).append("\n")
                            .append("Sent at: ").append(homework.getSendTimeByUser()).append("\n")
                            .append("----------------------\n")
                            .append("Open .zip file to check homework")
                            .append("Give ball(1,2,3,4,5)");
                    sendDocument.setCaption(stringBuilder.toString());
                    executeObject.apply(sendDocument);
                    LocalStorage.setAdminState(AdminState.ENTER_FEEDBACK);
                }
            }
        } else {

        }
    }

    private boolean isAnyIndexOfHomework(String callbackQueryData) {
        try {
            Integer.parseInt(callbackQueryData);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isAnyButtonClicked(String text) {
        ButtonNamesForAdmin[] values = ButtonNamesForAdmin.values();
        return Arrays.stream(values).anyMatch(value -> value.getString().equals(text));
    }
}
