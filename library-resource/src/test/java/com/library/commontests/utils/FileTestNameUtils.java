package com.library.commontests.utils;

import org.junit.Ignore;

/**
 * Created by ozgur on 22.05.2016.
 */
@Ignore
public class FileTestNameUtils {
    private static final String PATH_REQUEST = "/request/";
    private static final String PATH_RESPONSE = "/response/";

    public FileTestNameUtils() {
    }

    public static String getPathFileRequest(final String mainFolder, final String fileName) {
        return mainFolder + PATH_REQUEST + fileName;
    }

    public static String getPathFileResponse(final String mainFolder, final String fileName) {
        return mainFolder + PATH_RESPONSE + fileName;
    }

}
