package com.ecommerce.vo;

import lombok.Data;


@Data
public class OrderCustomer {
	//前端畫畫面好玩的 可有可無
	private String cusName;//收件人姓名
	
	private String mobileNumber;//收件人電話
	
	private String homeNumber;//收件人手機
	
	private String orderAddr;//收件人地址
	
}
