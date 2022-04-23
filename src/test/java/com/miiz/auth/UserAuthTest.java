package com.miiz.auth;

import com.miiz.database.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;


public class UserAuthTest {

    private AutoCloseable closeable;

    @Mock
    private static Database mockDb;
    private UserAuth userAuth;


    @BeforeEach
    public void initBeforeEach() {
        closeable = MockitoAnnotations.openMocks(this);
        userAuth = new UserAuth(mockDb);
    }

    @AfterEach
    public void doAfterEach() throws Exception {
        closeable.close();
    }

    @Test
    public void testUserLogin_WrongUsername() {
        // returned fake user
        User user = new User(-1L, "test", "test");
        Mockito.when(mockDb.getUserByUsername(Mockito.anyString())).thenReturn(user);

        assertFalse(userAuth.userLogin("a","b"));
    }

    @Test
    public void testUserLogin_WrongPassword() {
        // returned fake user
        User user = new User(-1L, "test", "a");
        Mockito.when(mockDb.getUserByUsername(Mockito.anyString())).thenReturn(user);

        assertFalse(userAuth.userLogin("a","test"));
    }

    @Test
    public void testUserLogin_Success() {
        // returned fake user
        // hashedpw = test
        User user = new User(1, "test", "$2a$12$anNJsMu/Y8tQJ.0TO8hFGu9I/DxJfX613Y194WYgylvb5Arkt.KSa");
        Mockito.when(mockDb.getUserByUsername(Mockito.anyString())).thenReturn(user);

        assertTrue(userAuth.userLogin("test", "test"));
    }

    @Test
    public void testUserRegister_NameInUse() {
        // returned fake user
        // hashedpw = test
        User user = new User(1, "test", "$2a$12$anNJsMu/Y8tQJ.0TO8hFGu9I/DxJfX613Y194WYgylvb5Arkt.KSa");
        Mockito.when(mockDb.getUserByUsername(Mockito.anyString())).thenReturn(user);

        assertFalse(userAuth.userRegister("test", "test"));
    }

    @Test
    public void testUserRegister_Success() {
        // returned fake user
        // hashedpw = test
        User user = new User(-1, "test", "$2a$12$anNJsMu/Y8tQJ.0TO8hFGu9I/DxJfX613Y194WYgylvb5Arkt.KSa");
        Mockito.when(mockDb.getUserByUsername(Mockito.anyString())).thenReturn(user);
        assertTrue(userAuth.userRegister("test", "test"));
    }


}
