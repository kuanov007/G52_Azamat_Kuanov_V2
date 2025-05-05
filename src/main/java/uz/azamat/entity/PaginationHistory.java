package uz.azamat.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data


public class PaginationHistory {
    private Long chatId;
    private Integer messageId;
    private int page;
}
