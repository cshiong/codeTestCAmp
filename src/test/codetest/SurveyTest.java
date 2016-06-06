package test.codetest;

import com.codetest.Question;
import com.codetest.Survey;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cboyd002c on 5/19/16.
 */
public class SurveyTest {


    String fileName="myTestSurvey.csv";
    File file;



    @Before
    public void setUp() throws Exception {
        //dynamicaly create the test file
        file = new File(fileName);
        file.createNewFile();

    }

    @Test
    public void parserFile1() throws Exception {

        List<String> lines = Arrays.asList("type,text,theme","ratingquestion,do you like you job,work");

        //create a question file;
        Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);

        Survey survey = new Survey("myTestSurvey");;

        survey.parserFile(file);

        List<String> headers = Arrays.asList("type","text","theme");
        List<String> data = Arrays.asList("ratingquestion","do you like you job","work");
        Question q = new Question(headers,data);

        //covered getQuestions()
        Assert.assertEquals(q.getFields(),survey.getQuestions().get(0).getFields());



    }

    @Test
    public void parserFile2() throws Exception {

        List<String> lines = Arrays.asList("type,text,theme","ratingquestion,\"In general, I have the resources (e.g., business tools, information, facilities, IT or functional support) I need to be effective.\", the work");

        //create a question file;
        Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);

        Survey survey = new Survey("myTestSurvey");

        survey.parserFile(file);

        List<String> headers = Arrays.asList("type","text","theme");
        List<String> data = Arrays.asList("ratingquestion","\"In general, I have the resources (e.g., business tools, information, facilities, IT or functional support) I need to be effective.\""," the work");
        Question q = new Question(headers,data);

        //covered getQuestions()
        boolean size = survey.getQuestions().size()>0 ? true:false;
        Assert.assertTrue(size);
        Assert.assertEquals(q.getFields(),survey.getQuestions().get(0).getFields());

    }

    @Test
    public void parserFile3() throws Exception {
        //test the spaces before or after , we should trim them
        List<String> lines = Arrays.asList("type,text,theme","  ratingquestion,\"In general, I have the resources (e.g., business tools, information, facilities, IT or functional support) I need to be effective.\", the work");

        //create a question file;
        Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);

        Survey survey = new Survey("myTestSurvey");

        survey.parserFile(file);

        List<String> headers = Arrays.asList("type","text","theme");
        List<String> data = Arrays.asList("ratingquestion","\"In general, I have the resources (e.g., business tools, information, facilities, IT or functional support) I need to be effective.\""," the work");
        Question q = new Question(headers,data);

        //covered getQuestions()
        boolean size = survey.getQuestions().size()>0 ? true:false;
        Assert.assertTrue(size);
        Assert.assertEquals(q.getFields(),survey.getQuestions().get(0).getFields());

    }



    @After
    public void tearDown() throws Exception {
        //delete the test survey file.
        file.delete();

    }
}