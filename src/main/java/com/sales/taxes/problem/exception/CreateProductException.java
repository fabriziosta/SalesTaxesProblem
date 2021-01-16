package com.sales.taxes.problem.exception;

public class CreateProductException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CreateProductException() {
        super();
    }

    public CreateProductException(String message) {
        super(message);
    }

}
