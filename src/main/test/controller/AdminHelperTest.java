package controller;

import com.sun.net.httpserver.HttpExchange;
import dao.*;
import model.items.Level;
import model.users.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminHelperTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void checkIfMethodIsInvokeAtGetRequest() {
        // arrange
        String responseMethod = "GET";
        HttpExchange httpExchangeMock = mock(HttpExchange.class);
        AdminDAO adminDAOMock = mock(AdminDAO.class);
        UtilityService utilityService = spy(UtilityService.class);
        when(httpExchangeMock.getRequestMethod()).thenReturn(responseMethod);
        OutputStream outputStream = new ByteArrayOutputStream();
        when(httpExchangeMock.getResponseBody()).thenReturn(outputStream);
        Level level = new Level(1, "Level_1", 1000);
        List<Level> levels = Collections.singletonList(level);
        try {
            when(adminDAOMock.getLevelList()).thenReturn(levels);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AdminHelper SUT = new AdminHelper(new MentorDAO(), new UserDAO(), new StudentDAO(), adminDAOMock, utilityService);
        // act
        try {
            SUT.showLevels(httpExchangeMock);
        } catch (DBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // assert
        try {
            verify(utilityService).sendResponse(any(), any());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}