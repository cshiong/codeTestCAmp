package com.codetest;

/**
*  QuestionType is a enum which define all the question types.
 */
public enum QuestionType {
    R("RATINGQUESTION"),
    S("SINGLESELECT");


    private final String name;

    private QuestionType(final String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return getName();
    }
}
