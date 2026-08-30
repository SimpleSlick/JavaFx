package com.gradecalc;

public class Subject{
    private String name;
    private double marks;

    public Subject(String name, double marks){
        this.name = name;
        this.marks = marks;
    }

    public String getName(){
        return name;
    }

    public double getMarks(){
        return marks;
    }
}