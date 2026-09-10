package tech.oliver.bankguard;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionValidatorTest {

    private TransactionValidator validator = new TransactionValidator();

    @Nested
    class validateTransaction {

        @Test
        void shouldThrowIllegalArgumentWhenTransactionIsZero() {

            var transaction = new Transaction(
                    0, "PIX", false, 0
            );

            assertThrows(IllegalArgumentException.class, () -> {
                validator.validateTransaction(transaction);
            });
        }

        @Test
        void shouldThrowIllegalArgumentWhenTransactionIsNegative() {

            var transaction = new Transaction(
                    -2, "PIX", false, 0
            );

            assertThrows(IllegalArgumentException.class, () -> {
                validator.validateTransaction(transaction);
            });
        }

        @Test
        void shouldReturnBlockedWhenFailedAttemptsIsEqualTo3() {

            var transaction = new Transaction(
                    20, "PIX", false, 3
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("BLOCKED", output);
        }

        @Test
        void shouldReturnBlockedWhenFailedAttemptsIsBiggerThan3() {

            var transaction = new Transaction(
                    20, "PIX", false, 4
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("BLOCKED", output);
        }

        @Test
        void shouldReturnManualReviewWhenIsInternationalAndValueIsBiggerThan1000() {

            var transaction = new Transaction(
                    1001, "PIX", true, 0
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("MANUAL REVIEW", output);
        }

        @Test
        void shouldReturnManualReviewWhenIsNotInternationalAndValueIsBiggerThan1000() {

            var transaction = new Transaction(
                    1001, "PIX", false, 0
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("APPROVED", output);
        }

        @Test
        void shouldReturnApprovedWhenIsInternationalAndValueIsSmallerThan1000() {

            var transaction = new Transaction(
                    0.5, "PIX", true, 0
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("APPROVED", output);
        }

        @Test
        void shouldReturnApprovedWhenIsInternationalAndValueIsEqualTo1000() {

            var transaction = new Transaction(
                    1000, "PIX", true, 0
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("APPROVED", output);
        }

        @Test
        void shouldReturnManualReviewWhenIsPixAndIsBiggerThan5000() {

            var transaction = new Transaction(
                    5001, "PIX", false, 0
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("MANUAL REVIEW", output);
        }

        @Test
        void shouldReturnApprovedWhenIsPixAndIsEqualTo5000() {

            var transaction = new Transaction(
                    5000, "PIX", false, 0
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("APPROVED", output);
        }

        @Test
        void shouldReturnBlockedWhenIsBiggerThan10000() {

            var transaction = new Transaction(
                    10001, "TED", false, 0
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("BLOCKED", output);
        }

        @Test
        void shouldReturnApprovedWhenTEDAndIsEqualTo10000() {

            var transaction = new Transaction(
                    10000, "TED", false, 0
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("APPROVED", output);
        }

        @Test
        void shouldReturnApprovedWhenIsPixAndIsSmallerThan5000() {

            var transaction = new Transaction(
                    3500, "PIX", false, 0
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("APPROVED", output);
        }

        @Test
        void shouldReturnApprovedWhenIsPixAndIsEqualToCents() {
            var transaction = new Transaction(
                    0.5, "PIX", false, 0
            );

            var output = validator.validateTransaction(transaction);

            assertEquals("APPROVED", output);
        }
    }
}