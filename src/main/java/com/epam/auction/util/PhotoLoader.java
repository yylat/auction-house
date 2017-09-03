package com.epam.auction.util;

import com.epam.auction.exception.PhotoLoadingException;
import com.epam.auction.receiver.SiteManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;

public class PhotoLoader {

    private static final String UPLOAD_PATH = SiteManager.getInstance().getUploadPath();

    private String currentDate;

    public PhotoLoader() {
        currentDate = String.valueOf(new Date().getTime());
    }

    public static void createUploadFolder() {
        File uploadDirectory = new File(UPLOAD_PATH);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
    }

    public String savePhotoToServer(InputStream photoInputStream, int index) throws PhotoLoadingException {
        String fileName = currentDate + index;
        try {
            Files.copy(photoInputStream, Paths.get(UPLOAD_PATH + fileName));
        } catch (IOException e) {
            throw new PhotoLoadingException("Error trying to save photo.", e);
        }
        return fileName;
    }

    public String loadPhotoAsString(String fileName) throws PhotoLoadingException {
        String result;

        try (InputStream inputStream = new FileInputStream(UPLOAD_PATH + fileName)) {
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