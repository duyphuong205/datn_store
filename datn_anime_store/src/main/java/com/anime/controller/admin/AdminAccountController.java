package com.anime.controller.admin;

import com.anime.constants.HashPasswordConstant;
import com.anime.entity.User;
import com.anime.service.FileService;
import com.anime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class AdminAccountController {

    private final UserService userService;

    private final HttpServletRequest request;

    private final FileService fileService;

    @GetMapping("/admin/edit-profile")
    public String doShowEditProfile(Model model) {
        User user = userService.getByUsername(request.getRemoteUser());
        model.addAttribute("userEdit", user);
        model.addAttribute("avatarUserUrl", user.getAvatarUrl());
        return "admin/edit-profile";
    }

    @PostMapping("/admin/edit-profile")
    public String doEditProfile(@ModelAttribute("userEdit") User user,
                                @RequestParam("currentPassword") String currentPassword,
                                @RequestParam(value = "newPassword", required = false) String newPassword,
                                @RequestParam("fileImage") MultipartFile fileImage,
                                Model model) {
        try {
            User userLogin = userService.getByUsername(request.getRemoteUser());
            if (fileImage.getSize() == 0 || fileImage == null || fileImage.isEmpty()) {
                user.setAvatarUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png");
            } else {
                user.setAvatarUrl(fileService.uploadFileCloudinary(fileImage));
            }
            if (!"".equals(currentPassword) && currentPassword != null) {
                if (!HashPasswordConstant.ENCODER.matches(currentPassword, userLogin.getPassword())) {
                    model.addAttribute("currentPasswordInvalid", "Mật khẩu hiện tại không đúng!");
                    return "admin/edit-profile";
                } else {
                    if ("".equals(newPassword) || newPassword.length() == 0) {
                        model.addAttribute("newPasswordNull", "Mật khẩu mới không được trống!");
                        return "admin/edit-profile";
                    } else {
                        user.setPassword(newPassword);
                    }
                }
            }
            userService.editProfile(user);
            model.addAttribute("avatarUserUrl", user.getAvatarUrl());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "admin/edit-profile";
    }
}
