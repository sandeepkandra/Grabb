package com.kitchengrabbngo.sqlitedb;

import android.content.Context;

public class Printer {
	
	private Context context;


	//save the context recievied via constructor in a local variable

	public Printer(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public String orderid,check;

	public String printerResValues;
	public String printerValues;
	public String getPrinterResValues()
	{
		return printerResValues;
	}
	public String getPrinterValues()
	{
		return printerValues;
	}
	public String getOrderId()
	{
		return orderid;
	}
	public String getCheck()
	{
		return check;
	}



}
