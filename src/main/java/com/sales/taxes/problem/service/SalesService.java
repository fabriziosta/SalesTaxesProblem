package com.sales.taxes.problem.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.sales.taxes.problem.model.Product;
import com.sales.taxes.problem.model.Receipt;

public class SalesService {
	private SalesService() {}
	
	public static void writeOutputAsLogs(List<Receipt> receiptsList) {
		generateResult(receiptsList).forEach(System.out::println);
	}

	public static void writeOutputAsFile(List<Receipt> receiptsList, String path) throws IOException {
		Path outputFile = Paths.get(path);

		if (Files.notExists(outputFile, LinkOption.NOFOLLOW_LINKS))
			Files.createFile(outputFile);

		Files.write(outputFile, generateResult(receiptsList), StandardOpenOption.WRITE);
	}
	
	private static List<String> generateResult(List<Receipt> receiptsList){
		List<String> result = new ArrayList<>();
		for(Receipt receipt : receiptsList) {
			result.add("Receipt n. " + (receiptsList.indexOf(receipt) + 1));
			
			for(Product product : receipt.getProductList())
				result.add(product.getQuantity() + product.getDescription() + " " + String. format("%.2f", product.getTotalAmount()));
			
			result.add("Sales Taxes: " + String. format("%.2f", receipt.getTotalTaxes()));
			result.add("Total: " + String. format("%.2f", receipt.getTotalReceiptAmount()) + "\n");
		}
		return result;
	}
}
