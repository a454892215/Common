package com.test.java;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Test {

    public static void main(String[] args) {


    }

    public static String getTwoDecimalNum(float accuracyValue) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(accuracyValue);
    }
}

