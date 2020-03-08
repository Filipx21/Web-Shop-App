package pl.filip.shop.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import pl.filip.shop.dto.OrderUserDto;
import pl.filip.shop.mapper.OrderUserMapper;
import pl.filip.shop.services.BuyService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class OrderController {

    private BuyService buyService;
    private OrderUserMapper orderUserMapper;

    public OrderController(BuyService buyService, OrderUserMapper orderUserMapper) {
        this.buyService = buyService;
        this.orderUserMapper = orderUserMapper;
    }

    @GetMapping("/orders")
    public String story(Principal principal, Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(8);
        Page<OrderUserDto> orderPage;

//        orderPage = buyService.findAllOrders(principal.getName(), PageRequest.of(currentPage - 1, pageSize));

        orderPage = fillPage(buyService.findAllOrders(principal.getName())
                .stream()
                .map(orderUserMapper::toOrderUserDto)
                .collect(Collectors.toList()), PageRequest.of(currentPage - 1, pageSize));

        if (!orderPage.hasContent()) {
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
        buyService.buyProduct(email, id);
        return "redirect:/products";
    }

    private Page<OrderUserDto> fillPage(List<OrderUserDto> ordersList, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<OrderUserDto> orders;
        if (ordersList.size() < startItem) {
            orders = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ordersList.size());
            orders = ordersList.subList(startItem, toIndex);
        }
        return new PageImpl<>(orders,
                PageRequest.of(currentPage, pageSize),
                ordersList.size());
    }

}
