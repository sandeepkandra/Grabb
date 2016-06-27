package com.kitchengrabbngo.gson;

import java.util.ArrayList;

/**
 * Created by sandeep on 15-Feb-16.
 */
public class OrderParent {
   // public int Merchant_ID;

    public OrdersChild Orders;

    public class OrdersChild
    {

        public ArrayList<OrderArrayDetails> OrderArray=new ArrayList<>();
    }

    public  class  OrderArrayDetails
    {

      // public ProductsCls Products;

        public String Order_Status;
        public String Status_id;
        public  String sequence;
        public int Order_ID;
        public int consumerid;

        public int Token;
        public  String user_profile_image;

        public ArrayList<ProductsDetails> Products=new ArrayList<>();
        public transient String eta;
        public transient boolean colorIsGreen=false;
        public transient  boolean checkShowTxtBox=false;
        public  int Order_ETA;
    }


   /* public class ProductsCls
    {
        ArrayList<ProductsDetails> Products=new ArrayList<>();


    }*/
    public  class ProductsDetails
    {
       // public CustomisationCls Customization;

        public String Product_ID;
        public  String Product_Name;
        public int Quantity;
        public  String Customization_Required;
        public ArrayList<CustomisationCls> Customization=new ArrayList<>();


    }
    public  class CustomisationCls
    {
        public String category;
        public  String category_value;

    }
}
