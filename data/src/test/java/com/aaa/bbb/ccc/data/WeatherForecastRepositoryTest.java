package com.aaa.bbb.ccc.data;

import com.aaa.bbb.ccc.data.model.WeatherForecast;
import com.aaa.bbb.ccc.data.model.translateApi.TranslateResponse;
import com.aaa.bbb.ccc.data.network.OpenWeatherMapApi;
import com.aaa.bbb.ccc.data.network.TranslateApi;
import com.aaa.bbb.ccc.data.repository.impl.WeatherForecastRepository;
import com.aaa.bbb.ccc.data.repository.intrf.ICashRepository;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.ConnectException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.ArgumentMatchers.anyString;

public class WeatherForecastRepositoryTest {
    private MockWebServer serverWeather;
    private MockWebServer serverTranslate;
    private WeatherForecastRepository repository;
    private TestSubscriber<WeatherForecast> testSubscriber;
    private String cityName = "Makiyivka";
    private String translateCityName = "Макеевка";
    private String lat = "10";
    private String lon = "20";
    private String lang = "ru";
    private String metric = "metric";
    private OpenWeatherMapApi weatherApi;
    private TranslateApi translateApi;
    private String translateLang = "en-ru";
    private Gson gson;
    private ICashRepository cashRepository;
    private String responseOfTranslate = "{\"code\":200,\"lang\":\"" + translateLang + "\",\"text\":[\"" + translateCityName + "\"]}";

