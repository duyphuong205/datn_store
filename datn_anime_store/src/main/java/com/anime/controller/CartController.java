package com.anime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anime.constants.SessionConstant;
import com.anime.dto.CartDto;
import com.anime.service.CartService;
import com.anime.service.PaymentMethodService;
import com.anime.utils.SessionUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {

	private final SessionUtil session;

	private final CartService cartService;
	
	private final PaymentMethodService paymentMethodService;

	@GetMapping("/cart-view")
	public String doShowCartView() {
		return "user/cart";
	}
	
	@GetMapping("/update")
	public String doGetUpdate(@RequestParam("productId") Long productId, 
							  @RequestParam("quantity") Integer quantity,
							  @RequestParam("isReplace") boolean isReplace) {

		CartDto currentCart = session.getAttribute(SessionConstant.CURRENT_CART);
		cartService.updateCart(currentCart, productId, quantity, isReplace);
		return "user/cart::#viewCartFragment";

	}
	
	@GetMapping("/checkout")
	public String doShowCheckout(Model model) {
		model.addAttribute("payments", paymentMethodService.getByIsActive());
		return "user/checkout";
	}

	@GetMapping("/clearAll")
	public String clearCart(CartDto cartDto) {
		cartService.clearAll(cartDto);
		session.removeAttribute(SessionConstant.CURRENT_CART);
		return "redirect:/cart-view";
	}
}
