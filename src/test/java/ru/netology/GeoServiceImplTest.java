package ru.netology;


import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    private final GeoService geoService = new GeoServiceImpl();

    @Test
    void byIp_RussianIp_ReturnsRussia() {
        Location location = geoService.byIp("172.0.0.1");
        assertEquals(Country.RUSSIA, location.getCountry());
    }

    @Test
    void byIp_AmericanIp_ReturnsUSA() {
        Location location = geoService.byIp("96.0.0.1");
        assertEquals(Country.USA, location.getCountry());
    }
}
