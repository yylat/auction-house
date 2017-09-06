package com.epam.auction.util;

import com.epam.auction.exception.PhotoLoadingException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

/**
 *
 */
public class PhotoLoader {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String UPLOAD_PATH = SiteManager.getInstance().getUploadPath();

    private String currentDate;

    public PhotoLoader() {
        currentDate = String.valueOf(new Date().getTime());
    }

    public static void createUploadFolder() {
        File uploadDirectory = new File(UPLOAD_PATH);
        if (!uploadDirectory.exists()) {
            if (!uploadDirectory.mkdirs()) {
                LOGGER.log(Level.ERROR, "Can't create directory to store photos.");
                throw new RuntimeException();
            }
        }
    }

    public long savePhotoToServer(InputStream photoInputStream, int index) throws PhotoLoadingException {
        String fileName = currentDate + index;
        try {
            Files.copy(photoInputStream, Paths.get(UPLOAD_PATH + fileName));
        } catch (IOException e) {
            throw new PhotoLoadingException("Error trying to save photo.", e);
        }
        return Long.valueOf(fileName);
    }

    public void deletePhoto(String photoName) throws PhotoLoadingException {
        String photoPath = UPLOAD_PATH + photoName;

        File photo = new File(photoPath);

        if (!photo.exists())
            throw new PhotoLoadingException("Delete error: no such file or directory.");

        if (!photo.canWrite())
            throw new PhotoLoadingException("Delete error: write protected path.");

        boolean result = photo.delete();

        if (!result)
            throw new PhotoLoadingException("Deletion failed.");
    }

    public String loadPhotoAsString(long photoId) throws PhotoLoadingException {
        String result;

        try (InputStream inputStream = new FileInputStream(UPLOAD_PATH + photoId)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            result = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            throw new PhotoLoadingException("Error trying to load photo.", e);
        }

        return result;
    }

}