package com.ecommerce.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
public class OrderVo {

	private long orderID;
	
	private LocalDateTime orderDate;

	private String customerID;

	private long goodsBuyPrice;

	private long buyQuantity;
	
	private Long goodsID;
	
	
}
