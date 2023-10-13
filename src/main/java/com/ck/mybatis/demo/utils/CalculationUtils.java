package com.ck.mybatis.demo.utils;

public class CalculationUtils {

    public static String calculateRatioAndFormat(Integer numerator, Integer denominator) {
        if(denominator == null || denominator == 0 || numerator == null) {
            throw new IllegalArgumentException("分子或分母不能为空或零.");
        }
        double ratio = (double) numerator / denominator;
        double percentage = ratio * 100;
        return String.format("%d/%d = %.2f%%", numerator, denominator, percentage);
    }
}
