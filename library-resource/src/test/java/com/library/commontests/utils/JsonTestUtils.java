package com.library.commontests.utils;

import org.json.JSONException;
import org.junit.Ignore;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by ozgur on 22.05.2016.
 */
@Ignore
public class JsonTestUtils {

    public static final String BASE_JSON_DIR = "json/";

    private JsonTestUtils() {//no one can instantiate it
    }

    public static String readJsonFile(final String relativePath) {
        InputStream is = JsonTestUtils.class.getClassLoader().getResourceAsStream(BASE_JSON_DIR + relativePath);
        try (Scanner s = new Scanner(is)) {
            return s.useDelimiter("\\A").hasNext() ? s.next() : "";
        }
    }

    public static void assertJsonMatchesFileContent(String actualJson,String fileNameWithExpectedJson){
        assertJsonMatchesExpectedJson(actualJson,readJsonFile(fileNameWithExpectedJson));
    }

    public static void assertJsonMatchesExpectedJson(final String actualJson, final String exptectedJson) {
        try {
            JSONAssert.assertEquals(exptectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
        } catch (final JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
