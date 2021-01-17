package com.sales.taxes.problem.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sales.taxes.problem.model.Product;
import com.sales.taxes.problem.model.Receipt;

@ExtendWith(MockitoExtension.class)
class SalesServiceTest {

	@Test
	void writeOutputAsLogs_Ok() {
		SalesService.writeOutputAsLogs(buildReceiptList());
	}
	
	@Test
	void writeOutputAsFile_Ok() throws IOException {
		String pathFile = "./mock";
		Path path = Paths.get(pathFile);
		SalesService.writeOutputAsFile(buildReceiptList(), pathFile);
		
		boolean result = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
		
		if(result)
			Files.delete(path);
		
		assertTrue(result);
	}
	
	private List<Receipt> buildReceiptList() {
		List<Receipt> receiptsList = new ArrayList<>();
		Receipt receipt = new Receipt();
		Product product = new Product();
		
		product.setQuantity(1);
		product.setDescription("book");
		product.setBasicTaxApplicable(false);
		product.setImportDutyTaxApplicable(false);
		product.setAmountWithoutTaxes(7.99);
		receipt.getProductList().add(0, product);
		receiptsList.add(receipt);
		return receiptsList;
	}
}
