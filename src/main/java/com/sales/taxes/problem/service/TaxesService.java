package com.sales.taxes.problem.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sales.taxes.problem.exception.ReadFileException;
import com.sales.taxes.problem.model.Product;
import com.sales.taxes.problem.model.Receipt;

public class TaxesService {
	private static final float BASIC_TAX = 0.1F;
	private static final float IMPORT_DUTY_TAX = 0.05F;

	public static boolean isBasicTaxApplicable(String description) throws IOException {
		boolean isTaxExempt = false;
		
		Path folder = Paths.get("./src/main/resources/keywords");
		
		List<Path> keywordsFiles = Files.list(folder).collect(Collectors.toList());

		if (keywordsFiles.isEmpty())
			throw new ReadFileException("Keywords files not found!");

		for(Path fileWithKeywords : keywordsFiles){
			Optional<Boolean> found = Files.readAllLines(fileWithKeywords, StandardCharsets.UTF_8)
				.parallelStream()
				.filter(x -> description.contains(x))
				.map(x -> true)
				.findAny();
			
			if(found.isPresent()) {
				isTaxExempt = true;
				break;
			}
		}
		
		return !isTaxExempt;
	}

	public static boolean isImportDutyTaxApplicable(String description) {
		return description.contains("imported");
	}
	
	public static void calculateTotalReceiptTaxesAndAmount(Receipt receipt) {
		receipt.getProductList().parallelStream().forEach(x -> {
			calculateTotalProductAmount(x);
		});
		
		receipt.getProductList().stream().forEach(x -> {
			receipt.setTotalTaxes(receipt.getTotalTaxes() + x.getTotalTaxes());
			receipt.setTotalReceiptAmount(receipt.getTotalReceiptAmount() + x.getTotalAmount());
		});
	}

	public static void calculateTotalProductAmount(Product product) {
		double totalTaxes = 0;

		if (product.isBasicTaxApplicable() && product.isImportDutyTaxApplicable())
			totalTaxes = product.getAmountWithoutTaxes() * (BASIC_TAX + IMPORT_DUTY_TAX);
		else if(product.isImportDutyTaxApplicable())
			totalTaxes = product.getAmountWithoutTaxes() * IMPORT_DUTY_TAX;
		else if (product.isBasicTaxApplicable())
			totalTaxes = product.getAmountWithoutTaxes() * BASIC_TAX;

		System.out.println("Taxes for "+product+" are "+totalTaxes+" - ("+roundUp(totalTaxes)+" rounded)");
		product.setTotalTaxes(totalTaxes);
		product.setTotalAmount(product.getAmountWithoutTaxes() + totalTaxes);
		System.out.println("Total amount is "+product.getTotalAmount()+" ("+roundUp(product.getTotalAmount())+" rounded)");
	}
	
	public static Double roundUp(double d) {
		return Math.round(d * 20) / 20.0;
	}
}
