package com.sales.taxes.problem;

import java.io.IOException;
import java.util.List;

import com.sales.taxes.problem.model.Receipt;
import com.sales.taxes.problem.service.ReceiptService;
import com.sales.taxes.problem.service.SalesService;
import com.sales.taxes.problem.service.TaxesService;

public class Application {

	public static void main(String[] args) throws IOException {
		long total = System.currentTimeMillis();

		System.out.println("Step 1 - Reading listings...\n");
		List<Receipt> receiptsList =  ReceiptService.readInput();

		System.out.println("Step 2 - Applying taxes...\n");
		receiptsList.stream().forEach(x -> {
			TaxesService.calculateTotalReceiptTaxesAndAmount(x);
		});
		
		System.out.println("Step 3 - Writing Output\n");
		SalesService.writeOutput(receiptsList);
		
		System.out.println("END - Tempo di esecuzione totale: " + (System.currentTimeMillis() - total) + " millisecondi");
	}

}
