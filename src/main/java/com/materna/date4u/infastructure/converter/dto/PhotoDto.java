package com.materna.date4u.infastructure.converter.dto;

public record PhotoDto(Long id,
                       Long profileId,
                       String name,
                       boolean isProfilePhoto) {
}
