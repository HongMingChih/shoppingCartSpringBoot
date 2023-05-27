package com.ecommerce.vo;

import java.util.List;

import javax.validation.constraints.Min;

import com.ecommerce.entity.BeverageOrders;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
public class GoodsOrderVo {

	private long goodsID;
	
	private String goodsName;

	private String description;	

	private int price;

	private int quantity;

	private String imageName;

	private String status;
	
	@Min(value = 1, message = "orderVo greater than zero")
	private List<OrderVo> beverageOrders;
	
}
