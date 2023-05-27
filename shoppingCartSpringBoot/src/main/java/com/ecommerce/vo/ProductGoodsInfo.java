package com.ecommerce.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@Data
@ToString
public class ProductGoodsInfo {
	
	private List<GoodsDataVo> goodsDatas;//此頁商品數
	

	private GenericPageable genericPageble;//
	
	
}
