import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.oliver.Calculadora;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadorTest {

    private Calculadora calculadora = new Calculadora();


    @Nested
    class somar {

        @Test
        @DisplayName("Should add two numbers")
        public void shouldAddTwoNumbers() {
            // Arrange
            int a = 2;
            int b = 3;
            // Act
            var output = calculadora.somar(a, b);
            // Assert
            assertEquals(5, output);
        }

        @Test
        @DisplayName("Should add when two numbers is zero")
        void shouldAddWhenTwoNumbersIsZero() {
            // Arrange
            int a = 0;
            int b = 0;
            // Act
            var output = calculadora.somar(a, b);
            // Assert
            assertEquals(0, output);
        }
    }

    @Nested
    class subtrair {
        @Test
        @DisplayName("Should subtract two numbers")
        void shouldSubtractTwoNumbers() {
            // Arrange
            int a = 5;
            int b = 1;
            // Act
            var output = calculadora.subtrair(a, b);
            // Assert
            assertEquals(4, output);
        }
    }

    @Nested
    class multiplicar {

        @Test
        @DisplayName("Should multiply two numbers")
        void shouldMultiplyTwoNumbers() {
            // Arrange
            int a = 5;
            int b = 2;
            // Act
            var output = calculadora.multiplicar(a, b);
            // Assert
            assertEquals(10, output);
        }
    }

    @Nested
    class dividir {

        @Test
        @DisplayName("Should divide two numbers")
        void shouldDivideTwoNumbers() {
            // Arrange
            int a = 4;
            int b = 2;
            // Act
            var output = calculadora.dividir(4, 2);
            // Assert
            assertEquals(2, output);
        }

        @Test
        @DisplayName("Should throw exception when divide to zero")
        void shouldThrowExceptionWhenDivideToZero() {
            // Arrange
            int a = 4;
            int b = 0;
            // Act & Assert
            var ex = assertThrows(ArithmeticException.class, () -> {
                calculadora.dividir(4, 0);
            });
            assertEquals("Divisão por zero não permitida.", ex.getMessage());
        }
    }

    @Nested
    class potencia {

        @Test
        @DisplayName("Should calculate pow correctly")
        void shouldCalculatePowCorrectly() {
            // Arrange
            int base = 2;
            int expoente = 2;
            // Act
            var output = calculadora.potencia(base, expoente);
            // Assert
            assertEquals(4, output);
        }
    }

    @Nested
    class raizQuadrada {

        @Test
        @DisplayName("Should calculate sqrt correctly")
        void shouldCalculateSqrtCorrectly() {
            // Arrange
            int number = 9;
            // Act
            var output = calculadora.raizQuadrada(number);
            // Assert
            assertEquals(3, output);
        }

        @Test
        @DisplayName("Should throw exception when number is negative")
        void shouldThrowExceptionWhenNumberIsNegative() {
            // Arrange
            int number = -1;
            // Act & Assert
            assertThrows(ArithmeticException.class, () -> {
                calculadora.raizQuadrada(number);
            });
        }
    }

    @Nested
    class absoluto {

        @Test
        @DisplayName("Should calculate abs correctly")
        void shouldCalculateAbsCorrectly() {
            // Arrange
            int number = -50;
            // Act
            var output = calculadora.absoluto(number);
            // Assert
            assertEquals(50, output);
        }
    }

    @Nested
    class ehPar {

        @Test
        @DisplayName("Should be true")
        void shouldBeTrue() {
            // Arrange
            int number = 4;
            // Act
            var output = calculadora.ehPar(number);
            // Assert
            assertTrue(output);
        }

        @Test
        @DisplayName("Should be false")
        void shouldBeFalse() {
            // Arrange
            int number = 3;
            // Act
            var output = calculadora.ehPar(number);
            // Assert
            assertFalse(output);
        }
    }

    @Nested
    class ehPrimo {

        @Test
        @DisplayName("Should be true when 3")
        void shouldBeTrueWhen3() {
            // Arrange
            int number = 3;
            // Act
            var output = calculadora.ehPrimo(number);
            // Assert
            assertTrue(output);
        }

        @Test
        @DisplayName("Should be false when 4")
        void shouldBeTrueWhen4() {
            // Arrange
            int number = 4;
            // Act
            var output = calculadora.ehPrimo(number);
            // Assert
            assertFalse(output);
        }
    }

    @Nested
    class maximo {

        @Test
        @DisplayName("Should be the max number")
        void shouldBeTheMaxNumber() {
            // Arrange
            int a = 3;
            int max = 10;
            // Act
            var output = calculadora.maximo(a, max);
            // Assert
            assertEquals(max, output);
        }
    }

    @Nested
    class minimo {

        @Test
        @DisplayName("Should be the min number")
        void shouldBeTheMinNumber() {
            // Arrange
            int min = 3;
            int b = 10;
            // Act
            var output = calculadora.minimo(min, b);
            // Assert
            assertEquals(min, output);
        }
    }
}
