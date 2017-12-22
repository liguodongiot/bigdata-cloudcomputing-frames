package com.lgd.base.java8;

public class Lambda2Demo {

    static String salutation = "Hello! ";
    public static void main(String args[]){
        GreetingService greetService1 = message -> {
            System.out.println(salutation + message);
            salutation = "ddd";
        };
        greetService1.sayMessage("Mahesh");
        System.out.println("salutation: "+salutation);
    }
    interface GreetingService {
        void sayMessage(String message);
    }

}
