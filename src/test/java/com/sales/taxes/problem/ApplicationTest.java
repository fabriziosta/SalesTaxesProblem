package com.sales.taxes.problem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sales.taxes.problem.model.Receipt;
import com.sales.taxes.problem.service.ReceiptService;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {

	@Test
	void main_OkNoArgs() throws IOException {
		String[] args = new String[] {};
		List<Receipt> receiptList = new ArrayList<>();
		
		Mockito.when(ReceiptService.readInput(Mockito.any())).thenReturn(receiptList);

		Application.main(args);
	}

	@Test
	void main_OkWithArgs() throws IOException {
		String[] args = new String[] {"./src/main/resources/inputFile"};
		List<Receipt> receiptList = new ArrayList<>();
		
		Mockito.when(ReceiptService.readInput(Mockito.any())).thenReturn(receiptList);
		
		Application.main(args);
	}
}
