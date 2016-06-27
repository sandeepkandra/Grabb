package com.kitchengrabbngo.sqlitedb;



import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PrinterSqlDetails extends SQLiteOpenHelper{
	
	
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "Printerdetails";
	
	public static final String PRINTER_ORDERID="orderid";
	public static final String PRINTER_CHECK="checkorder";

	public  String CREATE_PRINTER_TABLE;
	public static final String PRI_TABLE_NAME="priDetails";
	public SQLiteDatabase db;

	public PrinterSqlDetails(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		CREATE_PRINTER_TABLE = "CREATE TABLE priDetails( " +
				"orderid VARCHAR(10000)," +
				"checkorder VARCHAR(100)"+
								")";
		db.execSQL(CREATE_PRINTER_TABLE);
	//	Toast.makeText(getClass(), "created successfully", Toast.LENGTH_LONG).show();
		
		

	}
	
	
	//Insert into companyDetails
		public void addPrinterData(Printer edata){
			Log.d("INSERT pre DATA", edata.toString());
			// 1. get reference to writable DB
			//PrinterSqlDetails cData=new PrinterSqlDetails(null);
			 db = this.getWritableDatabase();


			ContentValues values = new ContentValues();
			values.put(PRINTER_ORDERID,edata.getOrderId());
			values.put(PRINTER_CHECK, edata.getCheck());



			
			db.replace(PRI_TABLE_NAME, PRINTER_ORDERID, values);
			db.replace(PRI_TABLE_NAME, PRINTER_CHECK, values);

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
