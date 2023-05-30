package com.anime.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anime.constants.SessionConstant;
import com.anime.dto.CartDto;
import com.anime.entity.User;
import com.anime.service.CartService;
import com.anime.service.UserService;
import com.anime.utils.SessionUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartApi {

	private final CartService cartService;

	private final UserService userService;

	private final HttpServletRequest request;

	private final SessionUtil session;

	// add, update, delete
	@GetMapping("/update")
	public ResponseEntity<?> doGetUpdate(@RequestParam("productId") Long productId,
			@RequestParam("quantity") Integer quantity, @RequestParam("isReplace") boolean isReplace) {

		CartDto currentCart = session.getAttribute(SessionConstant.CURRENT_CART);
		cartService.updateCart(currentCart, productId, quantity, isReplace);
		return ResponseEntity.ok(currentCart);
	}

	@GetMapping("/checkout")
	public ResponseEntity<?> doGetCheckout(@RequestParam("address") String address, @RequestParam("phone") String phone,
			@RequestParam("paymentMethodId") Long paymentMethodId) {
		User currentUser = userService.getByUsername(request.getRemoteUser());

		if (ObjectUtils.isEmpty(currentUser)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);// 401
		}

		CartDto currentCart = session.getAttribute(SessionConstant.CURRENT_CART);
		try {
			cartService.checkout(currentCart, currentUser, address, phone, paymentMethodId);
			session.setAttribute(SessionConstant.CURRENT_CART, new CartDto());
			return new ResponseEntity<>(HttpStatus.OK);// 200
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);// 400
		}

	}

	@GetMapping("/refresh-data")
	public ResponseEntity<?> doGetRefreshData() {
		CartDto currentCart = session.getAttribute(SessionConstant.CURRENT_CART);
		return ResponseEntity.ok(currentCart);
	}
}
