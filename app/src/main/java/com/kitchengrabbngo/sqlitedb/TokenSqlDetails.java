package com.kitchengrabbngo.sqlitedb;



import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TokenSqlDetails extends SQLiteOpenHelper{


	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "Tokendetails";

	public static final String TOKEN_ORDERID="orderid";
	public static final String TOKEN_TOKENID="tokenid";
	public static final String TOKEN_ETA="eta";
	public  String CREATE_TOKEN_TABLE;
	public static final String TOKEN_TABLE_NAME="tokenDetails";
	public SQLiteDatabase db;

	public TokenSqlDetails(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		CREATE_TOKEN_TABLE = "CREATE TABLE tokenDetails( " +
				"orderid VARCHAR(10000)," +
				"tokenid VARCHAR(100),"+
				"eta VARCHAR(100000)"+
								")";
		db.execSQL(CREATE_TOKEN_TABLE);
	//	Toast.makeText(getClass(), "created successfully", Toast.LENGTH_LONG).show();
		
		

	}
	
	
	//Insert into companyDetails
		public void addTokenData(Token edata){
			Log.d("INSERT pre DATA", edata.toString());
			// 1. get reference to writable DB
			//PrinterSqlDetails cData=new PrinterSqlDetails(null);
			 db = this.getWritableDatabase();


			ContentValues values = new ContentValues();
			values.put(TOKEN_ORDERID,edata.getOrderId());
			values.put(TOKEN_TOKENID, edata.get_token_id());
			values.put(TOKEN_ETA, edata.getEta());


			db.replace(TOKEN_TABLE_NAME, TOKEN_ETA, values);
			
			db.replace(TOKEN_TABLE_NAME, TOKEN_ORDERID, values);
			db.replace(TOKEN_TABLE_NAME, TOKEN_TOKENID, values);

			// 4. close
			db.close(); 
		}


	public void deletePreData(String da){
		//Log.d("INSERT pre DATA", edata.toString());
		// 1. get reference to writable DB
		//PrinterSqlDetails cData=new PrinterSqlDetails(null);
		db = this.getWritableDatabase();
		db.execSQL("DELETE FROM priDetails where checkorder='" + da + "'");
		//db.delete("preDetails","preVal =?",new String[]{da});

				// 4. close
				db.close();
	}
		
		/*public void updateEmployee(Printer edata) {
			Log.d("UPDATE EMP DATA", edata.toString());
		    db = this.getWritableDatabase();
		 
		    ContentValues values = new ContentValues();
		    values.put(FLAG, edata.getFlag());
		  //  values.put(KEY_PH_NO, contact.getPhoneNumber());
		 
		    // updating row
		  db.update(EMP_TABLE_NAME, values, EMPLOYEE_ID + "= ?",
		            new String[] { String.valueOf(edata.getEmpId()) });
		}
		
		public void updaterefotp(Printer edata) {
			Log.d("UPDATE EMP DATA", edata.toString());
		    db = this.getWritableDatabase();
		 
		    ContentValues values = new ContentValues();
		    values.put(PrinterSqlDetails.EMPLOYEE_REFNO, edata.getrefNo());
		    values.put(PrinterSqlDetails.EMPLOYEE_OTPNO, edata.getOtpNo());
		  //  values.put(KEY_PH_NO, contact.getPhoneNumber());
		 
		    // updating row
		  db.update(PrinterSqlDetails.EMP_TABLE_NAME, values, PrinterSqlDetails.EMPLOYEE_ID + "= ?",
		            new String[] { String.valueOf(edata.getEmpId()), });
		}
	*/
		
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS priDetails");
		
	}

}
