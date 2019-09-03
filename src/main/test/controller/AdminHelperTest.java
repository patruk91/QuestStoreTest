package controller;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dao.*;
import model.items.Level;
import model.users.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

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

    @Test
    void checkIfMethodIsInvokeAtPostRequest() {
        // arrange
        String responseMethod = "POST";
        HttpExchange httpExchangeMock = mock(HttpExchange.class);
        AdminDAO adminDAOMock = spy(AdminDAO.class);
        UtilityService utilityService = spy(UtilityService.class);
        when(httpExchangeMock.getRequestMethod()).thenReturn(responseMethod);
        InputStream inputStream = new ByteArrayInputStream("name=super+hard&range=120".getBytes());
        when(httpExchangeMock.getRequestBody()).thenReturn(inputStream);
        Headers headers = new Headers();
        when(httpExchangeMock.getResponseHeaders()).thenReturn(headers);
        try {
            doNothing().when(httpExchangeMock).sendResponseHeaders(anyInt(), anyInt());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            doNothing().when(adminDAOMock).addLevel(anyString(), anyInt());
        } catch (DBException e) {
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
            verify(utilityService).sendRedirect(any(), any());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}