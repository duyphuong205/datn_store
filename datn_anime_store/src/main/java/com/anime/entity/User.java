package com.anime.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.anime.entity.base.BaseEntity;
import com.anime.security.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Setter
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Serializable, Account {

	private static final long serialVersionUID = 5368618397244004624L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	private String fullname;

	private String email;
	private String avatarUrl;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<UserRole> userRoles;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Order> orders;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Review> reviews;

	public User(OAuth2User socialUser) {
		this.username = socialUser.getName();
		this.email = socialUser.getAttribute("email");
		this.fullname = socialUser.getAttribute("name");
		this.password = "";
		this.avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png";
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
