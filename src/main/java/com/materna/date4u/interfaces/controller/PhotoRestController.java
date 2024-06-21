package com.materna.date4u.interfaces.controller;

import com.materna.date4u.core.entities.Photo;
import com.materna.date4u.core.photo.PhotoService;
import com.materna.date4u.infastructure.repositories.PhotoRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PhotoRestController {

    private final PhotoService photos;
    private final PhotoRepository photoRepository;

    public PhotoRestController(PhotoService photos, PhotoRepository photoRepository) {
        this.photos = photos;
        this.photoRepository = photoRepository;
    }

    @GetMapping(path = "/api/photos/{imageName}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> photo(@PathVariable String imageName) {
/*        Optional<byte[]> download = photos.download(imageName);
        if (download.isPresent()) {
            return ResponseEntity.ok().body(download.get());
        } else {
            return ResponseEntity.notFound().build();
        }*/
        return ResponseEntity.of(photos.download(imageName));
    }

    @GetMapping(path = "/api/profile/{profileId}/photos")
    public ResponseEntity<List<Photo>> getPhotosForProfileId(@PathVariable Long profileId) {
        System.out.println("ID = " + profileId);
        System.out.println(photoRepository.findPhotosByProfileId(profileId));
        return ResponseEntity.of(Optional.empty());
    }

}
