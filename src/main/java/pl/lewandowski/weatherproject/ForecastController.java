package pl.lewandowski.weatherproject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
public class ForecastController {

    private Set<Long> timeset = new TreeSet<>();
    private ArrayList<Integer> tempset = new ArrayList<>();
    private JsonArray arr;
    private String city2;


    @PostMapping("/forecast")
    private ModelAndView forecastData(@RequestParam("city") String city, @RequestParam("code") String code, String resp)  {
        timeset.removeAll(timeset);
        tempset.removeAll(tempset);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city.toLowerCase() +","+code.toLowerCase()+"&APPID="+ConfigKey.API_KEY;

        resp = restTemplate.getForObject(url, String.class);

        JsonElement parser = new JsonParser().parse(resp);
        arr = parser.getAsJsonObject().get("list").getAsJsonArray();
        city2= StringUtils.capitalize(city);

        for (int i = 0; i < arr.size(); i++) {
            timeset.add(arr.get(i).getAsJsonObject().get("dt").getAsLong()*1000);
            tempset.add(arr.get(i).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsInt()-273);
        }
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("city",city2);
        model.put("dates",timeset);
        model.put("temp",tempset);



        return new ModelAndView("forecast", model);
    }




}
