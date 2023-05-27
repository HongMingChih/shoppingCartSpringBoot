package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dao.BeverageGoodsOracleDao;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageOrders;
import com.ecommerce.vo.GoodsOrderVo;
import com.ecommerce.vo.GoodsVo;
import com.ecommerce.vo.OrderVo;

@Service
public class GoodsOrderService {
	@Autowired
	private BeverageGoodsOracleDao beverageGoodsOracleDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public BeverageGoods createGoods(GoodsVo goodsVo){
		BeverageGoods storeInfos = (BeverageGoods) beverageGoodsOracleDao.findAll();
		
		return storeInfos;
	}
	/*
	 * 一對多新增
	 */
//	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Transactional()
	public BeverageGoods createGoodsOrder(GoodsOrderVo goodsOrderVo){
		BeverageGoods createGoodsOrder = BeverageGoods.builder()
				.goodsName(goodsOrderVo.getGoodsName())
				.description(goodsOrderVo.getDescription())
				.price(goodsOrderVo.getPrice())
				.quantity(goodsOrderVo.getQuantity())
				.imageName(goodsOrderVo.getImageName())
				.status(goodsOrderVo.getStatus())
				.build();
		List<BeverageOrders> orderVo=new ArrayList<>();
		for (OrderVo beverageOrders : goodsOrderVo.getBeverageOrders()) {
			BeverageOrders beverageOrder= BeverageOrders.builder()
					.orderDate(beverageOrders.getOrderDate())
					.customerID(beverageOrders.getCustomerID())
					.goodsBuyPrice(beverageOrders.getGoodsBuyPrice())
					.buyQuantity(beverageOrders.getBuyQuantity())
					.goodsID(goodsOrderVo.getGoodsID())
					.build();
			orderVo.add(beverageOrder);
		}
		createGoodsOrder.setBeverageOrders(orderVo);
		

		// 透過儲存「一」的那方Geography CascadeType.ALL屬性，就可以連同「多」的那方StoreInfo一併儲存!
		createGoodsOrder = beverageGoodsOracleDao.save(createGoodsOrder);
		
		
		return createGoodsOrder;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BeverageGoods updateGoodsOrder(GoodsOrderVo goodsOrderVo){
		BeverageGoods dbBeverageGoods = null;
		Optional<BeverageGoods> optBeverageGoods = beverageGoodsOracleDao.findById(goodsOrderVo.getGoodsID());
		if(optBeverageGoods.isPresent()){
			dbBeverageGoods = optBeverageGoods.get();
			
			if (goodsOrderVo.getGoodsName() != null) {
			    dbBeverageGoods.setGoodsName(goodsOrderVo.getGoodsName());
			}
			if (goodsOrderVo.getDescription() != null) {
			    dbBeverageGoods.setDescription(goodsOrderVo.getDescription());
			}
			if (goodsOrderVo.getPrice() != 0) {
			    dbBeverageGoods.setPrice(goodsOrderVo.getPrice());
			}
			if (goodsOrderVo.getQuantity() != 0) {
			    dbBeverageGoods.setQuantity(goodsOrderVo.getQuantity());
			}
			if (goodsOrderVo.getImageName() != null) {
			    dbBeverageGoods.setImageName(goodsOrderVo.getImageName());
			}
			if (goodsOrderVo.getStatus() != null) {
			    dbBeverageGoods.setStatus(goodsOrderVo.getStatus());
			}
			List<BeverageOrders> newBeverageOrders = new ArrayList<>();
			List<BeverageOrders> dbBeverageOrders = dbBeverageGoods.getBeverageOrders();
			for(OrderVo orderVo : goodsOrderVo.getBeverageOrders()){
				BeverageOrders beverageOrders = BeverageOrders.builder().goodsID(orderVo.getGoodsID())
						.orderID(orderVo.getOrderID()).build();
				if(dbBeverageOrders.contains(beverageOrders)) {
					beverageOrders = dbBeverageOrders.get(dbBeverageOrders.indexOf(beverageOrders));
				}
			    if (orderVo.getOrderDate()!=null) {
			    	beverageOrders.setOrderDate(orderVo.getOrderDate());
				}
			    if (orderVo.getCustomerID()!=null) {
			    	beverageOrders.setCustomerID(orderVo.getCustomerID());
				}
			    if (orderVo.getGoodsBuyPrice()!=0) {
					beverageOrders.setGoodsBuyPrice(orderVo.getGoodsBuyPrice());
				}
			    if (orderVo.getBuyQuantity()!=0) {
			    	beverageOrders.setBuyQuantity(orderVo.getBuyQuantity());
				}
			    beverageOrders.setGoodsID(goodsOrderVo.getGoodsID());					
				
				beverageOrders.setBeverageGood(dbBeverageGoods);

				newBeverageOrders.add(beverageOrders);
			}
			
			dbBeverageOrders.clear();
			dbBeverageOrders.addAll(newBeverageOrders);
//			dbBeverageGoods.setBeverageOrders(dbBeverageOrders);
			dbBeverageGoods=beverageGoodsOracleDao.save(dbBeverageGoods);
			
		}
		return dbBeverageGoods;
		
		
		
	}
	
}
