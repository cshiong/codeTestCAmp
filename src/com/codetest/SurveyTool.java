package com.codetest;

import java.io.File;
import java.io.FileNotFoundException;

public class SurveyTool {

    /*
    *  Take two string args, the first one is the file name of the survey question,
    *  the second is the survey result file name
    *  and print out the survey result summery: total participated, percentage of participate,
    *  and the rating questions average rating result.
     */
    public static void main(String[] args) {

	   if (args.length < 2) {
           System.out.println("Need to provide both survey and survey result file names!");
           return;
       }
        String surveyFileName = args[0];
        String resultFileName = args[1];

        try {
            Survey s = new Survey("mySurvey",surveyFileName);
            SurveySummery summery = new SurveySummery(s);
            File resultFile = new File (resultFileName);
            summery.parserFile(resultFile);
            System.out.println(summery.report());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
