package pl.lewandowski.weatherproject;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class WeatherController {



    private Integer timestamp;
    private int temperature;
    private String weatherIcon;
    private String city2;
    private String country2;
    private Date time;
    private int wind;
    private int pressure;
    private double lat;
    private double lon;


    @GetMapping("/")
    public String getForm(Model model){

        return "form2";
    }

    @PostMapping("/")
    private String getData(@RequestParam ("city") String city, @RequestParam("country") String country, String resp) {
        lon=0;
        lat=0;
        pressure = 0;
        wind = 0;
        time = null;
        city2=null;
        country2=null;
        temperature=0;
        weatherIcon=null;


        RestTemplate restTemplate = new RestTemplate();
        resp=null;
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city.toLowerCase() + "," + country.toLowerCase() + "&APPID=d50e2ced77c40b123bb179d8e5652e86";

        resp = restTemplate.getForObject(url, String.class);
//        System.out.println(resp);
        JsonElement parser = new JsonParser().parse(resp);
        timestamp = parser.getAsJsonObject().get("dt").getAsInt();
        time=new Date((long)timestamp*1000);
        temperature = parser.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsInt()-273;
        city2 = StringUtils.capitalize(city);
        country2 = StringUtils.capitalize(country);
        weatherIcon = parser.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
        wind = parser.getAsJsonObject().get("wind").getAsJsonObject().get("speed").getAsInt();
        pressure = parser.getAsJsonObject().get("main").getAsJsonObject().get("pressure").getAsInt();
        lon = parser.getAsJsonObject().get("coord").getAsJsonObject().get("lon").getAsDouble();
        lat = parser.getAsJsonObject().get("coord").getAsJsonObject().get("lat").getAsDouble();


        return "redirect:/weather";
    }

    @GetMapping("/weather")
    private ModelAndView weatherModel() {
        Map<String, Object> model = new LinkedHashMap<>();
        model.clear();
        model.put("lon",lon);
        model.put("lat",lat);
        model.put("pressure",pressure);
        model.put("wind",wind);
        model.put("time", time);
        model.put("city", city2);
        model.put("country", country2);
        model.put("temp", temperature);
        model.put("icon", weatherIcon);
        return new ModelAndView("weather", model);
    }


}
