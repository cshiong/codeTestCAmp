package test.codetest;

import com.codetest.Question;
import com.codetest.QuestionType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by cboyd002c on 5/19/16.
 */
public class QuestionTest {

    Question q;
    Map<String,String>  expectedFields;

    @Before
    public void setUp() throws Exception {
        //expected
        expectedFields = new HashMap<String,String>();
        expectedFields.put("type","ratingquestion");
        expectedFields.put("text","do you like you job");
        expectedFields.put("theme","work");

        List<String> headers = Arrays.asList("type","text","theme");
        List<String> data = Arrays.asList("ratingquestion","do you like you job","work");
        q = new Question(headers,data);
    }

    @Test
    public void createQuestion1(){
        Assert.assertEquals(expectedFields, q.getFields());
    }

    @Test
    public void createQuestion2(){
        Question q2 = new Question(expectedFields, QuestionType.R);

        Assert.assertEquals(expectedFields, q2.getFields());
    }


    @Test
    public void getType() throws Exception {
          Assert.assertEquals(QuestionType.R,q.getType());
    }

    @Test
    public void setType() throws Exception {
        q.setType(QuestionType.S);
        Assert.assertEquals(QuestionType.S,q.getType());

    }

}