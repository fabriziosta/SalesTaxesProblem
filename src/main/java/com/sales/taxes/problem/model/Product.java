package com.sales.taxes.problem.model;

import lombok.Data;

@Data
public class Product {
	private String description;
	private int quantity;
	private double amountWithoutTaxes;
	private double totalAmount;
	private boolean isBasicTaxApplicable;
	private boolean isImportDutyTaxApplicable;
}