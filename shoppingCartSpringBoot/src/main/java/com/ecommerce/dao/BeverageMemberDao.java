package com.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.BeverageMember;
@Repository
public interface BeverageMemberDao extends JpaRepository<BeverageMember, Long> {
	
	// Enhanced JPQL Syntax
	@Query("SELECT b FROM BeverageMember b WHERE b.identificationNo = ?1")
	List<BeverageMember> queryByMemberByID(String identificationNo);
}
