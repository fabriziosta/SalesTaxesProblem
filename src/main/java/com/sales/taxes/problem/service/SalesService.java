package com.sales.taxes.problem.service;

import java.util.List;

import com.sales.taxes.problem.model.Product;
import com.sales.taxes.problem.model.Receipt;

public class SalesService {
	private SalesService() {}
	
	public static void writeOutput(List<Receipt> receiptsList) {
		for(Receipt receipt : receiptsList) {
			System.out.println("Receipt n. " + (receiptsList.indexOf(receipt) + 1));
			
			for(Product product : receipt.getProductList())
				System.out.println(product.getQuantity() + product.getDescription() + " " + String. format("%.2f", product.getTotalAmount()));
			
			System.out.println("Sales Taxes: " + String. format("%.2f", receipt.getTotalTaxes()));
			System.out.println("Total: " + String. format("%.2f", receipt.getTotalReceiptAmount()) + "\n");
		}
	}
}
