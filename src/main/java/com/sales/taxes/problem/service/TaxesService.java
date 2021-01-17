package com.sales.taxes.problem.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sales.taxes.problem.model.Product;
import com.sales.taxes.problem.model.Receipt;

public class TaxesService {
	private static final Logger LOGGER = Logger.getLogger( TaxesService.class.getName() );
	private TaxesService() {}
	private static final float BASIC_TAX = 0.1F;
	private static final float IMPORT_DUTY_TAX = 0.05F;

	public static boolean isBasicTaxApplicable(String description) throws IOException {
		boolean isTaxExempt = false;
		
		Path folder = Paths.get("./src/main/resources/keywords");
		
		try(Stream<Path> streamPath = Files.list(folder)){
			List<Path> keywordsFiles = streamPath.collect(Collectors.toList());

			for(Path fileWithKeywords : keywordsFiles){
				Optional<Boolean> found = Files
					.readAllLines(fileWithKeywords)
					.parallelStream()
					.filter(z -> description.toLowerCase().contains(z.toLowerCase()))
					.map(x -> true)
					.findAny();
				
				if(found.isPresent()) {
					isTaxExempt = true;
					break;
				}
			}
		}
		return !isTaxExempt;
	}

	public static boolean isImportDutyTaxApplicable(String description) {
		return description.contains("imported");
	}
	
	public static void calculateTotalReceiptTaxesAndAmount(Receipt receipt) {
		receipt.getProductList().parallelStream().forEach(TaxesService::calculateTotalProductAmount);
		
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

		product.setTotalTaxes(roundUpToNearest005(totalTaxes));
		product.setTotalAmount(roundUpToNearest005((product.getAmountWithoutTaxes() * product.getQuantity()) + totalTaxes));
	}
	
	public static Double roundUpToNearest005(double d) {
		double result = d;

		if(!String.format("%.2f", d).endsWith("9") && !String.format("%.2f", d).endsWith("0"))
			result = Math.ceil(d /  0.05) *  0.05;
		
		LOGGER.log(Level.FINE, "Price: {0} - Rounded: {1}", new Object[] {d, result});
		return result;
	}
}
