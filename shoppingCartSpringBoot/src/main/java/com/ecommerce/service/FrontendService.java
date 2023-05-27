package com.ecommerce.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.BeverageGoodsOracleDao;
import com.ecommerce.dao.BeverageOrdersDao;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageOrders;
import com.ecommerce.vo.CheckoutCompleteInfo;
import com.ecommerce.vo.GenericPageable;
import com.ecommerce.vo.GoodsDataVo;
import com.ecommerce.vo.GoodsVo;
import com.ecommerce.vo.MemberInfo;
import com.ecommerce.vo.OrderCustomer;
import com.ecommerce.vo.OrderVo;
import com.ecommerce.vo.ProductGoodsInfo;

import lombok.Builder;

@Service
public class FrontendService {
	@Autowired
	private BeverageGoodsOracleDao beverageGoodsOracleDao;
	
	@Autowired
	private BeverageOrdersDao beverageOrdersDao;

	public ProductGoodsInfo queryGoodsData(String searchKeyword, GenericPageable genericPageable) {
		if (searchKeyword.matches("^[a-zA-Z]*$")) { // 如果是英文字串
	        searchKeyword = searchKeyword.toUpperCase(); // 將字串轉成全大寫
	    }
		// page 頁數從零開始代表第一頁
		Pageable pageable = PageRequest.of(genericPageable.getCurrentPageNo()-1, genericPageable.getPageDataSize());
		ProductGoodsInfo productGoodsInfo=new ProductGoodsInfo();
		List<BeverageGoods>	 goodsDataInfoPage=beverageGoodsOracleDao.findByGoodsIDIsNotNullAndSearchKeyword(searchKeyword, pageable);
		List<BeverageGoods>	 goodsDataInfoAll=beverageGoodsOracleDao.findBySearchKeyword(searchKeyword);
		List<GoodsDataVo> goodsDataVo = goodsDataInfoPage.stream()
		        .map(beverageGoods -> GoodsDataVo.builder()
		                .goodsID(beverageGoods.getGoodsID())
		                .goodsName(beverageGoods.getGoodsName())
		                .description(beverageGoods.getDescription())
		                .price(beverageGoods.getPrice())
		                .quantity(beverageGoods.getQuantity())
		                .imageName(beverageGoods.getImageName())
		                .status(beverageGoods.getStatus())
		                .build())
		        .collect(Collectors.toList());
		productGoodsInfo.setGoodsDatas(goodsDataVo);
		genericPageable.setDataTotalSize(goodsDataInfoAll.size());
		productGoodsInfo.setGenericPageble(genericPageable);
		return productGoodsInfo;
	}
	

	@Transactional
	public CheckoutCompleteInfo checkoutGoods(MemberInfo sessionMemberInfo, OrderCustomer customer,
	        List<GoodsVo> cartGoods) {
	    CheckoutCompleteInfo checkoutCompleteInfo = new CheckoutCompleteInfo();
	    // 組合商品
	    Map<GoodsVo, Integer> goodsMap = new HashMap<>();

	    for (GoodsVo goods : cartGoods) {
	        if (goodsMap.containsKey(goods)) {
	            int count = goodsMap.get(goods);
	            goodsMap.put(goods, count + 1);
	        } else {
	            goodsMap.put(goods, 1);
	        }
	    }

	    List<BeverageOrders> orderGoodsList = new ArrayList<>();
	    // 轉成orderGoods
	    for (Map.Entry<GoodsVo, Integer> entry : goodsMap.entrySet()) {
	        GoodsVo goods = entry.getKey();
	        int count = entry.getValue();
	        Optional<BeverageGoods> beverageGoodsOpt = beverageGoodsOracleDao.findById(goods.getGoodsID());
	        if (beverageGoodsOpt.isPresent() && beverageGoodsOpt.get().getQuantity() >= count) {
	            BeverageOrders beverageOrders = BeverageOrders.builder()
	                    .goodsBuyPrice((long) goods.getPrice())
	                    .buyQuantity((long) count)
	                    .customerID(sessionMemberInfo.getIdentificationNo())
	                    .orderDate(LocalDateTime.now())
	                    .goodsID(goods.getGoodsID())
	                    .build();
	            orderGoodsList.add(beverageOrders);
	            beverageOrdersDao.save(beverageOrders);

	            BeverageGoods dbBeverageGoods = beverageGoodsOpt.get();
	            dbBeverageGoods.setQuantity(dbBeverageGoods.getQuantity() - count);
	            dbBeverageGoods.getBeverageOrders().add(beverageOrders);
	            beverageGoodsOracleDao.save(dbBeverageGoods);
	        } else {
	            throw new RuntimeException("商品數量不足");
	        }
	    }
	    checkoutCompleteInfo.setOrderGoodsList(orderGoodsList);
	    return checkoutCompleteInfo;
	}


	
}
