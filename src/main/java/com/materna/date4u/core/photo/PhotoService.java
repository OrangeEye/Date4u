package com.materna.date4u.core.photo;

import com.materna.date4u.core.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Service
public class PhotoService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final FileSystem fs;
    private final Thumbnail thumbnail;


    public PhotoService(ObjectProvider<FileSystem> fs, @ThumbnailRendering(ThumbnailRendering.RenderingQuality.FAST) Thumbnail thumbnail) {
        this.fs = fs.getIfUnique();
        this.thumbnail = thumbnail;
    }

    public Optional<byte[]> download(String name) {
        try {
            return Optional.of(fs.load(name + ".jpg"));
        } catch (UncheckedIOException e) {
            return Optional.empty();
        }
    }

//    public Optional<byte[]> download( Photo photo ) {
//        return download( photo.getName() );
//    }

    public String upload(byte[] imageBytes) {
        Future<byte[]> thumbnailBytes = thumbnail.thumbnail(imageBytes);

        String imageName = UUID.randomUUID().toString();

        //    NewPhotoEvent newPhotoEvent = new NewPhotoEvent( imageName, OffsetDateTime.now() );
        //    publisher.publishEvent( newPhotoEvent );

        // First: store original image
        fs.store(imageName + ".jpg", imageBytes);

        // Second: store thumbnail
        try {
            log.info("upload");
            fs.store(imageName + "-thumb.jpg", thumbnailBytes.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
        }

        return imageName;
    }
}
