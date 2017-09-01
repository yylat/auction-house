package com.epam.auction.util;

import com.epam.auction.exception.PhotoLoadingException;
import com.epam.auction.receiver.SiteManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        try {
            return Converter.inputStreamToString(new FileInputStream(UPLOAD_PATH + fileName));
        } catch (IOException e) {
            throw new PhotoLoadingException("Error trying to load photo.", e);
        }
    }

}