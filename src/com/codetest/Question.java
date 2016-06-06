package com.codetest;

import java.util.*;

/**
 * Question Class defines the question used for survey.
 */
public class Question {


    private QuestionType type=QuestionType.R;
    //for easy extend and flexible order of columns with the header and value pair
    private Map<String,String>  fields;

    //default constructor
    public Question (){
        fields = new HashMap<String,String>();
    }


    /**
     *  Constructor takes a list of head and list of corresponding data.
     *  @param headers
     *  @param data
     */
    public Question(List<String> headers, List <String> data){

        fields = new HashMap<String, String>(data.size());
        for (int i=0 ; i < data.size(); i++){
            String key = headers.get(i);
            //need trim the left and tail spaces
            key = key.replace("^\\s+", "").trim();
            String value = data.get(i);
            value = value.replace("^\\s+","").trim();

            if ( key.equalsIgnoreCase("type") && value.equalsIgnoreCase("singleselect")){
                type = QuestionType.S;
            }
            fields.put(key,value);
        }
    }

    /**
     *  Constructor takes a fields map and type .
     *  @param fields
     *  @param type
     */
    public Question(Map<String,String> fields, QuestionType type){
        this.fields = fields;
        this.type = type;
    }


    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    /**
     * Get all the fields of the question
     */
    public Map<String, String> getFields() {
        return this.fields;
    }

    /**
     * Set all the fields of the question
     */
    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }


}
