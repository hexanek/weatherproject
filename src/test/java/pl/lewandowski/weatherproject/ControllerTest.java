package pl.lewandowski.weatherproject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void getWeather() {

        RestTemplate restTemplate = new RestTemplate();


        final String baseUrl = "https://api.openweathermap.org/data/2.5/weather?q=gdynia,poland&APPID=" + ConfigKey.API_KEY;


        ResponseEntity<String> result = restTemplate.getForEntity(baseUrl, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true,result.hasBody());
        Assert.assertEquals(true, result.getBody().contains("temp"));
        Assert.assertEquals(true, result.getBody().contains("54.52"));
        Assert.assertEquals(true, result.getBody().contains("18.54"));
    }

    @Test
    public void getForecast() {

        RestTemplate restTemplate = new RestTemplate();


        final String baseUrl = "https://api.openweathermap.org/data/2.5/forecast?q=gdynia,pl&APPID=" + ConfigKey.API_KEY;


        ResponseEntity<String> result = restTemplate.getForEntity(baseUrl, String.class);

        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true,result.hasBody());
        Assert.assertEquals(true, result.getBody().contains("Gdynia"));
    }


}