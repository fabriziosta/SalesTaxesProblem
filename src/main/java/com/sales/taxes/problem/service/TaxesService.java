package com.sales.taxes.problem.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sales.taxes.problem.exception.ReadFileException;
import com.sales.taxes.problem.model.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaxesService {
	private static final float BASIC_TAX = 0.1F;
	private static final float IMPORT_DUTY_TAX = 0.05F;

	public static boolean isBasicTaxApplicable(String description) throws IOException {
		boolean result = false;

		Path foodKeywords = Paths.get("./src/main/resources/taxExemptionFoodKeywords");
		Path genericKeywords = Paths.get("./src/main/resources/taxExemptionGenericKeywords");

		if (Files.notExists(foodKeywords, LinkOption.NOFOLLOW_LINKS)
				|| Files.notExists(genericKeywords, LinkOption.NOFOLLOW_LINKS))
			throw new ReadFileException("Keywords not found!");

		result = Files.readAllLines(genericKeywords).parallelStream().filter(x -> description.contains(x))
				.map(x -> true).findFirst().get();

		if (result == false)
			result = Files.readAllLines(foodKeywords).parallelStream().filter(x -> description.contains(x))
					.map(x -> true).findFirst().get();

		return result;
	}

	public static boolean isImportDutyTaxApplicable(String description) {
		return description.contains("imported");
	}

	public static void calculateTotalProductAmount(Product product) {
		double totalTaxes = 0;

		if (product.isBasicTaxApplicable() && product.isImportDutyTaxApplicable())
			totalTaxes = product.getAmountWithoutTaxes() * (BASIC_TAX + IMPORT_DUTY_TAX);
		else if(product.isImportDutyTaxApplicable())
			totalTaxes = product.getAmountWithoutTaxes() * IMPORT_DUTY_TAX;
		else if (product.isBasicTaxApplicable())
			totalTaxes = product.getAmountWithoutTaxes() * BASIC_TAX;

		log.info("Taxes for {} are {} - ({} rounded)", product, totalTaxes, roundUp(totalTaxes));
		product.setTotalTaxes(totalTaxes);
		product.setTotalAmount(product.getAmountWithoutTaxes() + totalTaxes);
		log.info("Total amount is {} ({} rounded)", product.getTotalAmount(), roundUp(product.getTotalAmount()));
	}
	
	public static Double roundUp(double d) {
		return Math.round(d * 20) / 20.0;
	}
}
