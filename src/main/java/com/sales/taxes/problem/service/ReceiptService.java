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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiptService {
	
	public static List<Receipt> readInput() throws IOException{
		List<Receipt> receiptsList = new ArrayList<>();
		
		Path path = Paths.get("./src/main/resources/inputFile");
		
		if(Files.notExists(path, LinkOption.NOFOLLOW_LINKS))
			throw new ReadFileException("Input file does not exist!");
		
		Files.readAllLines(path)
			.stream()
			.forEach((String line) -> {
				Receipt receipt = new Receipt();
				
				if("".equals(line.trim())) {
					
					if(!receipt.getProductList().isEmpty())
						receiptsList.add(receipt);
					receipt = new Receipt();
				} else
					receipt.getProductList().add(createProduct(line));
		});
		return receiptsList;
	}
	
	public static Product createProduct(String input) {
		Product product = new Product();
		
		try {
			String quantity = input.split(" ")[0];
			product.setQuantity(Integer.valueOf(quantity));
			
			input = input.substring(quantity.length()).replace(" at",":");
			
			String description = input.split(":")[0];
			product.setDescription(description + ":");
			
			String price = input.split(":")[1].trim();
			product.setAmountWithoutTaxes(Double.valueOf(price));
			
			product.setImportDutyTaxApplicable(TaxesService.isImportDutyTaxApplicable(description));
			product.setBasicTaxApplicable(TaxesService.isBasicTaxApplicable(description));
		
		} catch (Exception e) {
			throw new CreateProductException("Creating product failed with input: '" + input + "'");
		}
		log.info("{}", product);
		return product;
	}

}
