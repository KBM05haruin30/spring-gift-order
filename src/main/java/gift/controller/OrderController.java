package gift.controller;


import gift.annotation.LoginMember;
import gift.dto.OrderDTO;
import gift.dto.OrderResponseDTO;
import gift.model.Member;
import gift.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/orders/{optionId}")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showOrderForm(@PathVariable Long optionId, Model model) {
        OrderResponseDTO orderDTO = new OrderResponseDTO(optionId, 10L, "임시 메시지");
        model.addAttribute("orderDTO", orderDTO);
        return "order_form";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addOrder(@PathVariable Long optionId, @RequestBody @Valid OrderDTO orderDTO,
        @LoginMember Member member) {
        String accessToken = orderDTO.accessToken();
        try {
            orderService.createOrder(orderDTO, member.getEmail());
            return ResponseEntity.ok("주문이 성공적으로 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
