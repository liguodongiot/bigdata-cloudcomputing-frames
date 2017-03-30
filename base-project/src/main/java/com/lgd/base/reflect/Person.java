package com.lgd.base.reflect;

import java.io.Serializable;

/**
 * Created by liguodong on 2017/3/30.
 */

public class Person implements Serializable,ExtInfoInterface,BaseInterface {

    private Long id;
    public String name;

    public Person() {
        this.id = 100L;
        this.name = "afsdfasd";
    }

    public Person(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(Long id) {
        super();
        this.id = id;
    }

    @SuppressWarnings("unused")
    private Person(String name) {
        super();
        this.name = name+"=======";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Person [id=" + id + ", name=" + name + "]";
    }
    private String getSomeThing() {
        return "sdsadasdsasd";
    }

    private void privateMethod(){
        System.out.println("this is a private method");
    }
}

