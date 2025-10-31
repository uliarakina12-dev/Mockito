package ru.netology;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class MessageSenderImplTest {

    @Mock
    private GeoService geoService;

    @Mock
    private LocalizationService localizationService;

    private MessageSender messageSender;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageSender = new MessageSenderImpl(geoService, localizationService);
    }

    @Test
    void send_RussianIp_SendsRussianMessage() {
        // Arrange
        Mockito.when(geoService.byIp("172.123.12.19"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");

        // Act
        String result = messageSender.send(headers);

        // Assert
        assertEquals("Добро пожаловать", result);
    }

    @Test
    void send_AmericanIp_SendsEnglishMessage() {
        // Arrange
        Mockito.when(geoService.byIp("96.44.33.22"))
                .thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.33.22");

        // Act
        String result = messageSender.send(headers);

        // Assert
        assertEquals("Welcome", result);
    }

    @Test
    void send_OtherIp_SendsEnglishMessage() {
        // Arrange
        Mockito.when(geoService.byIp("192.168.1.1"))
                .thenReturn(new Location("Berlin", Country.GERMANY, null, 0));
        Mockito.when(localizationService.locale(Country.GERMANY))
                .thenReturn("Welcome"); // По условию — английский для всех, кроме России

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "192.168.1.1");

        String result = messageSender.send(headers);

        assertEquals("Welcome", result);
    }

    @Test
    void send_NoIpHeader_SendsEnglishByDefault() {
        Mockito.when(geoService.byIp(anyString()))
                .thenReturn(new Location("London", Country.USA, null, 0));
        Mockito.when(localizationService.locale(any()))
                .thenReturn("Welcome");

        Map<String, String> headers = new HashMap<>();

        String result = messageSender.send(headers);

        assertEquals("Welcome", result);
    }
}
