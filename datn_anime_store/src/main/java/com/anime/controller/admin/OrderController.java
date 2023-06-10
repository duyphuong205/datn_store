package com.anime.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anime.entity.Order;
import com.anime.entity.OrderDetail;
import com.anime.entity.OrderStatus;
import com.anime.service.OrderDetailService;
import com.anime.service.OrderService;
import com.anime.service.OrderStatusService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	
	private final OrderStatusService orderStatusService;
	
	private final OrderDetailService orderDetailService;

	@GetMapping("/admin/order")
	public String doShowView(Model model, 
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "10") int pageSize) {
		List<Order> orders = new ArrayList<>();
		Page<Order> pageOrders = null;
		try {
			if (username == null || "".equals(username.trim())) {
				pageOrders = orderService.getByIsActive(PageRequest.of(pageNumber - 1, pageSize));
			} else {
				pageOrders = orderService.getOrdersByUsername(username, PageRequest.of(pageNumber - 1, pageSize));
			}

			orders = pageOrders.getContent();
			model.addAttribute("totalPages", pageOrders.getTotalPages());
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("currentUsername", username);
			model.addAttribute("usernames", orderService.getListUsernameOrdered());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("orders", orders);
		return "admin/order";
	}

	@ResponseBody
	@GetMapping("/admin/order/edit/{id}")
	public Order doEdit(@PathVariable("id") Long id) {
		return orderService.findById(id);
	}
	
	@PostMapping("/admin/order/update")
	public String doUpdate(@RequestParam("orderStatus") Integer status,
						   @RequestParam("id") Long orderId) {
		try {
			OrderStatus orderStatus = orderStatusService.getByStatus(status);
			
			Order order = orderService.findById(orderId);
			order.setOrderStatus(orderStatus);
			
			orderService.update(order);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/admin/order";
	}
	
	@GetMapping("/admin/order-detail/{id}")
	public String doGetOrderDetail(Model model, @PathVariable("id") Long id,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "10") int pageSize) {
		List<OrderDetail> orderDetails = new ArrayList<>();
		try {
			Page<OrderDetail> pageOrderDetails = orderDetailService.getByOrderId(id,
					PageRequest.of(pageNum - 1, pageSize));
			orderDetails = pageOrderDetails.getContent();
			model.addAttribute("totalPages", pageOrderDetails.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("orderDetails", orderDetails);
		return "/admin/order-detail";
	}
}
