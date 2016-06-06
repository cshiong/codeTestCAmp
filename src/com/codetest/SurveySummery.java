package com.codetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SuerverSummery class define the survey results based on the survey and response
 */
public class SurveySummery implements FileParser {

    Logger logger = Logger.getLogger(SurveySummery.class.getName());

    private Survey survey;
    private int totalLines;
    private int participate;
    //question index as the key and the total rating as the value to use to calculate average rating.
    private Map<Integer,Integer> ratingTotals;


    /**
    *  Constructor the surverSummery for this survey
     *  @param survey
     */

    public SurveySummery(Survey survey) {
        this.survey = survey;
    }


    /**
    *  initialization the Rating Questions result total map.which will be used for calculate the average rate.
     *  Set all the keys which is the rating question index,this key will be equal the result column's index-3
     */
    private void initRatingTotals() throws ExceptionInInitializerError{
        logger.log(Level.INFO,"initRatingTotals()");
        if( survey == null || survey.getQuestions().size()==0){
             String errMsg = "the survey is not defined yet!";
             logger.log(Level.SEVERE,errMsg);
             throw new ExceptionInInitializerError(errMsg);
        }

        if (ratingTotals == null) {
            ratingTotals = new HashMap<Integer,Integer>(survey.getQuestions().size());
        }
        for (int i=0; i< survey.getQuestions().size(); i++) {
            if (survey.getQuestions().get(i).getType() == QuestionType.R) {
                ratingTotals.put(i, 0);
            }
        }
    }
    /**
    * parse result file and set the summery, the result file columns have to be in the fixed order:
     * email,id,timestamp,question1,question2...
     * @param file result file
     * @
     */
    @Override
    public void parserFile(File file) throws FileNotFoundException {
        logger.log(Level.INFO, "parseFile()");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line =scanner.nextLine();
            //using comma as deliminator and using regex to escape it if it is inside ""
            String[] result =line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            totalLines++;

            if ( result.length > 3 && !result[2].equalsIgnoreCase("")){ //timestamp field not empty means not participated.
                participate++;
                if (ratingTotals == null || ratingTotals.size()==0){
                    initRatingTotals();
                }
                // need to identify if it is a rating question column
                for (int i= 3; i< result.length; i++){

                    int questionIndex = i-3;

                    if( ratingTotals.keySet().contains(questionIndex)//rating question
                            && (result[i]!=null ||!result[i].equalsIgnoreCase(""))){

                        int rate = Integer.parseInt(result[i].trim()) + ratingTotals.get(questionIndex);

                        ratingTotals.put(questionIndex,rate);
                    }
                }
            }
        }
        scanner.close();
    }

    /**
    * Get the survey participated percentage.
     * @return  string represents the percentage of participation.
     */
    public String getParticipateRate(){

        float percentage = 100 * participate/totalLines;
        return String.format("%.0f%%",percentage);

    }

    public int getParticipate() {
        return participate;
    }

    /**
     * Get the survey rating questions average rating.
     * @return  String.
     */

    public Map<Integer,Integer> getAverageRatings(){

        Map<Integer,Integer> aveRating = new HashMap<Integer,Integer>(ratingTotals.size());
        for ( Map.Entry<Integer,Integer> entry : ratingTotals.entrySet()){
            aveRating.put( entry.getKey(),entry.getValue()/participate);
    }
        return aveRating;
    }

    /**
    *  Return the actual survey questions etc for this Survey Summery
     *  @return Survey
     */
    public Survey getSurvey(){
        return survey;
    }


    /**
    * Get the survey report based on the requirement.
     * @return  string  (can be defined as a json)
     */
    public String report(){
        StringBuilder output = new StringBuilder();
        output.append("TotalParticipate: "+ participate + ", ParticipatePercentage:"
                + getParticipateRate() );
         if (ratingTotals==null || ratingTotals.size()<=0)
             return output.toString();

        for (Map.Entry<Integer,Integer> entry : getAverageRatings().entrySet()){
            output.append(", question " + (Integer.valueOf(entry.getKey())+1) + ": averageRating:" + entry.getValue());
        }
        return output.toString();
    }
}
