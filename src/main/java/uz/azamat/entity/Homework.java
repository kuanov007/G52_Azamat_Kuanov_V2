package uz.azamat.entity;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data

public class Homework {
    @NonNull
    private UUID uuid;
    @NonNull
    private Long userChatId;
    private String themeOrDescription;
    private String zipFileId;
    private byte mark;
    private String feedbackByTeacher;
    private String sendTimeByUser;
    private String checkTimeByTeacher;

}
