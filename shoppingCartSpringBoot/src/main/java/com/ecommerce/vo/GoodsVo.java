package com.ecommerce.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@SuperBuilder
@NoArgsConstructor
@Data
public class GoodsVo {
	
	private long goodsID;
	
	private String goodsName;
	
	private String description;
	
	private int price;
	
	private int quantity;
	
	private MultipartFile file;
	
	private String imageName;
	
	private String status;
	
	
}
