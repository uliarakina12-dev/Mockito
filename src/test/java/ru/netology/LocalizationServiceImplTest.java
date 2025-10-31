package ru.netology;

import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {

    private final LocalizationService localizationService = new LocalizationServiceImpl();

    @Test
    void locale_Russia_ReturnsRussianText() {
        String result = localizationService.locale(Country.RUSSIA);
        assertEquals("Добро пожаловать", result);
    }

    @Test
    void locale_USA_ReturnsEnglishText() {
        String result = localizationService.locale(Country.USA);
        assertEquals("Welcome", result);
    }
}
