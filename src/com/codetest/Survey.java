package com.codetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Survey class define all the questions for this survey.
 */
public class Survey implements FileParser{

    Logger logger = Logger.getLogger(Survey.class.getName());

    private List<Question> questions;

    //name of the survey to identify different survey.
    private String name;

    public Survey(String name) {
        this.name = name;
        questions = new ArrayList<Question>();
    }

    public Survey(List<Question> questions, String name) {
        this.questions = questions;
        this.name = name;
    }


    public Survey(String name, String questionFile) throws FileNotFoundException {
        this.name = name;

        File suveyFile = new File (questionFile);
        parserFile(suveyFile);

    }

    /**
     * parse survey question file and set the survey, file need has the headers without fixed order
     * @param file result file name
     * @
     */
    @Override
    public void parserFile(File file) throws FileNotFoundException{
        logger.log(Level.INFO, "parseFile()");
        Scanner scanner = new Scanner(file);
        //use it for logging error, know which line has issue.
        int lineCount =0;
        if (scanner.hasNextLine()) {

            String[] headers = scanner.nextLine().split("\\s*,\\s*");
            //get actual questions
            while (scanner.hasNextLine()){
                //the data maybe have special char, even , inside one column, need to consider.

                String line= scanner.nextLine();
                lineCount++;

                final String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (data.length != headers.length){ //something wrong if that happens and ignore this line and print the error.
                    logger.warning("something wrong with line:" + lineCount + ", the column not march the headers,this question will be ignored!");
                    continue;
                }

                Question q = new Question(Arrays.asList(headers),Arrays.asList(data));
                if (questions == null ){
                    questions = new ArrayList<Question>();
                }
                questions.add(q);
            }

        scanner.close();
        }

    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
