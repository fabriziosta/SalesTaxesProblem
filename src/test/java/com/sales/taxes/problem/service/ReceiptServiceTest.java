package com.sales.taxes.problem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sales.taxes.problem.exception.CreateProductException;
import com.sales.taxes.problem.model.Product;

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
}
