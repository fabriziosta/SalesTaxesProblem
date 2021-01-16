package com.sales.taxes.problem.model;

import java.util.List;

import lombok.Data;

@Data
public class Receipt {

	private List<Product> productList;
	private double totalReceiptAmount;
	
}
