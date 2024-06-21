package com.materna.date4u.interfaces.controller;

import com.materna.date4u.core.entities.Profile;
import com.materna.date4u.core.entities.Unicorn;
import com.materna.date4u.infastructure.converter.dto.ProfileDto;
import com.materna.date4u.infastructure.converter.mapper.ProfileMapper;
import com.materna.date4u.infastructure.repositories.ProfileRepository;
import com.materna.date4u.infastructure.repositories.UnicornRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/profiles")
public class ProfileRestController {

    private final ProfileRepository profiles;
    private final UnicornRepository unicornRepository;
    private final ProfileMapper profileMapper = new ProfileMapper();

    @Autowired
    public ProfileRestController(ProfileRepository profiles, UnicornRepository unicornRepository) {
        this.profiles = profiles;
        this.unicornRepository = unicornRepository;
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleException() {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public Stream<ProfileDto> profiles() {
        return profiles.findAll().stream().map(profileMapper::convert);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> profile(@PathVariable long id, Authentication authentication) {
        System.out.println(authentication.getName());
        return profiles
                .findById(id)
                .map(profile -> ResponseEntity.ok(profileMapper.convert(profile)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/self")
    public ResponseEntity<ProfileDto> profileSelf(Authentication authentication) {

        return unicornRepository
                .findByEmail(authentication.getName())
                .map(Unicorn::getProfile)
                .map(profile -> ResponseEntity.ok(profileMapper.convert(profile)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody ProfileDto profileDto) {
        if (profileDto.id() != null) {
            return ResponseEntity.badRequest().body("ID should be null for new profiles.");
        }

        Profile profile = profileMapper.convertFromDto(profileDto);
        profile = profiles.save(profile);

        return ResponseEntity.ok().body(profileMapper.convert(profile));
    }

}
