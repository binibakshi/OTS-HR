package com.example.biniProject.Exeption;

public class DataNotFoundExeption extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1123723695619132206L;

	public DataNotFoundExeption() {
		super("No Data Found");
	}

	public DataNotFoundExeption(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public DataNotFoundExeption(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DataNotFoundExeption(String arg0) {
		super("no data found for " + arg0);
		// TODO Auto-generated constructor stub
	}

	public DataNotFoundExeption(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	
	
	
}
