package com.lgd.base.guava;

import com.google.common.base.Throwables;

import java.io.IOException;

/**
 * <p>Project: bigdata-frames</p>
 * <p>Package: com.lgd.base.guava</p>
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author guodong.li
 * @version 1.0.0
 * @date 2018/6/20
 */


public class ThrowableDemo {

    public static void main(String args[]){
        ThrowableDemo tester = new ThrowableDemo();
        try {
            tester.showcaseThrowables();
        } catch (InvalidInputException e) {
            //get the root cause
            System.out.println(Throwables.getRootCause(e));
        }catch (Exception e) {
            //get the stack trace in string format
            System.out.println(Throwables.getStackTraceAsString(e));
        }

        try {
            tester.showcaseThrowables2();
        }catch (Exception e) {
            System.out.println(Throwables.getStackTraceAsString(e));
        }

        System.out.println("over");
    }

    public void showcaseThrowables() throws InvalidInputException{
        try {
            sqrt(-3.0);
        } catch (Throwable e) {
            //check the type of exception and throw it
            Throwables.throwIfInstanceOf(e, InvalidInputException.class);
            Throwables.throwIfUnchecked(e);
        }
    }

    public void showcaseThrowables2() throws IndexOutOfBoundsException{
        try {
            int[] data = {1,2,3};
            getValue(data, 4);
        } catch (Throwable e) {
            Throwables.propagateIfInstanceOf(e, IndexOutOfBoundsException.class);
            Throwables.propagate(e);
        }
    }

    public double sqrt(double input) throws InvalidInputException{
        if(input < 0) throw new InvalidInputException();
        return Math.sqrt(input);
    }

    public double getValue(int[] list, int index) throws IndexOutOfBoundsException {
        return list[index];
    }

    public void dummyIO() throws IOException {
        throw new IOException();
    }
}

class InvalidInputException extends Exception {
}