package com.anime.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anime.constants.StatusConstant;
import com.anime.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "order_status")
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -671814563398799215L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer status = StatusConstant.NEW;

	@JsonIgnore
	@OneToMany(mappedBy = "orderStatus")
	private List<Order> orders;
}
