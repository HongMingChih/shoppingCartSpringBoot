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
public class GoodsReportSalesInfo {

	private List<OrderVo> ordersDataInfoPage;

	private GenericPageable genericPageble;//
}
