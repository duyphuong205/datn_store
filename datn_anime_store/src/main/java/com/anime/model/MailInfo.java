package com.anime.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo implements Serializable {

	private static final long serialVersionUID = 2285467267052619555L;

	private String from;
	private String to;
	private String subject;
	private String body;

	public MailInfo(String to, String subject, String body) {
		this.from = "animeshop2462@gmail.com";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}
