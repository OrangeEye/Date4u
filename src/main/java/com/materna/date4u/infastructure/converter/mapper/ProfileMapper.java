package com.materna.date4u.infastructure.converter.mapper;

import com.materna.date4u.core.entities.Profile;
import com.materna.date4u.infastructure.converter.dto.PhotoDto;
import com.materna.date4u.infastructure.converter.dto.ProfileDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileMapper {

    public ProfileDto convert(Profile profile) {
        List<PhotoDto> photos = profile.getPhotos().stream().map(photo ->
                new PhotoDto(photo.getId(), profile.getId(), photo.getName(), photo.isProfilePhoto())
        ).toList();

        return new ProfileDto(
                profile.getId(), profile.getNickname(), profile.getBirthdate(), profile.getHornlength(),
                profile.getGender(), profile.getAttractedToGender(), profile.getDescription(),
                profile.getLastseen(), photos
        );
    }

    public Profile convertFromDto(ProfileDto p) {
        return new Profile(
                p.nickname(), p.birthdate(), p.hornlength(),
                p.gender(), p.attractedToGender(), p.description(),
                p.lastseen()
        );
    }
}
