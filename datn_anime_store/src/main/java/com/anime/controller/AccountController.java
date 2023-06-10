package com.anime.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anime.constants.HashPasswordConstant;
import com.anime.constants.StatusConstant;
import com.anime.entity.Order;
import com.anime.entity.OrderDetail;
import com.anime.entity.User;
import com.anime.service.FileService;
import com.anime.service.MailService;
import com.anime.service.OrderDetailService;
import com.anime.service.OrderService;
import com.anime.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {

	private final UserService userService;

	private final FileService fileService;

	private final MailService mailService;

	private final OrderService orderService;

	private final OrderDetailService orderDetailService;

	@GetMapping("/login")
	public String doShowLogin() {
		return "user/login";
	}

	@GetMapping("/login/error")
	public String doLoginError(Model model) {
		model.addAttribute("message", "Tên người dùng hoặc mật khẩu không chính xác!");
		return "user/login";
	}

//	@RequestMapping("/oauth2/login/success")
//	public String oauth2LoginSuccess(OAuth2AuthenticationToken auth) {
//		OAuth2User socialUser = auth.getPrincipal();
//		String email = socialUser.getAttributes().get("email").toString();
//		try {
//			User account = userService.getByUsernameOrEmail(email);
//			userService.setAccount(account);
//			Role role = roleService.getByName(RoleConstant.ROLE_CUSTOMER);
//			UserRole userRole = new UserRole();
//			userRole.setRole(role);
//			userRole.setUser(account);
//		} catch (Exception e) {
//			User account = userService.createFromSocial(socialUser);
//			userService.setAccount(account);
//		}
//		return "forward:/index";
//	}

	@GetMapping("/register")
	public String doShowRegister(Model model) {
		model.addAttribute("userReq", new User());
		return "user/register";
	}

	@PostMapping("/register")
	public String doRegister(@Valid @ModelAttribute("userReq") User userReq, Errors errors, Model model,
			@RequestParam("fileImage") MultipartFile fileImage,
			@RequestParam("confirmPassword") String confirmPassword) {
		try {
			if (errors.hasErrors()) {
				return "user/register";
			} else {
				String imageUrl = "";
				if (fileImage.getSize() == 0 || fileImage == null || fileImage.isEmpty()) {
					imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png";
				} else {
					imageUrl = fileService.uploadFile(fileImage);
				}
				if (userService.getByUsername(userReq.getUsername()) != null) {
					model.addAttribute("existsUsername", "Tên người dùng đã tồn tại!");
					return "user/register";
				}
				if (userService.getByEmail(userReq.getEmail()) != null) {
					model.addAttribute("existsEmail", "Email người dùng đã tồn tại!");
					return "user/register";
				}
				if (confirmPassword == null || confirmPassword.equals("")) {
					model.addAttribute("confirmPasswordNull", "Vui lòng nhập lại mật khẩu!");
					return "user/register";
				}
				if (!confirmPassword.equalsIgnoreCase(userReq.getPassword())) {
					model.addAttribute("notMatchPass", "Mật khẩu không khớp nhau!");
					return "user/register";
				}
				userReq.setAvatarUrl(imageUrl);
				userService.register(userReq);
				return "redirect:/login";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "/user/register";
		}
	}

	@GetMapping("/forgot-password")
	public String doShowForgotPassword() {
		return "user/forgot-password";
	}

	@PostMapping("/forgot-password")
	public String doForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
		try {
			User userResp = userService.getByEmail(email);
			if (userResp == null) {
				redirectAttributes.addFlashAttribute("error", "Email không tồn tại!");
			} else {
				Random random = new Random();
				Integer rdPassoword = random.nextInt(999999);
				String newPassword = String.valueOf(rdPassoword);
				userResp.setPassword(HashPasswordConstant.ENCODER.encode(newPassword));

				userService.updatePassword(userResp.getPassword(), userResp.getId());

				mailService.send(email, "Mật Khẩu Mới",
						"<html><body><div style='margin-left: 22%;'><div class='card' style='width: 600px;'><div style='background-color: #252f3d; height: 50px; border-top-left-radius: 10px; border-top-right-radius: 10px;'><h2 style='color: white; text-align: center; padding-top: 10px;;'>Anime Store</h2></div><div class='card-body' style='border: 1px solid rgb(237, 236, 236);'><b style='font-size: 20px; padding-left: 15px;'>Được xác nhận từ địa chỉ email của bạn</b><p style='padding-top: 10px; padding-left: 15px; padding-right: 15px;'>Đây là mật khẩu mới sẽ được sử dụng trong suốt quá trình sử dụng hệ thống và mua sắm của chúng tôi. Vui lòng không được cung cấp mật khẩu hoặc văn bản này ra bên ngoài. Cảm ơn quý khách!</p><div style='text-align: center; padding-bottom: 15px;'><b>Mật khẩu mới</b><h1 style='font-weight: 900; color: red;'>"
								+ rdPassoword
								+ "</h1></div><hr /><div style='margin-bottom: 10px;'><b style='padding-left: 15px; margin-bottom: 10px;'>Bạn muốn đăng nhập? <a href='http://localhost:8080/anime/login'>Đăng nhập tại đây</a></b></div></div></div></div></body></html>");
				redirectAttributes.addFlashAttribute("message", "Vui lòng kiểm tra email của bạn!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:/forgot-password";
	}

	@GetMapping("/my-order")
	public String doShowOrders(HttpServletRequest request, Model model,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "6") int pageSize) {
		List<Order> ordersNew = new ArrayList<>();
		List<Order> ordersShipping = new ArrayList<>();
		List<Order> ordersSuccess = new ArrayList<>();
		List<Order> ordersCancel = new ArrayList<>();

		try {
			String username = request.getRemoteUser();
			Page<Order> pageOrdersNew = orderService.findOrdersByOrderStatusAndUsername(StatusConstant.NEW, username,
					PageRequest.of(pageNum - 1, pageSize));
			Page<Order> pageOrdersShipping = orderService.findOrdersByOrderStatusAndUsername(StatusConstant.SHIPPING,
					username, PageRequest.of(pageNum - 1, pageSize));
			Page<Order> pageOrdersSuccess = orderService.findOrdersByOrderStatusAndUsername(StatusConstant.SUCCESS,
					username, PageRequest.of(pageNum - 1, pageSize));
			Page<Order> pageOrdersCancel = orderService.findOrdersByOrderStatusAndUsername(StatusConstant.CANCEL,
					username, PageRequest.of(pageNum - 1, pageSize));

			ordersNew = pageOrdersNew.getContent();
			ordersShipping = pageOrdersShipping.getContent();
			ordersSuccess = pageOrdersSuccess.getContent();
			ordersCancel = pageOrdersCancel.getContent();

			model.addAttribute("totalPagesNew", pageOrdersNew.getTotalPages());
			model.addAttribute("totalPagesShipping", pageOrdersShipping.getTotalPages());
			model.addAttribute("totalPagesSuccess", pageOrdersSuccess.getTotalPages());
			model.addAttribute("totalPagesCancel", pageOrdersCancel.getTotalPages());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("ordersNew", ordersNew);
		model.addAttribute("ordersShipping", ordersShipping);
		model.addAttribute("ordersSuccess", ordersSuccess);
		model.addAttribute("ordersCancel", ordersCancel);
		return "/user/my-order";
	}

	@GetMapping("/order-detail/{id}")
	public String doGetOrderDetail(Model model, @PathVariable("id") Long id,
			@RequestParam(value = "page", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "6") int pageSize) {
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
		return "/user/order-detail";
	}
}
