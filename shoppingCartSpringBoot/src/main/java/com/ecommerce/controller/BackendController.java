package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.service.BackendService;
import com.ecommerce.vo.GenericPageable;
import com.ecommerce.vo.GoodsDataCondition;
import com.ecommerce.vo.GoodsDataInfo;
import com.ecommerce.vo.GoodsReportSalesInfo;
import com.ecommerce.vo.GoodsSalesReportCondition;
import com.ecommerce.vo.GoodsVo;

import io.swagger.annotations.ApiOperation;
@CrossOrigin
@RestController
@RequestMapping("/ecommerce/BackendController")
public class BackendController {
		
	@Autowired
	private BackendService backendService;
	
	
	@ApiOperation(value = "購物網-後臺-查詢商品列表")
	@GetMapping(value = "/queryGoodsData")
	public ResponseEntity<GoodsDataInfo> queryGoodsData(@RequestParam(required = false) Integer goodsID, 
			 @RequestParam(required = false) String goodsName, @RequestParam(required = false) String priceSort,
			 @RequestParam(required = false) Integer startPrice, @RequestParam(required = false) Integer endPrice, 
			 @RequestParam(required = false) Integer quantity, @RequestParam String status,
			 @RequestParam int currentPageNo, @RequestParam int pageDataSize, @RequestParam int pagesIconSize) {
		Long longGoodsID = null;
		if (goodsID != null) {
		    longGoodsID = Long.valueOf(goodsID);
		}
		GoodsDataCondition condition = GoodsDataCondition.builder().goodsID(longGoodsID).goodsName(goodsName)
				.startPrice(startPrice).endPrice(endPrice).priceSort(priceSort).quantity(quantity).status(status).build();
		
		GenericPageable genericPageable = GenericPageable.builder().currentPageNo(currentPageNo)
				.pageDataSize(pageDataSize).pagesIconSize(pagesIconSize).build();
				
		GoodsDataInfo goodsDataInfo = backendService.queryGoodsData(condition, genericPageable);		
		
		return ResponseEntity.ok(goodsDataInfo);
	}
	
	
	@ApiOperation(value = "購物網-後臺-商品訂單查詢(一個商品對應到多筆訂單)")
	@GetMapping(value = "/queryGoodsSales")
	public ResponseEntity<GoodsReportSalesInfo> queryGoodsSales(
			 @RequestParam String startDate, @RequestParam String endDate,  
			 @RequestParam int currentPageNo, @RequestParam int pageDataSize, @RequestParam int pagesIconSize) {
		/*
		 startDate:2022/09/19
		 endDate:2022/09/19
		 currentPageNo:1
		 pageDataSize: 3
		 pagesIconSize: 3
		 */	
		GoodsSalesReportCondition condition = GoodsSalesReportCondition.builder().startDate(startDate).endDate(endDate).build();
		
		GenericPageable genericPageable = GenericPageable.builder().currentPageNo(currentPageNo)
				.pageDataSize(pageDataSize).pagesIconSize(pagesIconSize).build();
		
		GoodsReportSalesInfo goodsReportSalesInfo = backendService.queryGoodsSales(condition, genericPageable);
		
		return ResponseEntity.ok(goodsReportSalesInfo);
	}
	
	@ApiOperation(value = "購物網-後臺-商品維護作業-查詢單一商品資料")
	@GetMapping(value = "/queryGoodsByID")
	public ResponseEntity<BeverageGoods> queryGoodsByID(@RequestParam long goodsID){
		
		BeverageGoods goodsData = backendService.queryGoodsByID(goodsID);
		
		return ResponseEntity.ok(goodsData);
	}
	
	@ApiOperation(value = "購物網-後臺-商品維護作業-查詢全部商品清單")
	@GetMapping(value = "/queryAllGoods")
	public ResponseEntity<List<BeverageGoods>> queryAllGoods() {
		
		List<BeverageGoods> goodsDatas = backendService.queryAllGoods();
		
		return ResponseEntity.ok(goodsDatas);
	}
		
	@ApiOperation(value = "購物網-後臺-商品新增作業")
	@PostMapping(value = "/createGoods", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<BeverageGoods> createGoods(@ModelAttribute GoodsVo goodsVo) throws IOException {
		
		BeverageGoods goods = backendService.createGoods(goodsVo);
		
		return ResponseEntity.ok(goods);
	}
	
	
	
	@ApiOperation(value = "購物網-後臺-商品維護作業-更新商品資料")
	@PostMapping(value = "/updateGoods", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<BeverageGoods> updateGoods(@ModelAttribute GoodsVo goodsVo) throws IOException {
		
		BeverageGoods goods = backendService.updateGoodsGoods(goodsVo);
		
		return ResponseEntity.ok(goods);
	}
	


	
	
	
}
