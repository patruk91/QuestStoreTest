package helpers;

import dao.DBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataParserTest {


    @Test
    void checkIfParsedDataIsNotNull() {
        String strToParse = "user1=userowski1&user2=userowski2";
        try {
            assertNotNull(DataParser.parseFormData(strToParse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void throwIllegalArgumentExceptionWhenStringToParseIsEmpty() {
        String strToParse = "";
        assertThrows(IllegalArgumentException.class, () -> DataParser.parseFormData(strToParse));
    }

    @Test
    void throwNullPointerExceptionWhenStringToParseIsNull() {
        String strToParse = null;
        assertThrows(NullPointerException.class, () -> DataParser.parseFormData(strToParse));
    }

    @Test
    void checkIfStringToParseIsWithoutEqualSign() {
        String strToParse = "user1";
        assertThrows(IllegalArgumentException.class, () -> DataParser.parseFormData(strToParse));
    }

    @Test
    void checkIfStringToParseHaveOneRecord() {
        String strToParse = "user1=userowski1";
        Map<String, String> expected = new HashMap<>();
        expected.put("user1", "userowski1");
        Map<String, String> actual = null;
        try {
            actual = DataParser.parseFormData(strToParse);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       assertEquals(expected.get("user1"), actual.get("user1"));
    }

    @Test
    void checkIfStringToParseMultipleRecords() {
        String strToParse = "user1=userowski1&user2=userowski2";
        Map<String, String> expected = new HashMap<>();
        expected.put("user1", "userowski1");
        expected.put("user2", "userowski2");

        Map<String, String> actual = null;
        try {
            actual = DataParser.parseFormData(strToParse);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> finalActual = actual;

        assertAll(
                () -> assertEquals(expected.get("user1"), finalActual.get("user1")),
                () -> assertEquals(expected.get("user2"), finalActual.get("user2"))
        );
    }

}