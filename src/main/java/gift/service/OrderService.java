package gift.service;

import gift.dto.OrderRequestDTO;
import gift.dto.OrderResponseDTO;
import gift.model.Option;
import gift.model.Member;
import gift.model.Order;
import gift.repository.OrderRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OptionService optionService;
    private final MemberService memberService;

    public OrderService(OrderRepository orderRepository, OptionService optionService,
        MemberService memberService) {
        this.orderRepository = orderRepository;
        this.optionService = optionService;
        this.memberService = memberService;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO, String email) {
        Option option = optionService.findOptionById(orderRequestDTO.optionId());
        Member member = memberService.findMemberByEmail(email);
        Long quantity = orderRequestDTO.quantity();
        optionService.subtractQuantity(option.getId(), quantity);
        Order order = new Order(null, option, quantity, LocalDateTime.now(), orderRequestDTO.message(), member);
        orderRepository.save(order);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order.getId(),order.getOption().getId(), order.getQuantity(), order.getOrderDateTime(), order.getMessage());
        return orderResponseDTO;
    }

}
