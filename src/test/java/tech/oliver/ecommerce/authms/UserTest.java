package tech.oliver.ecommerce.authms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Nested
    class isValidPassword {

        @Test
        @DisplayName("Should Return True When Password Is Valid")
        void shouldReturnTrueWhenPasswordIsValid() {

            String password = "123";
            var user = new User("admin", password);

            var isPasswordValid = user.isValidPassword(password);

            assertTrue(isPasswordValid);
        }

        @Test
        @DisplayName("Should Return False When Password Is Invalid")
        void shouldReturnFalseWhenPasswordIsInvalid() {

            String password = "123";
            String otherPassword = "567";
            var user = new User("admin", password);

            var isPasswordValid = user.isValidPassword(otherPassword);

            assertFalse(isPasswordValid);
        }
    }

    @Nested
    class changePassword {

        @Test
        @DisplayName("Should change password with success")
        void shouldChangePasswordWithSuccess() {

            String password = "123";
            String newPassword = "456";
            var user = new User("admin", password);

            user.changePassword(newPassword);

            assertEquals(newPassword, user.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when password is null")
        void shouldThrowExceptionWhenPasswordIsNull() {

            String password = "123";
            String newPassword = null;
            var user = new User("admin", password);

            var ex = assertThrows(IllegalArgumentException.class, () -> {
                user.changePassword(newPassword);
            });

            assertEquals("A senha não pode ser vazia", ex.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when password is empty")
        void shouldThrowExceptionWhenPasswordIsEmpty() {

            String password = "123";
            String newPassword = "";
            var user = new User("admin", password);

            var ex = assertThrows(IllegalArgumentException.class, () -> {
                user.changePassword(newPassword);
            });

            assertEquals("A senha não pode ser vazia", ex.getMessage());
        }
    }
}