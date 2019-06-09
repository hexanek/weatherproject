package pl.lewandowski.weatherproject;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lewandowski.weatherproject.CoordSaver.Coords;
import pl.lewandowski.weatherproject.CoordSaver.CoordsRepo;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WeatherControllerTest {

    @Mock
    Coords coords;

    @Mock
    CoordsRepo coordsRepo;

    @Mock
    Weather weather;

    @Test
    public void newCoordsAdd(){
        List<Coords> list = new ArrayList<>();

        Coords coords = new Coords(40.21,54.20,"gdynia");
        Coords coords2 = new Coords(40.21,54.20,"gdansk");
        Coords coords3 = new Coords(40.21,54.20,"sopot");

        list.add(coords);
        list.add(coords2);
        list.add(coords3);

        Assert.assertThat(list, Matchers.hasSize(3));

    }

    @Test
    public void newWeatherAdd(){
        List<Weather> list = new ArrayList<>();


        Weather weather = new Weather("gdynia","poland",10,1050,54.25,54.35,20);
        Weather weather2 = new Weather("gdansk","poland",10,1050,54.25,54.35,20);
        Weather weather3 = new Weather("sopot","poland",10,1050,54.25,54.35,20);

        list.add(weather);
        list.add(weather2);
        list.add(weather3);
        Assert.assertThat(list,Matchers.hasSize(3));
    }



}