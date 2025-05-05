package uz.azamat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data


public class PaginationHistory {
    private Long chatId;
    private Integer messageId;
    private int page;
}
