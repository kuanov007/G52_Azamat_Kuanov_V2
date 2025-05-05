package uz.azamat.util;

import lombok.Getter;
import lombok.Setter;
import uz.azamat.entity.Homework;
import uz.azamat.entity.PaginationHistory;
import uz.azamat.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GlobalStorage {
    @Getter
    private static final List<User> users = new ArrayList<>();

    @Getter
    public static final List<Homework> homeworks = new ArrayList<>();

    @Getter
    @Setter
    private static List<PaginationHistory> paginationHistories = new ArrayList<>();

    public static Optional<User> getUserByChatId(Long chatId) {
        return Optional.of(
                users.stream().filter(user ->
                                user.getChatId().equals(chatId)
                        ).findFirst()
                        .get());
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static boolean isUserAlreadyRegistered(Long chatId) {
        return users.stream().anyMatch(user -> user.getChatId().equals(chatId));
    }

    public static void addPaginationHistory(PaginationHistory paginationHistory) {
        paginationHistories.add(paginationHistory);
    }


    public static PaginationHistory getPaginationHistory(Long userChatId, Integer messageId) {
        return paginationHistories.stream().filter(paginationHistory -> {
            return paginationHistory.getChatId().equals(userChatId) && paginationHistory.getMessageId().equals(messageId);
        }).findFirst().get();
    }

    public static Homework getHomeWorkById(UUID uuid) {
        return homeworks.stream().filter(homework -> {
            return homework.getUuid().equals(uuid);
        }).findFirst().get();
    }

    public static void addHomeWork(Homework homework) {
        homeworks.add(homework);
    }

    public static int getSizeOfHomeworks() {
        return homeworks.size();
    }
}
