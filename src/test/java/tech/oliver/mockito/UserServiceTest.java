package tech.oliver.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.oliver.ecommerce.Order;
import tech.oliver.ecommerce.repository.OrderRepository;
import tech.oliver.ecommerce.service.OrderService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @InjectMocks
    private OrderService orderService;

    @Nested
    class placeOrder {

        @Test
        @DisplayName("Should place order with success")
        void shouldPlaceOrderWithSuccess() {

            var dummyOrder = new Order(1, "Bruno", 200.0);

            orderService.placeOrder(dummyOrder);

            verify(repository, times(1)).save(orderArgumentCaptor.capture());
            var orderCaptured = orderArgumentCaptor.getValue();
            assertEqualsOrder(dummyOrder, orderCaptured);
        }

        @ParameterizedTest
        @ValueSource(doubles = {0, -2.0, -50.0})
        @DisplayName("Should throw exception when total is below or equal zero")
        void shouldThrowExceptionWhenTotalIsBelowOrEqualZero(double total) {

            var dummyOrder = new Order(1, "Bruno", total);

            assertThrows(IllegalArgumentException.class, () -> {
                orderService.placeOrder(dummyOrder);
            });
            verify(repository, times(0)).save(any());
        }

        @Test
        @DisplayName("Should throw exception when place order")
        void shouldThrowExceptionWhenPlaceOrder() {

            var dummyOrder = new Order(1, "Bruno", 200.0);
            doThrow(new RuntimeException()).when(repository).save(any());

            assertThrows(RuntimeException.class, () -> {
                orderService.placeOrder(dummyOrder);
            });
        }
    }

    @Nested
    class getOrder {

        @Test
        @DisplayName("Should return order when exists")
        void shouldReturnOrderWhenExists() {

            int orderId = 1;
            var dummyOrder = new Order(1, "Bruno", 200.0);
            doReturn(dummyOrder).when(repository).findById(eq(orderId));

            var order = orderService.getOrder(orderId);

            assertNotNull(order);
            assertEqualsOrder(dummyOrder, order);
            verify(repository, times(1)).findById(eq(orderId));
        }

        @Test
        @DisplayName("Should return null when order does not exists")
        void shouldReturnNullWhenOrderDoesNotExists() {

            int orderId = 1;
            doReturn(null).when(repository).findById(eq(orderId));

            var order = orderService.getOrder(orderId);

            assertNull(order);
            verify(repository, times(1)).findById(eq(orderId));
        }
    }

    private static void assertEqualsOrder(Order dummyOrder, Order order) {
        assertEquals(dummyOrder.getId(), order.getId());
        assertEquals(dummyOrder.getCustomer(), order.getCustomer());
        assertEquals(dummyOrder.getTotal(), order.getTotal());
    }
}