package com.anime.service.impl;

import java.text.DecimalFormat;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.anime.constants.StatusConstant;
import com.anime.dto.CartDetailDto;
import com.anime.dto.CartDto;
import com.anime.entity.Order;
import com.anime.entity.OrderStatus;
import com.anime.entity.PaymentMethod;
import com.anime.entity.Product;
import com.anime.entity.User;
import com.anime.service.CartService;
import com.anime.service.OrderDetailService;
import com.anime.service.OrderService;
import com.anime.service.OrderStatusService;
import com.anime.service.PaymentMethodService;
import com.anime.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final ProductService productService;

	private final OrderService orderService;

	private final OrderDetailService orderDetailService;

	private final PaymentMethodService paymentMethodService;

	private final OrderStatusService orderStatusService;

	@Override
	public CartDto updateCart(CartDto cart, Long productId, Integer quantity, boolean isReplace) {
		Product product = productService.findById(productId);
		Map<Long, CartDetailDto> details = cart.getDetails();

		if (!details.containsKey(productId)) {
			CartDetailDto cartDetailDto = createNewCartDetail(product, quantity);
			details.put(productId, cartDetailDto);
		} else if (quantity > 0) {
			if (isReplace) {
				details.get(productId).setQuantity(quantity);
			} else {
				Integer currentQuantity = details.get(productId).getQuantity();
				Integer newQuantity = currentQuantity + quantity;
				details.get(productId).setQuantity(newQuantity);
			}
		} else {
			details.remove(productId);
		}

		cart.setTotalQuantity(getTotalQuantity(cart));
		cart.setTotalPrice(getTotalPrice(cart));
		return cart;
	}

	@Override
	public Integer getTotalQuantity(CartDto cart) {
		Integer totalQuantity = 0;
		Map<Long, CartDetailDto> details = cart.getDetails();
		return totalQuantity += details.values().stream().mapToInt(item -> item.getQuantity()).sum();
	}

	@Override
	public Double getTotalPrice(CartDto cart) {
		Double totalPrice = 0D;
		Map<Long, CartDetailDto> details = cart.getDetails();
		return totalPrice += details.values().stream().mapToDouble(item -> (item.getQuantity() * item.getPrice()))
				.sum();
	}

	@Override
	public void clearAll(CartDto cart) {
		Map<Long, CartDetailDto> details = cart.getDetails();
		details.clear();
	}

	@Transactional(rollbackOn = { Throwable.class })
	@Override
	public void checkout(CartDto cartDto, User user, String address, String phone, Long paymentMethodId)
			throws Exception {
		if (StringUtils.isAnyEmpty(address, phone)) {
			throw new Exception("Address or Phone must be not null or empty or whitespace!");
		}

		Order order = new Order();
		PaymentMethod paymentMethod = paymentMethodService.findById(paymentMethodId);
		OrderStatus orderStatus = orderStatusService.getByStatus(StatusConstant.NEW);

		order.setUser(user);
		order.setAddress(address);
		order.setPhone(phone);
		order.setPaymentMethod(paymentMethod);
		order.setOrderStatus(orderStatus);

		Order orderResponse = orderService.create(order);

		if (ObjectUtils.isEmpty(orderResponse)) {
			throw new Exception("Insert Orders Failed!");
		}

		Product product = null;
		for (CartDetailDto cartDetailDto : cartDto.getDetails().values()) {
			product = productService.findById(cartDetailDto.getProductId());

			if (cartDetailDto.getQuantity() <= product.getQuantity()) {
				cartDetailDto.setOrderId(orderResponse.getId());
				orderDetailService.insert(cartDetailDto);

				Integer newQuantity = product.getQuantity() - cartDetailDto.getQuantity();
				productService.updateQuantityAndSelled(newQuantity, product.getSelled() + 1, product.getId());
			} else {
				throw new Exception("Order quantity must be less than current product quantity!");
			}

		}
	}

	private CartDetailDto createNewCartDetail(Product product, Integer quantity) {
		CartDetailDto cartDetailDto = new CartDetailDto();
		cartDetailDto.setProductId(product.getId());
		cartDetailDto.setName(product.getName());
		cartDetailDto.setImage(product.getImagesProducts().get(0).getImageUrl());
		cartDetailDto.setQuantity(quantity);
		if (product.getDiscount() != null && product.getDiscount() != 0) {
			DecimalFormat decimalFormat = new DecimalFormat("#.###");
			String priceFormat = decimalFormat.format(product.getPrice() * (1 - product.getDiscount()));
			cartDetailDto.setPrice(Double.parseDouble(priceFormat));
		} else {
			cartDetailDto.setPrice(product.getPrice());
		}
		cartDetailDto.setSlug(product.getSlug());
		return cartDetailDto;
	}

}
