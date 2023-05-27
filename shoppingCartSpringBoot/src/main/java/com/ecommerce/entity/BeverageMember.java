package com.ecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "BEVERAGE_MEMBER", schema="LOCAL")
public class BeverageMember {
	
	@Id
	@Column(name = "IDENTIFICATION_NO")
	private String identificationNo;
	
	@Column(name = "PASSWORD")
	private String cusPassword;
	
	@Column(name = "CUSTOMER_NAME")
	private String cusName;
	
}
