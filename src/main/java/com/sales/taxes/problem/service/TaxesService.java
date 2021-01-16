package com.sales.taxes.problem.service;

import com.sales.taxes.problem.model.Product;

public class TaxesService {
	private static final float BASIC_TAX = 0.1F;
	private static final float IMPORT_DUTY_TAX = 0.05F;

	public static boolean isBasicTaxApplicable(String description) {
		boolean result = false;
		
		
		
		return result;
	}

	public static boolean isImportDutyTaxApplicable(String description) {
		return description.contains("imported");
	}
	
	public static Double calculateTotalPrice(Product product) {
		Double result = null;
		
		return result;
	}
}
