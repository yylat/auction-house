package com.epam.auction.validator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class PhotoValidatorTest {

    private static PhotoValidator photoValidator;

    @BeforeClass
    public static void init() {
        photoValidator = new PhotoValidator();
    }

    private String fileName;
    private boolean validity;

    public PhotoValidatorTest(String fileName, boolean validity) {
        this.fileName = fileName;
        this.validity = validity;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"image.jpg", true},
                {"image.jpeg", true},
                {"photo.png", true},
                {"photo.img.gif", true},
                {"file.txt", false},
                {"file.docx", false},
                {"index.html", false},
                {"script.controller.js", false},
                {"song.mp3", false},
                {"video.mp4", false}
        });
    }

    @Test
    public void validatePhotoExtensionTest() {
        assertThat(photoValidator.validatePhotoExtension(fileName), is(validity));
    }

}