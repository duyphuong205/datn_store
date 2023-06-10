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
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5368618397244004624L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Tên người dùng không được để trống!")
	private String username;

	@NotBlank(message = "Mật khẩu không được để trống!")
	private String password;

	@NotBlank(message = "Họ tên người dùng không được để trống!")
	private String fullname;

	@NotBlank(message = "Email không được để trống!")
	@Email(message = "Email không đúng định dạng!")
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
}
