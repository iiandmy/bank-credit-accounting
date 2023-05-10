package by.bsuir.bankcreditaccounting.dto.creditCreation;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCreationResponseDto {
    private Long creditId;
    private String message;
}
