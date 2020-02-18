package pl.filip.shop.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.filip.shop.model.OrderUser;
import pl.filip.shop.services.BuyService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class OrderController {

    private BuyService buyService;

    public OrderController(BuyService buyService) {
        this.buyService = buyService;
    }

    @GetMapping("/orders")
    public String story(Principal principal, Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<OrderUser> orderPage;

        orderPage = buyService.findAllOrders(principal.getName(), PageRequest.of(currentPage - 1, pageSize));

        if (orderPage == null) {
            return "not_found.html";
        }

        model.addAttribute("ordersPage", orderPage);

        int totalPages = orderPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("ordersPageNumbers", pageNumbers);
        }


        return "orders.html";
    }

    @GetMapping("/buy/{id}")
    public String buyProduct(@PathVariable("id") Long id, Principal principal){
        String email = principal.getName();
        OrderUser order = buyService.buyProduct(email, id);
        return "redirect:/products";
    }
}
