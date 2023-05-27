package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.BeverageGoods;

@Repository
public interface StoreInfoOracleDao extends JpaRepository<BeverageGoods, Long> {


}
