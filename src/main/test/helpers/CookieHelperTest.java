package helpers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dao.DBException;
import dao.SessionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CookieHelperTest {
    private CookieHelper cookieHelper;

    @BeforeEach
    void prepareObjects() {
        SessionDAO sessionDAO = new SessionDAO();
        cookieHelper = new CookieHelper(sessionDAO);
    }
    @Test
    void returnStringWithNoQuotation(){
        String expected = "sessionId";
        String cookieString = "cookie=\"mySessionId\"";
        String actual = cookieHelper.removeQuotationFromSessionId(cookieString);
        assertEquals(expected, actual);
    }

    @Test
    void nullArgumentReaction(){
        String expected = "";
        String cookieString = null;
        String actual = cookieHelper.removeQuotationFromSessionId(cookieString);
        assertEquals(expected, actual);
    }

    @Test
    void emptyStringReaction(){
        String expected = "";
        String cookieString = "";
        String actual = cookieHelper.removeQuotationFromSessionId(cookieString);
        assertEquals(expected, actual);
    }

    @Test
    void blankSpaceStringReaction(){
        String expected = "";
        String cookieString = " ";
        String actual = cookieHelper.removeQuotationFromSessionId(cookieString);
        assertEquals(expected, actual);
    }

    @Test
    void return4WhenSessionIdIsInCookie() {
        // arrange
        int expected = 4;
        SessionDAO sessionDAOMock = mock(SessionDAO.class);
        try {
            when(sessionDAOMock.getUserIdBySession("mySessionId")).thenReturn(4);
        } catch (DBException e) {
            e.printStackTrace();
        }
        CookieHelper cookieHelper = new CookieHelper(sessionDAOMock);
        Headers headers = new Headers();
        headers.add("Cookie", "sessionId=mySessionId");
        HttpExchange httpExchangeMock = mock(HttpExchange.class);
        when(httpExchangeMock.getRequestHeaders()).thenReturn(headers);

        // act
        int actual = cookieHelper.getUserIdBySessionID(httpExchangeMock);

        // assert
        assertEquals(expected, actual);
    }
}