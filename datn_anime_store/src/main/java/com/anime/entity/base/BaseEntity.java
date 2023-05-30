package com.anime.entity.base;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.anime.constants.ActiveConstant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 428860059038751728L;

	@CreatedBy
	@Column(updatable = false)
	private String createdBy;

	@LastModifiedBy
	@Column(insertable = false)
	private String updatedBy;

	@CreationTimestamp
	@Column(updatable = false, insertable = true)
	private Timestamp createDate;

	@UpdateTimestamp
	@Column(insertable = false, updatable = true)
	private Timestamp updateDate;

	private Boolean isActive = ActiveConstant.ENABLE;
}
