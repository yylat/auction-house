package com.epam.auction.validator;

import java.util.HashSet;
import java.util.Set;

public class PhotoValidator extends Validator {

    private static final Set<String> ACCEPTABLE_PHOTO_TYPES = new HashSet<>();

    static {
        ACCEPTABLE_PHOTO_TYPES.add("jpeg");
        ACCEPTABLE_PHOTO_TYPES.add("jpg");
        ACCEPTABLE_PHOTO_TYPES.add("png");
        ACCEPTABLE_PHOTO_TYPES.add("gif");
    }

    public boolean validatePhotoExtension(String photoName) {
        String extension = photoName.substring(photoName.lastIndexOf(".") + 1).toLowerCase();
        if (ACCEPTABLE_PHOTO_TYPES.contains(extension)) {
            return true;
        } else {
            setValidationMessage("Unacceptable file format: [" + extension + "].");
            return false;
        }
    }

}