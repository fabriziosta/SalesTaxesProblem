package com.sales.taxes.problem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sales.taxes.problem.model.Receipt;
import com.sales.taxes.problem.service.ReceiptService;
import com.sales.taxes.problem.service.SalesService;
import com.sales.taxes.problem.service.TaxesService;

public class Application {
	private static final Logger LOGGER = Logger.getLogger( Application.class.getName() );
	private static final String OUTPUT_FILE = "./Receipt.txt";

	public static void main(String[] args) throws IOException {
		long total = System.currentTimeMillis();
		
		LOGGER.log(Level.FINE, "Step 0 - Finding Input file...\n");
		String inputFile = retrieveInputFile(args);

		LOGGER.log(Level.FINE, "Step 1 - Reading listings...\n");
		List<Receipt> receiptsList =  ReceiptService.readInput(inputFile);

		LOGGER.log(Level.FINE, "Step 2 - Applying taxes...\n");
		receiptsList.stream().forEach(TaxesService::calculateTotalReceiptTaxesAndAmount);
		
		LOGGER.log(Level.FINE, "Step 3 - Writing Output\n");
		SalesService.writeOutputAsLogs(receiptsList);
		SalesService.writeOutputAsFile(receiptsList, OUTPUT_FILE);
		
		LOGGER.log(Level.FINE, "END - Total execution time: {0} ms", (System.currentTimeMillis() - total));
	}

	private static String retrieveInputFile(String[] args) {
		String inputFile;
		if(args.length > 0)
			inputFile = args[0];
		else {
			Scanner scanner = new Scanner(System.in);

			while(true) {
				System.out.println("Give me a valid inputFile path:");
				inputFile = scanner.nextLine();
				System.out.println(inputFile);
				if(inputFile != null && !"".equals(inputFile) && Files.exists(Paths.get(inputFile), LinkOption.NOFOLLOW_LINKS))
					break;
			}
	        scanner.close();
		}
		return inputFile;
	}
}
