package pl.lewandowski.weatherproject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    @GetMapping("/pogoda/{country}/{city}")
    public @ResponseBody Object getWeather(@PathVariable String country,
                                           @PathVariable String city){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<Object> response = restTemplate.getForEntity("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country +
                        "&APPID=80730ceb55c51bebae397cfc46e03344",
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
}
