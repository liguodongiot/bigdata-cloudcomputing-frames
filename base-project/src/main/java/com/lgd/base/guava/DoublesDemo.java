package com.lgd.base.guava;

import java.util.List;
import com.google.common.primitives.Doubles;

public class DoublesDemo {

    public static void main(String args[]){
        DoublesDemo tester = new DoublesDemo();
        tester.testDoubles();
    }

    private void testDoubles(){
        double[] doubleArray = {1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0};

        //convert array of primitives to array of objects
        List<Double> objectArray = Doubles.asList(doubleArray);
        System.out.println(objectArray.toString());

        //convert array of objects to array of primitives
        doubleArray = Doubles.toArray(objectArray);
        System.out.print("[ ");
        for(int i = 0; i< doubleArray.length ; i++){
            System.out.print(doubleArray[i] + " ");
        }
        System.out.println("]");
        //check if element is present in the list of primitives or not
        System.out.println("5.0 is in list? "+ Doubles.contains(doubleArray, 5.0f));

        //return the index of element
        System.out.println("5.0 position in list "+ Doubles.indexOf(doubleArray, 5.0f));

        //Returns the minimum
        System.out.println("Min: " + Doubles.min(doubleArray));

        //Returns the maximum
        System.out.println("Max: " + Doubles.max(doubleArray));
    }
}