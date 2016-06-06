# Survey Tool

A CLI application to parse and display survey data from CSV files, and display the results. 
the details requirement can be find in doc/readme.md file 

## How to run

This tool is written in java language, you need to set up the [java runtime environment](http://www.tutorialspoint.com/java/java_environment_setup.htm)

The jar file is under out/artifacts/codeTestCAmp.jar

Run command:

`java -jar codeTestCAmp.jar {yourSurveyFileName} {yourSurveyResponseFileName}
`

Example: if you run from out/artifacts/ directory

`
java -jar codeTestCAmp.jar ../../testData/survey-1.csv ../../testData/survey-1-responses.csv
`

Required arguments: file names of the survey question and survey response.


## Classes
 
### Question: 
  Key/value map to hold the fields to define the question, which can be extended to more properties.

### Survey: 
  With name to identify the survey and a list of questions. paraseFile() to parse the question file and set the question list.

### SurveySummery: 
  Contains the survey and the survey response result: participate rate, participate count, and average rate for rate question.
there is a private map with the rating question index as the key and total rate value as value used to calculate the average rate.
SurverySummery paraseFile() method parse the response file which can be called multiple times to add more responses to it.

Note: There may be spaces before and after in each column in the file, we should trim these spaces when we parse the file. 

## Problems:
1. The response csv doesn't have header which will may cause some issue, since user can be easily mess up the columns order.
   improve: add the header on the response file, and modify the code using the header as key on the map and also find the response value.


## Enhancement

1. Result can be defined as an object and print out as json format.

2. Could log more information.e.g each method and error etc.

3. Using the errorMessage.properties for all error messages and in the code to use them instead of hard coding.

4. Can be easily change the main() to take command like : create surveyName surveyFileName, addResponse surveyName responseFileName