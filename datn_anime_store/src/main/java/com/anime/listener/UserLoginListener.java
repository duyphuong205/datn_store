package com.anime.listener;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userLoginListener")
public class UserLoginListener implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
