package stepdefs;


import com.jayway.restassured.response.Response;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helper.RestAssuredHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

public class StepDefinitions {

    private static final Logger LOGGER = Logger.getLogger(StepDefinitions.class.getName());
    private RestAssuredHelper restAssuredHelper = new RestAssuredHelper();
    private Response response;
    public String day;
    public String city;




    @Given("^I like to holiday in \"([^\"]*)\"$")
    public void cityToHoliday(String cityHoliday) throws Throwable {

        if (cityHoliday.toLowerCase().equals("Sydney")) {
            city = "2147714";
        }
    }

    @And("^I only like to holiday on \"([^\"]*)\"$")
    public void iOnlyLikeToHolidayOn(String dayToHoliday) throws Throwable {
        day = dayToHoliday;
    }
    

    @And("^I want to know if temperature is warmer than '(\\d+)' degrees$")
    public void theTemperatureIsWarmerThanDegrees2(int degrees) throws Throwable {
        boolean flag = true;
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=Sydney&appid=080c9c75cfac14027313dcda02dab756&units=metric";
        Response response = restAssuredHelper.getRequest(url);
        List<String> dateResponse = response.jsonPath().getList("list.dt_txt");
        List<Float> tempResponse = response.jsonPath().getList("list.main.temp");
        List<String> weatherResponse = response.jsonPath().getList("list.weather.main");
        //Collecting date and time information
        ArrayList<String> daysAbove20 = new ArrayList<String>();
        ArrayList<String> daysSunny = new ArrayList<String>();
        ArrayList<String> datessabove20 = new ArrayList<>();

        for (int  next =0; next <= dateResponse.size() - 1;next+= 3) {
            String date = dateResponse.get(next).substring(0, 10);
            String time = dateResponse.get(next).substring(11, 19);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();
            for(int i= 0 ; i < 3 ; i++) {
               float tem = new Float(String.valueOf(tempResponse.get(next+i)));
                if((tem > 20))
                {
                    datessabove20.add(dateResponse.get(next));
                    daysAbove20.add(dayOfWeek.toString());

                }
                if(weatherResponse.equals("sunny")) {
                    daysSunny.add(dayOfWeek.toString());
                }

            }



//            Iterator<Float> myListIterator = tempResponse.iterator();
//            // Logic to find if the temparature is less than the expected temp
//            if((tempResponse.get(next)) > 20) {
//                daysAbove20.add(dayOfWeek.toString());
//            }


        }


        System.out.println("There are " + daysAbove20.size() + "days that are above 20 and below are the Days");
        daysAbove20.forEach(days->System.out.println(days));

        System.out.println("There are " + daysSunny.size() + "days that are sunny and below are the Days");
        daysSunny.forEach(days->System.out.println(days));


    }

}