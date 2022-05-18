package com.miiz.group;

import com.miiz.auth.User;
import com.miiz.auth.UserAuth;
import com.miiz.database.Database;
import com.miiz.utils.exceptions.InvalidInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WindowGroupHandlerTest {

    private AutoCloseable closeable;

    @Mock
    private Database mockDb;
    @Mock
    private WindowGroup windowGroup;
    private User user;
    private ByteArrayInputStream setInput(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    private WindowGroupHandler newHandler(ByteArrayInputStream stream) {
        Scanner scanner = new Scanner(stream);
        return new WindowGroupHandler(mockDb, user, scanner);
    }

    @BeforeEach
    public void initBeforeEach() {
        closeable = MockitoAnnotations.openMocks(this);
        user = Mockito.mock(User.class, Mockito.withSettings().useConstructor(1L, "test", "testHashPw"));
    }

    @AfterEach
    public void doAfterEach() throws Exception {
        closeable.close();
        System.setIn(setInput(""));
    }


    @Test
    public void testNewGroup_TooLongName() {
        ByteArrayInputStream testStream = setInput(
                "aaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n");

        WindowGroupHandler windowGroupHandler = newHandler(testStream);
        assertThrows(InvalidInputException.class, windowGroupHandler::newGroup);
    }



    @Test
    public void testNewGroup_NoName() {
        ByteArrayInputStream testStream = setInput("\n");

        WindowGroupHandler windowGroupHandler = newHandler(testStream);
        assertThrows(InvalidInputException.class, windowGroupHandler::newGroup);
    }

    @Test
    public void testNewGroup_Success() throws InvalidInputException {
        ByteArrayInputStream testStream = setInput("test");
        Mockito.when(mockDb.getWindowGroups()).thenReturn(new ArrayList<>());
        Mockito.when(mockDb.addWindowGroup(Mockito.any())).thenReturn(windowGroup);
        WindowGroupHandler windowGroupHandler = newHandler(testStream);
        windowGroupHandler.newGroup();
        verify(mockDb, times(1)).addWindowGroup(Mockito.any());
    }


}
