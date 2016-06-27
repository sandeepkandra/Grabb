package com.kitchengrabbngo.sqlitedb;

import android.content.Context;

public class Token {

	private Context context;


	//save the context recievied via constructor in a local variable

	public Token(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public String orderid,tokenid,eta;

	public String getOrderId()
	{
		return orderid;
	}
	public String getEta()
	{
		return eta;
	}
	public String get_token_id()
	{
		return tokenid;
	}

	public Token(String tokenid, String orderid,String eta) {
		super();
		this.tokenid = tokenid;
		this.eta =eta;
		this.orderid = orderid;


	}

}
