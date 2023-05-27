package com.ecommerce.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dao.BeverageGoodsOracleDao;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageOrders;
import com.ecommerce.vo.GenericPageable;
import com.ecommerce.vo.GoodsDataCondition;
import com.ecommerce.vo.GoodsDataInfo;
import com.ecommerce.vo.GoodsDataVo;
import com.ecommerce.vo.GoodsOrderVo;
import com.ecommerce.vo.GoodsReportSalesInfo;
import com.ecommerce.vo.GoodsSalesReportCondition;
import com.ecommerce.vo.GoodsVo;
import com.ecommerce.vo.OrderVo;

@Service
public class BackendService {

	@Autowired
	private BeverageGoodsOracleDao beverageGoodsOracleDao;

	public BeverageGoods createGoods(GoodsVo goodsVo) throws IOException {
		MultipartFile file = goodsVo.getFile();
		String fileName = file.getOriginalFilename();
		String uploadDir = "C:/home/VendingMachine/DrinksImage";
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
//		    	return null;
		}
		String filePath = "C:/home/VendingMachine/DrinksImage/" + File.separator + fileName;
		File dest = new File(filePath);
		file.transferTo(dest);
		BeverageGoods beverageGoods = new BeverageGoods();
		if (dest.exists()) {
			// 檔案上傳成功
			// 在此處執行後續的程式碼
			beverageGoods = BeverageGoods.builder().goodsName(goodsVo.getGoodsName())
					.description(goodsVo.getDescription()).price(goodsVo.getPrice()).quantity(goodsVo.getQuantity())
					.imageName(fileName)// 檔名
					.status(goodsVo.getStatus()).build();

			beverageGoodsOracleDao.save(beverageGoods);
			return beverageGoods;
		}

		return beverageGoods;
	}

	@Transactional
	public BeverageGoods updateGoodsGoods(GoodsVo goodsVo) throws IOException {
		Optional<BeverageGoods> optBeverageGoods = beverageGoodsOracleDao.findById(goodsVo.getGoodsID());
		BeverageGoods updateBeverageGoods = null;
		if (optBeverageGoods.isPresent()) {
			updateBeverageGoods = optBeverageGoods.get();
			if (goodsVo.getGoodsName() != null) {
				updateBeverageGoods.setGoodsName(goodsVo.getGoodsName());
			}
			if (goodsVo.getDescription() != null) {
				updateBeverageGoods.setDescription(goodsVo.getDescription());
			}
			if (goodsVo.getPrice() != 0) {
				updateBeverageGoods.setPrice(goodsVo.getPrice());
			}
			if (goodsVo.getQuantity() != 0) {
				updateBeverageGoods.setQuantity(goodsVo.getQuantity());
			}
			if (goodsVo.getStatus() != null) {
				updateBeverageGoods.setStatus(goodsVo.getStatus());
			}
			MultipartFile file = goodsVo.getFile();
			if (file != null && file.getSize() > 0) {
				Path path = Paths.get("C:/home/VendingMachine/DrinksImage/")
						.resolve(updateBeverageGoods.getImageName());
				if (Files.exists(path)) {
					Files.delete(path);
				}
				Files.copy(file.getInputStream(),Paths.get("C:/home/VendingMachine/DrinksImage/").resolve(file.getOriginalFilename()));
				updateBeverageGoods.setImageName(file.getOriginalFilename());
			}
		}
		return updateBeverageGoods;
	}

	
	/*
	 * 透過商品ID查詢單筆商品
	 */
	public BeverageGoods queryGoodsByID(long goodsID) {
		List<BeverageGoods>	goods=beverageGoodsOracleDao.queryByGeographyID(goodsID);
		  if (!goods.isEmpty()) {
		        return goods.get(0);
		    }
		 return new BeverageGoods();
	}

	public GoodsDataInfo queryGoodsData(GoodsDataCondition condition, GenericPageable genericPageable) {
		GoodsDataInfo goodsDataInfo=new GoodsDataInfo();
		
				// page 頁數從零開始代表第一頁
				Pageable pageable=null;
				if (null!=condition.getPriceSort()&&condition.getPriceSort().equals("1")) {
					 pageable = PageRequest.of(genericPageable.getCurrentPageNo()-1, genericPageable.getPageDataSize(), 
							Sort.by("price").descending());
				}else {
					 pageable = PageRequest.of(genericPageable.getCurrentPageNo()-1, genericPageable.getPageDataSize(), 
							Sort.by("goodsID").descending());
				}
				
				List<BeverageGoods>	 goodsDataInfoPage=beverageGoodsOracleDao.findByGoodsIDIsNotNullAndCondition(condition, pageable);
				List<BeverageGoods>	 goodsDataInfoAll=beverageGoodsOracleDao.findByCondition(condition);
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
				goodsDataInfo.setGoodsDatas(goodsDataVo);
				genericPageable.setDataTotalSize(goodsDataInfoAll.size());
//				genericPageable.getPagination();
				
				goodsDataInfo.setGenericPageble(genericPageable);
				
				
		return goodsDataInfo;
	}
	/*
	 * 訂單查詢
	 */
	public GoodsReportSalesInfo queryGoodsSales(GoodsSalesReportCondition condition, GenericPageable genericPageable) {
		GoodsReportSalesInfo goodsReportSalesInfo=new GoodsReportSalesInfo();
		// page 頁數從零開始代表第一頁
		Pageable pageable=PageRequest.of(genericPageable.getCurrentPageNo()-1, genericPageable.getPageDataSize(), 
				Sort.by("orderID").descending());
		// 將日期字符串轉換為LocalDateTime對象
		LocalDateTime startDate = null;
		if (condition.getStartDate() != null) {
			 startDate = LocalDateTime.parse(condition.getStartDate() + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}

		LocalDateTime endDate = null;
		if (condition.getEndDate() != null) {
		    endDate = LocalDateTime.parse(condition.getEndDate() + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		
		List<BeverageOrders> ordersDataInfoPage=beverageGoodsOracleDao.findByOrderIDIsNotNull(startDate,endDate, pageable);
		List<BeverageOrders> ordersDataInfoAll=beverageGoodsOracleDao.findByOrderDate(startDate,endDate);
		List<OrderVo> orderDataVo = ordersDataInfoPage.stream()
		        .map(beverageOrders -> OrderVo.builder()
		               .orderID(beverageOrders.getOrderID())
		               .orderDate(beverageOrders.getOrderDate())
		               .customerID(beverageOrders.getCustomerID())
		               .goodsBuyPrice(beverageOrders.getGoodsBuyPrice())
		               .buyQuantity(beverageOrders.getBuyQuantity())
		               .goodsID(beverageOrders.getGoodsID())
		               .build())
		        .collect(Collectors.toList());
		goodsReportSalesInfo.setOrdersDataInfoPage(orderDataVo);
		genericPageable.setDataTotalSize(ordersDataInfoAll.size());
		goodsReportSalesInfo.setGenericPageble(genericPageable);
		
		return goodsReportSalesInfo;
	}
	
	
	/*
	 * 全部商品查詢
	 */
	public List<BeverageGoods> queryAllGoods() {
		List<BeverageGoods>	 goodsDataInfoAll=beverageGoodsOracleDao.findAll();
		
		return goodsDataInfoAll;
	}

	
	

}
