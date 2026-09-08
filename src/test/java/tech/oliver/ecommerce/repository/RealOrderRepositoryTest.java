package tech.oliver.ecommerce.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.internal.matchers.Or;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.oliver.ecommerce.Order;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RealOrderRepositoryTest {

    @Spy
    private HashMap<Integer, Order> ordersSpy;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @InjectMocks
    private RealOrderRepository realOrderRepository;

    @Nested
    class save {

        @Test
        @DisplayName("Should save order")
        void shouldSaveOrder() {

            var dummyOrder = new Order(1, "Bruno", 200.0);

            realOrderRepository.save(dummyOrder);

            verify(ordersSpy, times(1)).put(eq(dummyOrder.getId()), orderArgumentCaptor.capture());
            var orderCaptured = orderArgumentCaptor.getValue();
            assertSame(dummyOrder, orderCaptured);
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Should find by id when order exists")
        void shouldFindByIdWhenOrderExists() {

            var id = 1;
            var dummyOrder = new Order(1, "Bruno", 200.0);
            doReturn(dummyOrder).when(ordersSpy).get(eq(id));

            var order = realOrderRepository.findById(id);

            assertNotNull(order);
            assertSame(dummyOrder, order);
            verify(ordersSpy, times(1)).get(eq(id));
        }

        @Test
        @DisplayName("Should return null when order does not exists")
        void shouldReturnNullWhenOrderDoesNotExists() {

            var id = 1;
            doReturn(null).when(ordersSpy).get(eq(id));

            var order = realOrderRepository.findById(id);

            assertNull(order);
            verify(ordersSpy, times(1)).get(eq(id));
        }
    }
}