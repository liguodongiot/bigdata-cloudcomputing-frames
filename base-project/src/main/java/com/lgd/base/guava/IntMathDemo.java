package com.lgd.base.guava;

import java.math.RoundingMode;
import com.google.common.math.IntMath;

public class IntMathDemo {

    public static void main(String args[]){
        IntMathDemo tester = new IntMathDemo();
        tester.testIntMath();
    }

    private void testIntMath(){
        try{
            System.out.println(IntMath.checkedAdd(Integer.MAX_VALUE, Integer.MAX_VALUE));
        }catch(ArithmeticException e){
            System.out.println("Error: " + e.getMessage());
        }
        // 必须是整数
        System.out.println(IntMath.divide(100, 5, RoundingMode.UNNECESSARY));
        try{
            //exception will be thrown as 100 is not completely divisible by 3 thus rounding
            // is required, and RoundingMode is set as UNNESSARY
            System.out.println(IntMath.divide(100, 3, RoundingMode.UNNECESSARY));
        }catch(ArithmeticException e){
            System.out.println("Error: " + e.getMessage());
        }

        //四舍五入
        System.out.println("Log2(2): "+IntMath.log2(2, RoundingMode.HALF_EVEN));

        System.out.println("Log10(10): "+IntMath.log10(10, RoundingMode.HALF_EVEN));

        System.out.println("sqrt(100): "+IntMath.sqrt(IntMath.pow(10,2), RoundingMode.HALF_EVEN));

        System.out.println("gcd(100,50): "+IntMath.gcd(100,50));

        System.out.println("modulus(100,50): "+IntMath.mod(100,50));

        System.out.println("factorial(6): "+IntMath.factorial(6));
    }
}