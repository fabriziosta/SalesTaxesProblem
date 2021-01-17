package com.sales.taxes.problem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sales.taxes.problem.exception.CreateProductException;
import com.sales.taxes.problem.model.Product;
import com.sales.taxes.problem.model.Receipt;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {
	
	@Test
	void createProductFromListings_Ok() {
		String input = "1 book at 12.49";
		Product result = ReceiptService.createProductFromListings(input);
		assertEquals(1, result.getQuantity());
		assertEquals(" book:", result.getDescription());
		assertEquals(12.49, result.getAmountWithoutTaxes());
		assertEquals(false, result.isImportDutyTaxApplicable());
		assertEquals(false, result.isBasicTaxApplicable());
	}
	
	@Test
	void createProductFromListings_KoNoQuantity() {
		String input = "book at 12.49";
		
	    Exception exception = assertThrows(CreateProductException.class, () -> {
	    	ReceiptService.createProductFromListings(input);
	    });

	    String expectedMessage = "Creating product failed with input";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void createProductFromListings_KoNoPrice() {
		String input = "1 book at ";
		
	    Exception exception = assertThrows(CreateProductException.class, () -> {
	    	ReceiptService.createProductFromListings(input);
	    });

	    String expectedMessage = "Creating product failed with input";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void readInput_Ok() throws IOException, URISyntaxException {
		List<String> allLines = new ArrayList<>();
		allLines.add("1 imported bottle of perfume at 27.99");
		allLines.add("");
		allLines.add("1 imported bottle of perfume at 27.99");
		
		List<Receipt> receiptsList = ReceiptService.readInput("./src/test/resources/inputFileTest");
		assertEquals(3, receiptsList.size());
		
	}
}
