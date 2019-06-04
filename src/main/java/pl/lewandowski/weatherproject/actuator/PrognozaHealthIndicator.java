package pl.lewandowski.weatherproject.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Controller
public class PrognozaHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=gdynia,pl&APPID=d50e2ced77c40b123bb179d8e5652e86";
        String url2 = "api.openweathermap.org";
        try {
            ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);

            if (resp.hasBody()) {
                return Health.up().withDetail("endpoint /prognoza up with getting data from api: ", url2).
                        build();
            } else {
                return Health.down().withDetail("ping_url", url).withDetail("ping_time", new Date()).build();
            }
        } catch (RestClientException e) {
            return Health.down(e).withDetail("ping_url", url).withDetail("ping_time", new Date()).build();
        }
    }
    }

