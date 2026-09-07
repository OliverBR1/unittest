package tech.oliver.mockito;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private static Database database;

    @Spy
    private static RealDatabase databaseSpy;

    @InjectMocks
    private static UserService userService;

    @BeforeAll
    public static void beforeAll() {
    }

    @Nested
    class getUserStatus {

        @Test
        @DisplayName("Should return an active user")
        void shouldReturnAnActiveUser() {
            // Arrange
            int userId = 1;
            String expectedStatus = "ACTIVE";
            doReturn(expectedStatus).when(database).getStatus(eq(userId)); // test stub

            // Act
            var output = userService.getUserStatus(userId);

            // Assert
            assertEquals(expectedStatus, output);
            verify(database, times(1)).getStatus(eq(userId));
        }
    }
}
