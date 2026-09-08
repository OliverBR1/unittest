package tech.oliver.ecommerce.authms.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.oliver.ecommerce.authms.User;
import tech.oliver.ecommerce.authms.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private User user;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userAc;

    @InjectMocks
    private AuthService authService;


    @Nested
    class authenticate {

        @Test
        @DisplayName("Should return false when user does not exist")
        void shoudlReturnFalseWhenUserDoesNotExist() {

            var username = "admin";
            var password = "123";
            doReturn(null).when(userRepository).findByUsername(eq(username));

            var isAuth = authService.authenticate(username, password);

            assertFalse(isAuth);
            verify(userRepository, times(1)).findByUsername(eq(username));
        }

        @Test
        @DisplayName("Should return false when user exist and password is invalid")
        void shoudlReturnFalseWhenUserExistAndPasswordIsInvalid() {

            var username = "admin";
            var password = "123";
            doReturn(user).when(userRepository).findByUsername(eq(username));
            doReturn(false).when(user).isValidPassword(eq(password));

            var isAuth = authService.authenticate(username, password);

            assertFalse(isAuth);
            verify(userRepository, times(1)).findByUsername(eq(username));
            verify(user, times(1)).isValidPassword(eq(password));
        }

        @Test
        @DisplayName("Should return true when user exist and password is valid")
        void shoudlReturnTrueWhenUserExistAndPasswordIsValid() {

            var username = "admin";
            var password = "123";
            doReturn(user).when(userRepository).findByUsername(eq(username));
            doReturn(true).when(user).isValidPassword(eq(password));

            var isAuth = authService.authenticate(username, password);

            assertTrue(isAuth);
            verify(userRepository, times(1)).findByUsername(eq(username));
            verify(user, times(1)).isValidPassword(eq(password));
        }
    }

    @Nested
    class register {

        @Test
        @DisplayName("Should register with success when user does not exist")
        void shouldRegisterWithSuccessWhenUserDoesNotExist() {

            var username = "admin";
            var password = "123";
            doReturn(null).when(userRepository).findByUsername(eq(username));

            authService.register(username, password);

            verify(userRepository, times(1)).findByUsername(eq(username));
            verify(userRepository, times(1)).save(userAc.capture());
            var userCaptured = userAc.getValue();
            assertEquals(username, userCaptured.getUsername());
            assertEquals(password, userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should NOT register when user already exist")
        void shouldNotRegisterWhenUserAlreadyExist() {

            var username = "admin";
            var password = "123";
            doReturn(user).when(userRepository).findByUsername(eq(username));

            var ex = assertThrows(IllegalArgumentException.class, () -> {
                authService.register(username, password);
            });

            assertEquals("Usuário já existe", ex.getMessage());
            verify(userRepository, times(1)).findByUsername(eq(username));
            verify(userRepository, times(0)).save(any());
        }
    }
}