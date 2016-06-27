package com.kitchengrabbngo.models;

import java.util.List;

public class Tokens {


    String token_id;

int eta;


    public String get_token_id() {
        return token_id;
    }

    public int get_eta() {
        return eta;
    }

    public Tokens(String tokenid, int eta) {
        super();
        this.token_id = tokenid;
this.eta =eta;


    }


}
