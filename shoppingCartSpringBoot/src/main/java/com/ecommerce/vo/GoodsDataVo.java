package com.ecommerce.vo;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GoodsDataVo {

	private long goodsID;

	private String goodsName;

	private String description;

	private int price;

	private int quantity;

	private String imageName;

	private String status;

}
