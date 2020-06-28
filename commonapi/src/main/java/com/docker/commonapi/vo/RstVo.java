package com.docker.commonapi.vo;

import java.io.Serializable;

public class RstVo implements Serializable {


    /**
     * num : 50
     */

    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public String img;


//    "balance": "0.00",
//            "point": "0",
//            "inviteNum": "0"

    public String balance;
    public String point;
    public String inviteNum;
}
