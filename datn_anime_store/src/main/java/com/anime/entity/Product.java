package com.anime.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.anime.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -188904471962850857L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer quantity;
	private Double price;
	private String slug;
	private Float discount;
	private Integer selled;
	private Integer view;
	private String description;

	@ManyToOne
	@JoinColumn(name = "categoryId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private Category category;

	@ManyToOne
	@JoinColumn(name = "unitTypeId", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "applications", "hibernateLazyInitializer" })
	private UnitType unitType;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<ImagesProduct> imagesProducts;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<OrderDetail> orderDetails;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<Review> reviews;
}
