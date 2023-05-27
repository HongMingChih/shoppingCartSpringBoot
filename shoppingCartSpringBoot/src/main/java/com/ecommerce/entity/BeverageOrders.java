package com.ecommerce.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@Data
@ToString(exclude = {"beverageGood"})
@EqualsAndHashCode(of = { "orderID" })
@Entity
@Table(name = "BEVERAGE_ORDER")
public class BeverageOrders {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "BEVERAGE_ORDER_SEQ_GEN")
	@SequenceGenerator(name = "BEVERAGE_ORDER_SEQ_GEN",sequenceName = "BEVERAGE_ORDER_SEQ",allocationSize = 1)
	@Column(name = "ORDER_ID")
	private Long orderID;
	
	@Column(name = "ORDER_DATE")
	private LocalDateTime orderDate;
	
	@Column(name = "CUSTOMER_ID")
	private String customerID;
	
	
	@Column(name = "GOODS_BUY_PRICE")
	private Long goodsBuyPrice;
	
	
	@Column(name = "BUY_QUANTITY")
	private Long buyQuantity;

	@Column(name = "GOODS_ID")
	private Long goodsID;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "GOODS_ID", insertable = false, updatable = false)
	private BeverageGoods beverageGood;
	
}
