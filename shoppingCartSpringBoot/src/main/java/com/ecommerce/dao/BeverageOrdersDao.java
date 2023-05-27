package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.BeverageOrders;

@Repository
public interface BeverageOrdersDao extends JpaRepository<BeverageOrders, Long> {


}
