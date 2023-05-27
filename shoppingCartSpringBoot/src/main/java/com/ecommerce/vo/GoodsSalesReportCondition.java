package com.ecommerce.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class GoodsSalesReportCondition {

	private String startDate;

	private String endDate;
	
	

}
