package test.codetest;

import com.codetest.Survey;
import com.codetest.SurveySummery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by cboyd002c on 5/19/16.
 */
public class SurveySummeryTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    File testFile;


    @Before
    public void setUp() throws Exception {

         if (testFile != null){
             testFile.delete();
         }
    }

    /**
    * A valid test all should be return correctly.input data include , inside "".
     */
    @Test
    public void surveySummeryTest1() throws Exception {
      // using the sample test file
       File surveyQ = new File("testData/survey-1.csv");
       File respons = new File("testData/survey-1-responses.csv");

        Survey survey = new Survey("survery-1");
        survey.parserFile(surveyQ);

        SurveySummery summery = new SurveySummery(survey);
        summery.parserFile(respons);

        Assert.assertEquals(5,summery.getParticipate());
        Assert.assertEquals("83%",summery.getParticipateRate());
        Assert.assertEquals(4,summery.getAverageRatings().get(0).intValue()); //rating question 1
        Assert.assertEquals(5,summery.getAverageRatings().get(1).intValue());  //rating question 2
    }


    /**
     * A valid test all should be return correctly
     */
    @Test
    public void surveySummeryTest2() throws Exception {
        // using the sample test file
        File surveyQ = new File("testData/survey-2.csv");
        File respons = new File("testData/survey-2-responses.csv");

        Survey survey = new Survey("survery-2");
        survey.parserFile(surveyQ);

        SurveySummery summery = new SurveySummery(survey);
        summery.parserFile(respons);

        Assert.assertEquals(5,summery.getParticipate());
        Assert.assertEquals("100%",summery.getParticipateRate());
        Assert.assertEquals(4,summery.getAverageRatings().get(0).intValue()); //rating question 1
        Assert.assertEquals(5,summery.getAverageRatings().get(1).intValue());  //rating question 2
    }


    /**
    * test response file has spaces between each column.
     */
    @Test
    public void surveySummeryTest2_1() throws Exception {
        // using the sample test file
        File surveyQ = new File("testData/survey-2.csv");

        //test the spaces before or after , it should be not an issue
        List<String> lines = Arrays.asList(",5, 2014-07-31T11:35:41+00:00 ,  4   ,5,5,2,\"Mary,V\"");

        //create a response file;
        String fileName="testData/survey-2-response1.csv";
        testFile = new File(fileName);
        testFile.createNewFile();
        Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);


        Survey survey = new Survey("survery-2_1");
        survey.parserFile(surveyQ);

        SurveySummery summery = new SurveySummery(survey);
        summery.parserFile(testFile);

        Assert.assertEquals(1,summery.getParticipate());
        Assert.assertEquals("100%",summery.getParticipateRate());
        Assert.assertEquals(4,summery.getAverageRatings().get(0).intValue()); //rating question 1
        Assert.assertEquals(5,summery.getAverageRatings().get(1).intValue());  //rating question 2
    }


    /**
     * A valid test, the response file has none participated, expected error throws
     */
    @Test
    public void surveySummeryTest3() throws Exception {
        //different constructor
        Survey survey = new Survey("survery-3","testData/survey-3.csv");

        File respons = new File("testData/survey-3-responses.csv");

        SurveySummery summery = new SurveySummery(survey);

        summery.parserFile(respons);


    }

    /**
     * Test SurveySummery which hasn't set the survey,the response is valid
     */
    @Test
    public void surveySummeryTest4() throws Exception {

        exception.expect(ExceptionInInitializerError.class);
        exception.expectMessage(containsString("the survey is not defined yet!"));

        Survey survey = new Survey("emptySurvey");

        SurveySummery summery = new SurveySummery(survey);
        File response = new File("testData/survey-2-responses.csv");
        summery.parserFile(response);

        Assert.assertEquals(0,summery.getParticipate());
        Assert.assertEquals("0%",summery.getParticipateRate());

    }


}