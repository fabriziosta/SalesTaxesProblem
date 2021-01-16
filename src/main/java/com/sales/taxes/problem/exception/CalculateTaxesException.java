package com.sales.taxes.problem.exception;

public class CalculateTaxesException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CalculateTaxesException() {
        super();
    }

    public CalculateTaxesException(String message) {
        super(message);
    }

}
