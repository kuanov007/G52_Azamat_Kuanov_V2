package uz.azamat.util;

import lombok.Getter;
import lombok.Setter;
import uz.azamat.entity.Homework;
import uz.azamat.ui.AdminUi;
import uz.azamat.ui.UserUi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LocalStorage {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("settings");
    private static final AdminUi adminUi = new AdminUi();
    private static final UserUi userUi = new UserUi();
    @Getter
    private static AdminState adminState = AdminState.MAIN;

    @Getter
    @Setter
    private static Homework handlingHomeworkForChecking = null;

    public static void setAdminState(AdminState adminState) {
        LocalStorage.adminState = adminState;
    }

    public static AdminUi getAdminUiInstance() {
        return adminUi;
    }

    public static UserUi getUserUiInstance() {
        return userUi;
    }

    public static String getBotToken() {
        return bundle.getString("bot.token");
    }

    public static String getBotUsername() {
        return bundle.getString("bot.username");
    }

    public static String getAdminChatId() {
        return bundle.getString("bot.adminId");
    }

    public static String getTimeNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String formattingDateTime = formatter.format(LocalDateTime.now());
        String date = formattingDateTime.substring(0, 10);
        String time = formattingDateTime.substring(11, 19);
        return time + " " + date;
    }

    public static String getHomeworkCaptionOnChecking(Homework homework) {
        return "Homework: " + homework.getThemeOrDescription() + "\n" +
               "Student: " + GlobalStorage.getUserByChatId(homework.getUserChatId()).get().getUsername() + "\n" +
               "Sent at: " + homework.getSendTimeByUser() + "\n" +
               "----------------------\n" +
               "Open .zip file to check homework" +
               "Give ball(1,2,3,4,5)";
    }
}
