package com.ecommerce.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = { "goodsID" })
//SQL
@NamedNativeQuery(
	name = "BeverageGoods.queryNamedNativeQueryByGoodsID",
	query = "SELECT * FROM BEVERAGE_GOODS S WHERE S.GOODS_ID = ?1",
	resultClass = BeverageGoods.class
)
@Entity
@Table(name = "BEVERAGE_GOODS", schema="LOCAL")
public class BeverageGoods {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "BEVERAGE_GOODS_SEQ_GEN")
	@SequenceGenerator(name = "BEVERAGE_GOODS_SEQ_GEN",sequenceName = "BEVERAGE_GOODS_SEQ",allocationSize = 1)
	@Column(name = "GOODS_ID")
	private Long goodsID;
	
	@Column(name = "GOODS_NAME")
	private String goodsName;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "PRICE")
	private int price;
	
	@Column(name = "QUANTITY")
	private int quantity;
	
	
	@Column(name = "IMAGE_NAME")
	private String imageName;

	@Column(name = "STATUS")
	private String status;
	
	// 雙向一對多關係(物件雙方各自都擁有對方的實體參照，雙方皆意識到對方的存在)
		// 避免聯集查詢被動觸發遞迴查尋的問題(java.lang.StackOverflowError)
		@JsonIgnore
		@OneToMany(
			fetch = FetchType.LAZY,
//			cascade = {CascadeType.MERGE, CascadeType.REMOVE},
			cascade = {CascadeType.ALL},
			orphanRemoval = true,
			mappedBy = "beverageGood"
		)
//	@JoinColumn(name = "orderID", insertable = false, updatable = false)
	private List<BeverageOrders> beverageOrders;
	
	
	
}
