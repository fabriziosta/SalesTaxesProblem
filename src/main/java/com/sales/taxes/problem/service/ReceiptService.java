package com.sales.taxes.problem.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.sales.taxes.problem.exception.CreateProductException;
import com.sales.taxes.problem.exception.ReadFileException;
import com.sales.taxes.problem.model.Product;
import com.sales.taxes.problem.model.Receipt;

public class ReceiptService {
	private ReceiptService() {}
	
	public static List<Receipt> readInput(String source) throws IOException {
		List<Receipt> receiptsList = new ArrayList<>();
		
		Path path = Paths.get(source);
		
		if(Files.notExists(path, LinkOption.NOFOLLOW_LINKS))
			throw new ReadFileException("Input file does not exist!");
		
		List<String> allLines = Files.readAllLines(path);
		
		Receipt receipt = new Receipt();
		for(String line : allLines){
			
			if(!"".equals(line.trim()))
				receipt.getProductList().add(createProductFromListings(line));
			else {
				receiptsList.add(receipt);
				receipt = new Receipt();
			}
		}
		receiptsList.add(receipt);
		return receiptsList;
	}
	
	public static Product createProductFromListings(String input) {
		Product product = new Product();
		
		try {
			String trimmedInput = input;
			String quantity = trimmedInput.split(" ")[0];
			product.setQuantity(Integer.valueOf(quantity));
			
			trimmedInput = trimmedInput.substring(quantity.length()).replace(" at",":");
			
			String description = trimmedInput.split(":")[0];
			product.setDescription(description + ":");
			
			String price = trimmedInput.split(":")[1].trim();
			product.setAmountWithoutTaxes(Double.valueOf(price));
			
			product.setImportDutyTaxApplicable(TaxesService.isImportDutyTaxApplicable(description));
			product.setBasicTaxApplicable(TaxesService.isBasicTaxApplicable(description));
		} catch (Exception e) {
			throw new CreateProductException("Creating product failed with input: '" + input + "'");
		}
		return product;
	}

}
