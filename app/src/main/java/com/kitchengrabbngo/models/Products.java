package com.kitchengrabbngo.models;

public class Products {

    String store_products_id;
            int product_id;
    String product_name,stock_check,product_image;




    public String get_store_products_id() {
        return store_products_id;
    }
    public int get_product_id() {
        return product_id;
    }

    public String get_product_name() {
        return product_name;
    }
    public String get_stock_check() {
        return stock_check;
    }

    public void set_stock_check(String stock_check) {
         this.stock_check=stock_check;
    }


    public Products(int product_id, String product_name, String store_products_id, String stock_check, String product_image) {
        super();
        this.product_id = product_id;
        this.product_name = product_name;
        this.store_products_id = store_products_id;
        this.stock_check = stock_check;
        this.product_image = product_image;


    }
}
