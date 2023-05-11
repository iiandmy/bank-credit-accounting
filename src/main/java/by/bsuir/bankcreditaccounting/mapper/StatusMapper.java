package by.bsuir.bankcreditaccounting.mapper;

import by.bsuir.bankcreditaccounting.domain.Status;
import by.bsuir.bankcreditaccounting.dto.StatusResponseDto;

public class StatusMapper {
    public static StatusResponseDto toDto(Status status) {
        return StatusResponseDto.builder()
                .id(status.getId())
                .name(status.getName())
                .build();
    }
}
