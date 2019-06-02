package pl.lewandowski.weatherproject.actuator;

import com.google.gson.JsonElement;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Component
public class PogodaHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=gdynia,poland&APPID=d50e2ced77c40b123bb179d8e5652e86";
        String url2 = "api.openweathermap.org";
        try {
            String resp = restTemplate.getForObject(url, String.class);
            //System.out.println("-------------->" + resp);
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map<String, Object> map = springParser.parseMap(resp);


            if ("Gdynia".equalsIgnoreCase((String) map.get("name"))) {
                return Health.up().withDetail("endpoint /pogoda up with getting data from api: ", url2).
                        build();
            } else {
                return Health.down().withDetail("ping_url", url).withDetail("ping_time", new Date()).build();
            }
        } catch (RestClientException e) {
            return Health.down(e).withDetail("ping_url", url).withDetail("ping_time", new Date()).build();
        }
    }
}
