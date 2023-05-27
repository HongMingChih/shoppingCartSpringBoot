package com.ecommerce.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageOrders;
import com.ecommerce.vo.GoodsDataCondition;

@Repository
public interface BeverageGoodsOracleDao extends JpaRepository<BeverageGoods, Long> {

	
		// Enhanced JPQL Syntax
		@Query("SELECT b FROM BeverageGoods b WHERE b.goodsID = ?1")
		List<BeverageGoods> queryByGeographyID(long goodsID);

		
		List<BeverageGoods> findByGoodsIDIsNotNull(GoodsDataCondition condition,Pageable pageable);
		
		//條件查詢(分頁)
		@Query("SELECT b FROM BeverageGoods b " +
			       "WHERE (:#{#condition.goodsID} IS NULL OR b.goodsID = :#{#condition.goodsID}) " +
			       "AND (:#{#condition.goodsName} IS NULL OR UPPER(b.goodsName) LIKE CONCAT('%', :#{#condition.goodsName}, '%')) " +
			       "AND (:#{#condition.startPrice} IS NULL OR b.price >= :#{#condition.startPrice}) " +
			       "AND (:#{#condition.endPrice} IS NULL OR b.price <= :#{#condition.endPrice}) " +
			       "AND (:#{#condition.quantity} IS NULL OR (b.quantity > 0 AND b.quantity <= :#{#condition.quantity})) " +
			       "AND (:#{#condition.status} IS NULL OR b.status = :#{#condition.status}) " +
			       "AND b.goodsID IS NOT NULL")
			List<BeverageGoods> findByGoodsIDIsNotNullAndCondition(@Param("condition") GoodsDataCondition condition, Pageable pageable);
		//條件查詢(全品項)
		@Query("SELECT b FROM BeverageGoods b " +
			       "WHERE (:#{#condition.goodsID} IS NULL OR b.goodsID = :#{#condition.goodsID}) " +
			       "AND (:#{#condition.goodsName} IS NULL OR UPPER(b.goodsName) LIKE CONCAT('%', :#{#condition.goodsName}, '%')) " +
			       "AND (:#{#condition.startPrice} IS NULL OR b.price >= :#{#condition.startPrice}) " +
			       "AND (:#{#condition.endPrice} IS NULL OR b.price <= :#{#condition.endPrice}) " +
			       "AND (:#{#condition.quantity} IS NULL OR (b.quantity > 0 AND b.quantity <= :#{#condition.quantity})) " +
			       "AND (:#{#condition.status} IS NULL OR b.status = :#{#condition.status}) " +
			       "AND b.goodsID IS NOT NULL")
			List<BeverageGoods> findByCondition(@Param("condition") GoodsDataCondition condition);
		
		//條件查詢(分頁)
		@Query("SELECT b FROM BeverageOrders b " +
		        "WHERE (:startDate IS NULL OR b.orderDate >= :startDate) " +
		        "AND (:endDate IS NULL OR b.orderDate <= :endDate) " +
		        "AND b.orderID IS NOT NULL")
		List<BeverageOrders> findByOrderIDIsNotNull(
		        @Param("startDate") LocalDateTime startDate,
		        @Param("endDate") LocalDateTime endDate,
		        Pageable pageable);

		//條件查詢範圍全部(分頁)
		@Query("SELECT b FROM BeverageOrders b " +
		        "WHERE (:startDate IS NULL OR b.orderDate >= :startDate) " +
		        "AND (:endDate IS NULL OR b.orderDate <= :endDate) ")
		List<BeverageOrders> findByOrderDate(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);

		//條件查詢(分頁)
		@Query("SELECT b FROM BeverageGoods b " +
		           "WHERE b.goodsID IS NOT NULL " +
		           "AND UPPER(b.goodsName) LIKE CONCAT('%', :searchKeyword, '%')")
		List<BeverageGoods> findByGoodsIDIsNotNullAndSearchKeyword(String searchKeyword, Pageable pageable);

		//前台條件查詢(全品項)
		@Query("SELECT b FROM BeverageGoods b " +
			       "WHERE (:searchKeyword IS NULL OR b.goodsName LIKE %:searchKeyword%) " +
			       "AND b.goodsID IS NOT NULL")
		List<BeverageGoods> findBySearchKeyword(@Param("searchKeyword") String searchKeyword);

}
