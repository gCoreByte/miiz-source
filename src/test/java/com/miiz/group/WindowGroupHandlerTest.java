package com.miiz.group;

import com.miiz.auth.User;
import com.miiz.auth.UserAuth;
import com.miiz.database.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WindowGroupHandlerTest {

    private AutoCloseable closeable;

    @Mock
    private Database mockDb;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private WindowGroupHandler windowGroupHandler;

    private ByteArrayInputStream setInput(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    @BeforeEach
    public void initBeforeEach() {
        System.setOut(new PrintStream(outContent));
        closeable = MockitoAnnotations.openMocks(this);
        User user = Mockito.mock(User.class, Mockito.withSettings().useConstructor(1L, "test", "testHashPw"));
        Scanner scanner = new Scanner(System.in);
        windowGroupHandler = new WindowGroupHandler(mockDb, user, scanner);
    }

    @AfterEach
    public void doAfterEach() throws Exception {
        closeable.close();
        System.setOut(originalOut);
    }

    @Test
    public void testNewGroup_TooLongName() {
        System.setIn(setInput("aaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        assertFalse(windowGroupHandler.newGroup());
    }

    @Test
    public void testNewGroup_NoName() {
        System.setIn(setInput(""));
        assertFalse(windowGroupHandler.newGroup());
    }

    // TODO: massive rewrites to make it unit testable
    // eg all functions return a true/false depending on if they succeed
    // throw exceptions if fail

}
