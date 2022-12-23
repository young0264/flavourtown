package com.flavourtown;


class Exam {
    private int count;

    //Exam의 생성자
    public Exam(int count) {
        this.count = count;
    }

    //count에 대한 get 과 set 메서드
    public int getCount() {
        return count;
    }

    public void setCount(int num) {
        this.count = num;
    }
}

public class Main {

    //** process메서드 **//
    public static void process1(Exam exam) {
        System.out.println("print1 : " + exam.getCount());
        exam.setCount(5);   //이때의 exam은 exam count=3 의 heap영역을 가르킴
        System.out.println("print2 : " + exam.getCount());
        Exam exam2 = new Exam(7);   //이때의 exam2는 새로운스택, 새로운 heap 영역을 가르킴
        exam = exam2;
        System.out.println("print3 : " + exam.getCount());   //새로 만들어진 stack, heap 영역
        exam.setCount(9);
        System.out.println("print4 : " + exam.getCount());   //새로 만들어진 stack, heap 영역

    }

    //** 메인 메서드 **//
    public static void main(String[] args) {
        Exam exam = new Exam(3);
        process1(exam);
        System.out.println("print5 : " + exam.getCount());    //기존의 stack, heap 영역

        //===============================================================//

        int value = 11;
        process2(value);
        System.out.println("print2-1 : "+ value);
    }

    public static void process2(int num) {
        num = 17;
        System.out.println("print2-2 : " + num);
    }
}
