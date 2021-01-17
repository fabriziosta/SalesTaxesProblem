package com.sales.taxes.problem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sales.taxes.problem.model.Product;

@ExtendWith(MockitoExtension.class)
class TaxesServiceTest {

	@Test
	void isBasicTaxApplicable_Ok() throws IOException {
		assertEquals(false, TaxesService.isBasicTaxApplicable("jam"));
		assertEquals(true, TaxesService.isBasicTaxApplicable("perfume"));
		assertEquals(true, TaxesService.isBasicTaxApplicable("jadore"));
		assertEquals(true, TaxesService.isBasicTaxApplicable("toys"));
		assertEquals(true, TaxesService.isBasicTaxApplicable("drug"));
		assertEquals(false, TaxesService.isBasicTaxApplicable("pizza"));
		assertEquals(true, TaxesService.isBasicTaxApplicable("playstation 5"));
	}
	
	@Test
	void isImportDutyTaxApplicable_Ok_Applicable() {
		boolean result = TaxesService.isImportDutyTaxApplicable("imported wine from Germany");
		assertEquals(true, result);
	}
	
	@Test
	void isImportDutyTaxApplicable_Ok_NotApplicable() {
		boolean result = TaxesService.isImportDutyTaxApplicable("imp");
		assertEquals(false, result);
	}
	
	@Test
	void isImportDutyTaxApplicable_Ko() {
		Exception exception = assertThrows(NullPointerException.class, () -> {
	    	TaxesService.isImportDutyTaxApplicable(null);
	    });

	    String expectedMessage = "Cannot invoke \"String.contains(java.lang.CharSequence)\" because \"description\" is null";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void calculateTotalProductAmount_Ok_AllTax() {
		Product product = new Product();
		product.setImportDutyTaxApplicable(true);
		product.setBasicTaxApplicable(true);
		product.setQuantity(1);
		product.setAmountWithoutTaxes(6.35);
		TaxesService.calculateTotalProductAmount(product);
		assertEquals("1,00", String.format("%.2f", product.getTotalTaxes())); //0.9525
		assertEquals("7,30", String.format("%.2f", product.getTotalAmount())); //7.30
	}
	
	@Test
	void calculateTotalProductAmount_Ok_BasicTax() {
		Product product = new Product();
		product.setImportDutyTaxApplicable(false);
		product.setBasicTaxApplicable(true);
		product.setQuantity(1);
		product.setAmountWithoutTaxes(3.58);
		TaxesService.calculateTotalProductAmount(product);
		assertEquals("0,40", String.format("%.2f", product.getTotalTaxes())); //0.358
		assertEquals("3,95", String.format("%.2f", product.getTotalAmount())); //3.95
	}
	
	@Test
	void calculateTotalProductAmount_Ok_ImportTax() {
		Product product = new Product();
		product.setImportDutyTaxApplicable(true);
		product.setBasicTaxApplicable(false);
		product.setQuantity(1);
		product.setAmountWithoutTaxes(1.58);
		TaxesService.calculateTotalProductAmount(product);
		assertEquals("0,10", String.format("%.2f", product.getTotalTaxes())); //0.079
		assertEquals("1,70", String.format("%.2f", product.getTotalAmount())); //1.659
	}
	
	@Test
	void calculateTotalProductAmount_Ok_NoTax() {
		Product product = new Product();
		product.setImportDutyTaxApplicable(false);
		product.setBasicTaxApplicable(false);
		product.setQuantity(1);
		product.setAmountWithoutTaxes(26.99);
		TaxesService.calculateTotalProductAmount(product);
		assertEquals(0, product.getTotalTaxes()); //0
		assertEquals(26.99, product.getTotalAmount()); //26.99
	}
	
	@Test
	void roundUpToNearest005_Ok_PrettyPrice() {
		assertEquals(11.29, TaxesService.roundUpToNearest005(11.29));
		assertEquals(1.99, TaxesService.roundUpToNearest005(1.99));
		assertEquals(3.99, TaxesService.roundUpToNearest005(3.99));
		assertEquals(5.09, TaxesService.roundUpToNearest005(5.09));
		assertEquals(1.00, TaxesService.roundUpToNearest005(1.00));
		assertEquals(6.00, TaxesService.roundUpToNearest005(6.0));
	}
	
	@Test
	void roundUpToNearest005_Ok_RoundedCorrectly() {
		assertEquals(11.25, TaxesService.roundUpToNearest005(11.22));
		assertEquals("6,80", String.format("%.2f", TaxesService.roundUpToNearest005(6.78)));
		assertEquals("9,70", String.format("%.2f", TaxesService.roundUpToNearest005(9.67)));
	}
}
