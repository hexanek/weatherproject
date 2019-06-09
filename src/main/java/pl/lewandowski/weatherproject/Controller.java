package pl.lewandowski.weatherproject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class Controller {



    @GetMapping("/pogoda/{country}/{city}")
    public @ResponseBody Object getWeather(@PathVariable String country,
                                           @PathVariable String city){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<Object> response = restTemplate.getForEntity("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country +
                        "&APPID=" + ConfigKey.API_KEY,
                Object.class);
        return response;
    }

    @GetMapping("/prognoza/{city}/{code}")
    public @ResponseBody Object getForecast(@PathVariable String city,
                                            @PathVariable String code){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<Object> response = restTemplate.getForEntity("https://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + code +
                        "&APPID=" + ConfigKey.API_KEY,
                Object.class);
        return response;
    }



    @GetMapping("/home")
    public String weather(Model model){
        Map<String, Object> modelik = new LinkedHashMap<>();
        model.addAttribute("weather", getWeather("poland","gdynia"));
        return "weather2";
    }
}
