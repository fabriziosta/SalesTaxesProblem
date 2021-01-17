package com.sales.taxes.problem;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sales.taxes.problem.service.ReceiptService;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {

	@Test
	void main_OkNoArgs() throws IOException {
		Mockito.when(ReceiptService.readInput(Mockito.any())).thenReturn(new ArrayList<>());
		Application.main(new String[] {});
	}

	@Test
	void main_OkWithArgs() throws IOException {
		Mockito.when(ReceiptService.readInput(Mockito.any())).thenReturn(new ArrayList<>());
		Application.main(new String[] {"./src/main/resources/inputFile"});
	}
	
	
}
