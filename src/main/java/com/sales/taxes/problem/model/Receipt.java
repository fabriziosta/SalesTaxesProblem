package com.sales.taxes.problem.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Receipt {
	private List<Product> productList;
	private double totalReceiptAmount = 0;
	private double totalTaxes = 0;
	
	public List<Product> getProductList(){
		if(productList == null)
			this.productList = new ArrayList<>();
		return this.productList;
	}
}
