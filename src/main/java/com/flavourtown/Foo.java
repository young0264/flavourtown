package com.flavourtown;

import lombok.Getter;

@Getter
public class Foo extends Person {
    private Integer number;
    private String name;

    @Override
    public String toString() {
        return "Foo{" +
                "number=" + number +
                ", name='" + name + '\'' +
                '}';
    }

    static {
        System.out.println("hello");
    }

    private void printHi() {
        System.out.println("hi");
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        Integer number1 = foo.getNumber();
//        String s = foo.getNumber().toString();
        String s1 = foo.toString();
        System.out.println(s1);
    }



}
