package uz.azamat.entity;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class User {
    @NonNull
    private Long chatId;
    private String firstName;
    private String lastName;
    private String username;
}
