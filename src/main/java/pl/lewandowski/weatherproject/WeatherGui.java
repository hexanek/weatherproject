package pl.lewandowski.weatherproject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Route
public class WeatherGui extends VerticalLayout {

    private Controller controller;



    private TextField miasto = new TextField("Podaj miasto");
    private TextField panstwo  = new TextField("Podaj państwo");
    private TextArea opis = new TextArea("Opis");
    private Button button = new Button("Sprawdź");


    @Autowired
    public WeatherGui(Controller controller) {


        add(miasto,panstwo, button, opis);



        button.addClickListener(ClickEvent -> getMap());

    }

    public void getMap() {
        String city = miasto.getValue().toLowerCase();
        String country = panstwo.getValue().toLowerCase();
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +"," + country + "&APPID=d50e2ced77c40b123bb179d8e5652e86";

        String resp = restTemplate.getForObject(url, String.class);

        JsonElement jsonElement = new JsonParser().parse(resp);
        //String lat = jsonElement.getAsJsonObject().get("coord").getAsJsonObject().get("lat").getAsString();
        int weather = jsonElement.getAsJsonObject().get("clouds").getAsJsonObject().get("all").getAsInt();
        int temp = jsonElement.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsInt() - 273;

        opis.setValue("Temperature in "+ city + " " + temp + " °C and cloudiness " +weather + "%");




    }

}
