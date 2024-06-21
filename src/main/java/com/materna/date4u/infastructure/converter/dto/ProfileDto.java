package com.materna.date4u.infastructure.converter.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ProfileDto(
        Long id, String nickname, LocalDate birthdate, int hornlength, int gender,
        Integer attractedToGender, String description, LocalDateTime lastseen, List<PhotoDto> photos
) {
}