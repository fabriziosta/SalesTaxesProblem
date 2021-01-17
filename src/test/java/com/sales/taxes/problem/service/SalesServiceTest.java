package com.sales.taxes.problem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sales.taxes.problem.model.Product;

@ExtendWith(MockitoExtension.class)
class SalesServiceTest {

	@Test
	void createProductFromListings_Ok() {
		String input = "1 book at 12.49";
		Product result = ReceiptService.createProductFromListings(input);
		assertEquals(1, result.getQuantity());
	}
}
