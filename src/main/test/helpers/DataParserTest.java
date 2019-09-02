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

}