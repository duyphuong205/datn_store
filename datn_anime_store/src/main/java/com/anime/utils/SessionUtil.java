package com.anime.utils;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionUtil {

	private final HttpSession session;
	
	public void setAttribute(String name, Object value) {
        session.setAttribute(name, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String name) {
        return (T) session.getAttribute(name);
    }

    public void removeAttribute(String name) {
        session.removeAttribute(name);
    }
}
