package com.materna.date4u.interfaces;

import com.materna.date4u.core.photo.PhotoService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ShellComponent
public class PhotoCommands {

    private final PhotoService photoService;

    public PhotoCommands(PhotoService photoService) {
        this.photoService = photoService;
    }

    @ShellMethod("Gibt die Dimensionen des Bildes (zB unicorn001) aus")
    String photoDim(String name) {// show-photo
        return photoService.download(name).map(bytes -> {
            try {
                var image = ImageIO.read(new ByteArrayInputStream(bytes));
                return "Width: " + image.getWidth()
                        + ", Height: " + image.getHeight();
            } catch (IOException e) {
                return "Unable to read image dimensions";
            }
        }).orElse("Image not found");
    }

    @ShellMethod("Lade das Photo hoch, welches unter dem Pfad zu finden ist")
    String uploadPhoto(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Path.of(filePath));
        String fileName = photoService.upload(bytes);
        return "Uploaded: " + fileName;
    }
}
