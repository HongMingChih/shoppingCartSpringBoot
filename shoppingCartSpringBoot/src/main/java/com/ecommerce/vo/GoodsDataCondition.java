package com.ecommerce.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class GoodsDataCondition {

	private Long goodsID;

	private String goodsName;
	
	private String priceSort;
	
	private Integer startPrice;
	
	private Integer endPrice;
	
	private Integer quantity;
	
	private String status;
	
}
