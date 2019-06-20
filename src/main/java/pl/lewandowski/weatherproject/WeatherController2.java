package pl.lewandowski.weatherproject;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import pl.lewandowski.weatherproject.CoordSaver.Coords;
import pl.lewandowski.weatherproject.CoordSaver.CoordsRepo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class WeatherController2 {


    CoordsRepo coordsRepo;
    WeatherRepo weatherRepo;

    @Autowired
    public WeatherController2(CoordsRepo coordsRepo, WeatherRepo weatherRepo) {
        this.coordsRepo = coordsRepo;
        this.weatherRepo = weatherRepo;
    }

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

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city.toLowerCase() + "," + country.toLowerCase() + "&APPID=" + ConfigKey.API_KEY;

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
        coordSaver();



        return "redirect:/weather";
    }

    @GetMapping("/weather")
    private ModelAndView weatherModel() {
        Weather weather = new Weather(city2,country2,wind,pressure,lat,lon,temperature,time,weatherIcon);
        weatherRepo.save(weather);
        Map<String, Object> model = new LinkedHashMap<>();
        model.clear();
        model.put("lon",weather.getLon());
        model.put("lat",weather.getLat());
        model.put("pressure",weather.getPressure());
        model.put("wind",weather.getWind());
        model.put("time", weather.getTime());
        model.put("city", weather.getCity());
        model.put("country", weather.getCountry());
        model.put("temp", weather.getTemperature());
        model.put("icon", weather.getWeatherIcon());
        return new ModelAndView("weather", model);
    }

    private void coordSaver(){
        Coords coords = new Coords(lon,lat,city2);
        coordsRepo.save(coords);
    }




}
