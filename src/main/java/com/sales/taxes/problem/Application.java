package com.sales.taxes.problem;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sales.taxes.problem.model.Receipt;
import com.sales.taxes.problem.service.ReceiptService;
import com.sales.taxes.problem.service.SalesService;
import com.sales.taxes.problem.service.TaxesService;

public class Application {
	private static final Logger LOGGER = Logger.getLogger( Application.class.getName() );
	private static final String INPUT_FILE = "./src/main/resources/inputFile";
	private static final String OUTPUT_FILE = "./Receipt.txt";

	public static void main(String[] args) throws IOException {
		long total = System.currentTimeMillis();
		
		String inputFile;
		if(args.length > 0)
			inputFile = args[0];
		else
			inputFile = INPUT_FILE;

		LOGGER.log(Level.FINE, "Step 1 - Reading listings...\n");
		List<Receipt> receiptsList =  ReceiptService.readInput(inputFile);

		LOGGER.log(Level.FINE, "Step 2 - Applying taxes...\n");
		receiptsList.stream().forEach(TaxesService::calculateTotalReceiptTaxesAndAmount);
		
		LOGGER.log(Level.FINE, "Step 3 - Writing Output\n");
		SalesService.writeOutputAsLogs(receiptsList);
		SalesService.writeOutputAsFile(receiptsList, OUTPUT_FILE);
		
		LOGGER.log(Level.FINE, "END - Total execution time: {0} ms", (System.currentTimeMillis() - total));
	}
}