    private Dispatcher dispatcherWeather = new Dispatcher() {
        @Override
        public MockResponse dispatch(RecordedRequest request) {
            if (("/forecast/?type=like&appid=bbe1f99da2af5300d8384fb8d80a9bff&lat=" + lat + "&lon=" + lon + "&lang=" + lang + "&units=" + metric + "").equals(request.getPath())) {
                String wat = "{\"cod\":\"200\",\"message\":0,\"cnt\":40,\"list\":[{\"dt\":1578160800,\"main\":{\"temp\":275.29,\"feels_like\":270.36,\"temp_min\":275.29,\"temp_max\":275.29,\"pressure\":1013,\"sea_level\":1013,\"grnd_level\":990,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04n\"}],\"clouds\":{\"all\":77},\"wind\":{\"speed\":4.22,\"deg\":203},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-04 18:00:00\"},{\"dt\":1578171600,\"main\":{\"temp\":275.56,\"feels_like\":270.83,\"temp_min\":275.56,\"temp_max\":275.56,\"pressure\":1012,\"sea_level\":1012,\"grnd_level\":989,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":4.06,\"deg\":204},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-04 21:00:00\"},{\"dt\":1578182400,\"main\":{\"temp\":275.7,\"feels_like\":271.69,\"temp_min\":275.7,\"temp_max\":275.7,\"pressure\":1012,\"sea_level\":1012,\"grnd_level\":989,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.06,\"deg\":213},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-05 00:00:00\"},{\"dt\":1578193200,\"main\":{\"temp\":275.46,\"feels_like\":271.38,\"temp_min\":275.46,\"temp_max\":275.46,\"pressure\":1011,\"sea_level\":1011,\"grnd_level\":988,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.18,\"deg\":211},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-05 03:00:00\"},{\"dt\":1578204000,\"main\":{\"temp\":274.58,\"feels_like\":270.52,\"temp_min\":274.58,\"temp_max\":274.58,\"pressure\":1012,\"sea_level\":1012,\"grnd_level\":989,\"humidity\":95,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"небольшой снег\",\"icon\":\"13d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.12,\"deg\":240},\"snow\":{\"3h\":0.31},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-05 06:00:00\"},{\"dt\":1578214800,\"main\":{\"temp\":274.18,\"feels_like\":270.92,\"temp_min\":274.18,\"temp_max\":274.18,\"pressure\":1012,\"sea_level\":1012,\"grnd_level\":990,\"humidity\":95,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"небольшой снег\",\"icon\":\"13d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.89,\"deg\":232},\"snow\":{\"3h\":0.63},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-05 09:00:00\"},{\"dt\":1578225600,\"main\":{\"temp\":274.69,\"feels_like\":271.71,\"temp_min\":274.69,\"temp_max\":274.69,\"pressure\":1013,\"sea_level\":1013,\"grnd_level\":991,\"humidity\":95,\"temp_kf\":0},\"weather\":[{\"id\":600,\"main\":\"Snow\",\"description\":\"небольшой снег\",\"icon\":\"13d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.6,\"deg\":263},\"snow\":{\"3h\":0.19},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-05 12:00:00\"},{\"dt\":1578236400,\"main\":{\"temp\":274.86,\"feels_like\":272.52,\"temp_min\":274.86,\"temp_max\":274.86,\"pressure\":1015,\"sea_level\":1015,\"grnd_level\":992,\"humidity\":96,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":0.76,\"deg\":269},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-05 15:00:00\"},{\"dt\":1578247200,\"main\":{\"temp\":274.81,\"feels_like\":271.52,\"temp_min\":274.81,\"temp_max\":274.81,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":994,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.91,\"deg\":323},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-05 18:00:00\"},{\"dt\":1578258000,\"main\":{\"temp\":274.45,\"feels_like\":270.89,\"temp_min\":274.45,\"temp_max\":274.45,\"pressure\":1018,\"sea_level\":1018,\"grnd_level\":995,\"humidity\":94,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.35,\"deg\":5},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-05 21:00:00\"},{\"dt\":1578268800,\"main\":{\"temp\":273.75,\"feels_like\":270.11,\"temp_min\":273.75,\"temp_max\":273.75,\"pressure\":1020,\"sea_level\":1020,\"grnd_level\":997,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.16,\"deg\":9},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-06 00:00:00\"},{\"dt\":1578279600,\"main\":{\"temp\":273.74,\"feels_like\":269.13,\"temp_min\":273.74,\"temp_max\":273.74,\"pressure\":1020,\"sea_level\":1020,\"grnd_level\":997,\"humidity\":82,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.34,\"deg\":15},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-06 03:00:00\"},{\"dt\":1578290400,\"main\":{\"temp\":273.83,\"feels_like\":269.21,\"temp_min\":273.83,\"temp_max\":273.83,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":999,\"humidity\":82,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.37,\"deg\":13},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-06 06:00:00\"},{\"dt\":1578301200,\"main\":{\"temp\":274.05,\"feels_like\":269.37,\"temp_min\":274.05,\"temp_max\":274.05,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":999,\"humidity\":78,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.37,\"deg\":30},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-06 09:00:00\"},{\"dt\":1578312000,\"main\":{\"temp\":274.48,\"feels_like\":269.69,\"temp_min\":274.48,\"temp_max\":274.48,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1000,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.54,\"deg\":15},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-06 12:00:00\"},{\"dt\":1578322800,\"main\":{\"temp\":274.16,\"feels_like\":269.05,\"temp_min\":274.16,\"temp_max\":274.16,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":1000,\"humidity\":74,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.88,\"deg\":22},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-06 15:00:00\"},{\"dt\":1578333600,\"main\":{\"temp\":272.6,\"feels_like\":268.15,\"temp_min\":272.6,\"temp_max\":272.6,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":1001,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":2.94,\"deg\":30},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-06 18:00:00\"},{\"dt\":1578344400,\"main\":{\"temp\":271.97,\"feels_like\":267.67,\"temp_min\":271.97,\"temp_max\":271.97,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":1001,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04n\"}],\"clouds\":{\"all\":81},\"wind\":{\"speed\":2.67,\"deg\":19},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-06 21:00:00\"},{\"dt\":1578355200,\"main\":{\"temp\":271.52,\"feels_like\":266.8,\"temp_min\":271.52,\"temp_max\":271.52,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":1001,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04n\"}],\"clouds\":{\"all\":51},\"wind\":{\"speed\":3.17,\"deg\":17},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-07 00:00:00\"},{\"dt\":1578366000,\"main\":{\"temp\":270.9,\"feels_like\":266.22,\"temp_min\":270.9,\"temp_max\":270.9,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1000,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":5},\"wind\":{\"speed\":3.04,\"deg\":31},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-07 03:00:00\"},{\"dt\":1578376800,\"main\":{\"temp\":270.58,\"feels_like\":265.92,\"temp_min\":270.58,\"temp_max\":270.58,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":1001,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"переменная облачность\",\"icon\":\"03d\"}],\"clouds\":{\"all\":30},\"wind\":{\"speed\":2.95,\"deg\":39},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-07 06:00:00\"},{\"dt\":1578387600,\"main\":{\"temp\":272.48,\"feels_like\":267.9,\"temp_min\":272.48,\"temp_max\":272.48,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1000,\"humidity\":69,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"переменная облачность\",\"icon\":\"03d\"}],\"clouds\":{\"all\":43},\"wind\":{\"speed\":2.72,\"deg\":42},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-07 09:00:00\"},{\"dt\":1578398400,\"main\":{\"temp\":273.44,\"feels_like\":269.15,\"temp_min\":273.44,\"temp_max\":273.44,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":999,\"humidity\":62,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"небольшая облачность\",\"icon\":\"02d\"}],\"clouds\":{\"all\":21},\"wind\":{\"speed\":2.23,\"deg\":22},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-07 12:00:00\"},{\"dt\":1578409200,\"main\":{\"temp\":271.43,\"feels_like\":266.76,\"temp_min\":271.43,\"temp_max\":271.43,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":999,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.73,\"deg\":32},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-07 15:00:00\"},{\"dt\":1578420000,\"main\":{\"temp\":270.68,\"feels_like\":266.16,\"temp_min\":270.68,\"temp_max\":270.68,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":999,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.47,\"deg\":41},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-07 18:00:00\"},{\"dt\":1578430800,\"main\":{\"temp\":270.56,\"feels_like\":265.85,\"temp_min\":270.56,\"temp_max\":270.56,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":999,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04n\"}],\"clouds\":{\"all\":60},\"wind\":{\"speed\":2.68,\"deg\":29},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-07 21:00:00\"},{\"dt\":1578441600,\"main\":{\"temp\":270.55,\"feels_like\":265.57,\"temp_min\":270.55,\"temp_max\":270.55,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":999,\"humidity\":68,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04n\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":3.01,\"deg\":29},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-08 00:00:00\"},{\"dt\":1578452400,\"main\":{\"temp\":270,\"feels_like\":264.74,\"temp_min\":270,\"temp_max\":270,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":999,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04n\"}],\"clouds\":{\"all\":72},\"wind\":{\"speed\":3.4,\"deg\":25},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-08 03:00:00\"},{\"dt\":1578463200,\"main\":{\"temp\":269.68,\"feels_like\":264.3,\"temp_min\":269.68,\"temp_max\":269.68,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1000,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04d\"}],\"clouds\":{\"all\":59},\"wind\":{\"speed\":3.53,\"deg\":32},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-08 06:00:00\"},{\"dt\":1578474000,\"main\":{\"temp\":271.77,\"feels_like\":266.51,\"temp_min\":271.77,\"temp_max\":271.77,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1000,\"humidity\":60,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.36,\"deg\":30},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-08 09:00:00\"},{\"dt\":1578484800,\"main\":{\"temp\":272.75,\"feels_like\":267.72,\"temp_min\":272.75,\"temp_max\":272.75,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1000,\"humidity\":58,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.09,\"deg\":21},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-08 12:00:00\"},{\"dt\":1578495600,\"main\":{\"temp\":271.1,\"feels_like\":266.29,\"temp_min\":271.1,\"temp_max\":271.1,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":1001,\"humidity\":65,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.77,\"deg\":14},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-08 15:00:00\"},{\"dt\":1578506400,\"main\":{\"temp\":270.51,\"feels_like\":266.42,\"temp_min\":270.51,\"temp_max\":270.51,\"pressure\":1025,\"sea_level\":1025,\"grnd_level\":1001,\"humidity\":67,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.71,\"deg\":49},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-08 18:00:00\"},{\"dt\":1578517200,\"main\":{\"temp\":270.24,\"feels_like\":266.9,\"temp_min\":270.24,\"temp_max\":270.24,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":1001,\"humidity\":68,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":0.63,\"deg\":350},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-08 21:00:00\"},{\"dt\":1578528000,\"main\":{\"temp\":270,\"feels_like\":266.36,\"temp_min\":270,\"temp_max\":270,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1000,\"humidity\":69,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.06,\"deg\":227},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-09 00:00:00\"},{\"dt\":1578538800,\"main\":{\"temp\":269.82,\"feels_like\":265.29,\"temp_min\":269.82,\"temp_max\":269.82,\"pressure\":1022,\"sea_level\":1022,\"grnd_level\":998,\"humidity\":67,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04n\"}],\"clouds\":{\"all\":60},\"wind\":{\"speed\":2.27,\"deg\":237},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-09 03:00:00\"},{\"dt\":1578549600,\"main\":{\"temp\":270,\"feels_like\":264.67,\"temp_min\":270,\"temp_max\":270,\"pressure\":1021,\"sea_level\":1021,\"grnd_level\":997,\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"облачно с прояснениями\",\"icon\":\"04d\"}],\"clouds\":{\"all\":78},\"wind\":{\"speed\":3.4,\"deg\":235},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-09 06:00:00\"},{\"dt\":1578560400,\"main\":{\"temp\":272.13,\"feels_like\":265.87,\"temp_min\":272.13,\"temp_max\":272.13,\"pressure\":1019,\"sea_level\":1019,\"grnd_level\":996,\"humidity\":69,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":5.07,\"deg\":234},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-09 09:00:00\"},{\"dt\":1578571200,\"main\":{\"temp\":273.22,\"feels_like\":266.71,\"temp_min\":273.22,\"temp_max\":273.22,\"pressure\":1016,\"sea_level\":1016,\"grnd_level\":993,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":5.64,\"deg\":247},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2020-01-09 12:00:00\"},{\"dt\":1578582000,\"main\":{\"temp\":272.56,\"feels_like\":266.16,\"temp_min\":272.56,\"temp_max\":272.56,\"pressure\":1015,\"sea_level\":1015,\"grnd_level\":992,\"humidity\":95,\"temp_kf\":0},\"weather\":[{\"id\":601,\"main\":\"Snow\",\"description\":\"снег\",\"icon\":\"13n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":6.05,\"deg\":256},\"snow\":{\"3h\":2.13},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2020-01-09 15:00:00\"}],\"city\":{\"id\":702320,\"name\":\"" + cityName + "\",\"coord\":{\"lat\":48.0478,\"lon\":37.9258},\"country\":\"UA\",\"population\":15000,\"timezone\":7200,\"sunrise\":1578115087,\"sunset\":1578145640}}";
                return new MockResponse().setResponseCode(200).setBody(wat);
            }
            return new MockResponse().setResponseCode(404);
        }
    };
    private Dispatcher dispatcherTranslate = new Dispatcher() {

        @Override
        public MockResponse dispatch(RecordedRequest request) {
            if (("/translate?key=trnsl.1.1.20191203T201408Z.4f47aaac61038623.808706d69ca874e035b1c7a8b730e0a9e60f9136&text=" + cityName + "&lang=" + translateLang + "").equals(request.getPath())) {
                return new MockResponse().setResponseCode(200).setBody(responseOfTranslate);
            }
            return new MockResponse().setResponseCode(404);
        }
    };


    @Before
    public void setUp() throws Exception {
        serverWeather = new MockWebServer();
        serverTranslate = new MockWebServer();


        serverWeather.setDispatcher(dispatcherWeather);
        serverTranslate.setDispatcher(dispatcherTranslate);
        serverWeather.start();
        serverTranslate.start();
        HttpUrl baseUrl = serverWeather.url("/");
        HttpUrl baseUrl1 = serverTranslate.url("/");


        RepositoryModule repositoryModule = new RepositoryModule();
        gson = repositoryModule.provideGson();
        GsonConverterFactory gsonConverterFactory = repositoryModule.provideGsonConverterFactory(gson);
        OkHttpClient okHttpClient = repositoryModule.provideOkhttpClient(repositoryModule.provideHttpLoggingInterceptor());

        translateApi = repositoryModule.provideTranslateApi(
                gsonConverterFactory,
                okHttpClient,
                repositoryModule.provideFactory(),
                baseUrl1);


        weatherApi = repositoryModule.provideOpenWeatherMapApi(
                gsonConverterFactory,
                okHttpClient,
                repositoryModule.provideFactory(),
                baseUrl);
        cashRepository = Mockito.mock(ICashRepository.class);
        repository = new WeatherForecastRepository(weatherApi, cashRepository, translateApi);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void getWeatherForecast_isCorrect() {
        repository.getWeatherForecast(lat, lon, lang, metric)
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        WeatherForecast response = testSubscriber.getOnNextEvents().get(0);
        Assert.assertEquals(translateCityName, response.getLocality().getName());
    }


    @Test
    public void getWeatherForecast_with_error_translate_isCorrect() throws Exception {
        serverTranslate.shutdown();
        repository.getWeatherForecast(lat, lon, lang, metric)
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        WeatherForecast response = testSubscriber.getOnNextEvents().get(0);
        Assert.assertEquals(cityName, response.getLocality().getName());
    }

    @Test
    public void getWeatherForecast_with_error_weatherServices_isCorrect() throws Exception {
        serverWeather.shutdown();
        repository.getWeatherForecast(lat, lon, lang, metric)
                .subscribe(testSubscriber);
        testSubscriber.assertNotCompleted();
        testSubscriber.assertNoValues();
        testSubscriber.assertError(ConnectException.class);
    }

    @Test
    public void getWeatherForecast_with_empty_translate_isCorrect() {
        translateApi = Mockito.mock(TranslateApi.class);
        responseOfTranslate = "{\"code\":200,\"lang\":\"" + translateLang + "\",\"text\":[]}";
        TranslateResponse response = gson.fromJson(responseOfTranslate, TranslateResponse.class);
        Mockito.when(translateApi.getTranslate(anyString(), anyString())).thenReturn(Observable.just(response));
        repository = new WeatherForecastRepository(weatherApi, cashRepository, translateApi);
        repository.getWeatherForecast(lat, lon, lang, metric)
                .subscribe(testSubscriber);
        testSubscriber.assertCompleted();
        Assert.assertEquals(testSubscriber.getOnNextEvents().get(0).getLocality().getName(), cityName);

    }


    @After
    public void tearDown() throws Exception {
        serverWeather.shutdown();
        serverTranslate.shutdown();
    }
}
