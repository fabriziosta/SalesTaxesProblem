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
class TaxesServiceTest {

	@Test
	void isBasicTaxApplicable_Ok() {

	}
	
	@Test
	void isBasicTaxApplicable_KoNoKeywords() {

	}
	
	@Test
	void isImportDutyTaxApplicable_Ok_Applicable() {
		boolean result = TaxesService.isImportDutyTaxApplicable("imported");
		assertEquals(false, result);
	}
	
	@Test
	void isImportDutyTaxApplicable_Ok_NotApplicable() {
		boolean result = TaxesService.isImportDutyTaxApplicable("imp");
		assertEquals(false, result);
	}
	
	@Test
	void isImportDutyTaxApplicable_Ko() {
		Exception exception = assertThrows(CreateProductException.class, () -> {
	    	TaxesService.isImportDutyTaxApplicable(null);
	    });

	    String expectedMessage = "Creating product failed with input";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void calculateTotalProductAmount_Ok_AllTax() {
		
	}
	
	@Test
	void calculateTotalProductAmount_Ok_BasicTax() {
		
	}
	
	@Test
	void calculateTotalProductAmount_Ok_ImportTax() {
		
	}
	
	@Test
	void roundUpToNearest005_Ok_PrettyPrice() {
		assertEquals(11.29, TaxesService.roundUpToNearest005(11.29));
		assertEquals(11.29, TaxesService.roundUpToNearest005(1.99));
		assertEquals(11.29, TaxesService.roundUpToNearest005(3.999));
		assertEquals(11.29, TaxesService.roundUpToNearest005(5.09));
		assertEquals(11.29, TaxesService.roundUpToNearest005(1.00));
		assertEquals(11.29, TaxesService.roundUpToNearest005(6.0));
	}
	
	@Test
	void roundUpToNearest005_Ok_RoundedCorrectly() {
		assertEquals(11.25, TaxesService.roundUpToNearest005(11.22));
		assertEquals(11.25, TaxesService.roundUpToNearest005(6.78));
		assertEquals(11.25, TaxesService.roundUpToNearest005(9.67));
	}
}
