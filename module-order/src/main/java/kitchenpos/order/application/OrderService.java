package kitchenpos.order.application;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.dto.ChangeOrderStatusRequest;
import kitchenpos.order.dto.OrderRequest;
import kitchenpos.order.dto.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    public OrderService(
            OrderRepository orderRepository
            , OrderValidator orderValidator
    ) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    @Transactional
    public OrderResponse create(final OrderRequest request) {
        Order order = request.toOrder();
        orderValidator.validateCreate(order);

        Order persistOrder = orderRepository.save(order);
        return OrderResponse.of(persistOrder);
    }

    public List<OrderResponse> list() {
        List<Order> persistOrders = orderRepository.findAll();

        return OrderResponse.fromList(persistOrders);
    }

    @Transactional
    public OrderResponse changeOrderStatus(final Long orderId, final ChangeOrderStatusRequest request) {
        Order persistOrder = findById(orderId);
        OrderStatus orderStatus = OrderStatus.valueOf(request.getOrderStatus());

        persistOrder.changeOrderStatus(orderStatus, orderValidator);
        return OrderResponse.of(persistOrder);
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
    }
}