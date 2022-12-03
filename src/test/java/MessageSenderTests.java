import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;
import java.util.HashMap;
import java.util.Map;

public class MessageSenderTests {

    //MessageSender msgSender = Mockito.mock(MessageSenderImpl.class);
    LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
    GeoService geoService = Mockito.mock(GeoServiceImpl.class);

    @Test
    void ruMsgSend() {
        MessageSender msgSender = new MessageSenderImpl(geoService, localizationService);
        Map <String, String> header = new HashMap<>();
        header.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.MOSCOW_IP);

        Mockito.when(geoService.byIp(GeoServiceImpl.MOSCOW_IP)).thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 8));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать!");

        Assertions.assertEquals("Добро пожаловать!", msgSender.send(header));

    }

    @Test
    void engMsgSend() {
        MessageSender msgSender = new MessageSenderImpl(geoService, localizationService);
        Map <String, String> header = new HashMap<>();
        header.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.NEW_YORK_IP);

        Mockito.when(geoService.byIp(GeoServiceImpl.NEW_YORK_IP)).thenReturn(new Location("NY", Country.USA, "7th Avenu", 50));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        Assertions.assertEquals("Welcome", msgSender.send(header));

    }
    @Test
    void geoLocationByIP() {
        //тестируем метод byIp по ру-IP
        GeoServiceImpl geoServiceTest = new GeoServiceImpl();
        String ipTest = GeoServiceImpl.MOSCOW_IP;
        geoServiceTest.byIp(ipTest);

        Assertions.assertEquals("172.0.32.11", ipTest);

    }
    @Test
    void testRespondedMsg() {
        //тестируем метод locale, возвращающий eng-текст
        LocalizationService localServTest = new LocalizationServiceImpl();
        Assertions.assertEquals("Welcome", localServTest.locale(Country.USA));
    }
}
