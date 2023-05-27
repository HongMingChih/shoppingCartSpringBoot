package com.ecommerce.vo;

import java.util.List;

import com.ecommerce.entity.BeverageOrders;

import lombok.Data;

@Data
public class CheckoutCompleteInfo {

	private String cusName;

	private String mobileNumber;

	private String homeNumber;

	private String orderAddr;

	 List<BeverageOrders> orderGoodsList;
}
