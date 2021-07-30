package com.epam.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.epam.test.bean.WeatherBean;
import com.epam.test.service.TempService;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author xu.song
 * @date 2021/7/29 23:24
 */
@Service
public class TempServiceImpl implements TempService {

    private static BlockingQueue<String> reqQueue = new ArrayBlockingQueue<>(100);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<Integer> getTemperature(String province, String city, String county) {

        // default value
        Integer temp = -100;

        if (isEmpty(province) || isEmpty(city) || isEmpty(county)) {
            System.out.println(province + city + county + ":" + temp);
            return Optional.of(temp);
        }

        if (!reqQueue.offer(province + city + county)) {
            System.out.println(province + city + county + ":" + temp);
            return Optional.of(temp);
        }

        try {
            Map<String, String> proVNameMap = getProvOfChinaMap();
            if (proVNameMap == null || !proVNameMap.containsKey(province)) {
                return Optional.of(temp);
            }

            String provCode = proVNameMap.get(province);
            Map<String, String> cityNameMap = getCityMap(provCode);
            if (cityNameMap == null || !cityNameMap.containsKey(city)) {
                return Optional.of(temp);
            }

            String cityCode = cityNameMap.get(city);
            Map<String, String> countryNameMap = getCountryMap(provCode + cityCode);
            if (countryNameMap == null || !countryNameMap.containsKey(county)) {
                return Optional.of(temp);
            }

            String countyCode = countryNameMap.get(county);
            WeatherBean weatherBean = getWeatherBean(provCode + cityCode + countyCode);
            if (weatherBean == null || weatherBean.getWeatherInfo() == null) {
                return Optional.of(temp);
            }
            String tempStr = weatherBean.getWeatherInfo().getTemp();
            if (isEmpty(tempStr)) {
                return Optional.of(temp);
            }
            temp = Double.valueOf(tempStr).intValue();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reqQueue.poll();
        }

        System.out.println(province + city + county + ":" + temp);
        return Optional.of(temp);
    }

    /**
     * get all the provinces in China
     *
     * @return
     */
    @Retryable(value = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000L, multiplier = 1))
    private Map<String, String> getProvOfChinaMap() {

        Map<String, String> proVNameMap = null;

        String cityUrl = "http://www.weather.com.cn/data/city3jdata/china.html";
        String provRestStr = restTemplate.getForObject(cityUrl, String.class);
        if (isEmpty(provRestStr)) {
            return null;
        }

        try {
            Map<String, String> provMap = JSON.parseObject(provRestStr, Map.class);
            if (provMap != null && !provMap.isEmpty()) {
                proVNameMap = provMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
            }
        } catch (Exception e) {
            System.out.println("json parse error");
        }

        return proVNameMap;
    }

    /**
     * get all the cities by province code
     *
     * @param provCode province code
     * @return
     */
    @Retryable(value = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000L, multiplier = 1))
    private Map<String, String> getCityMap(String provCode) {

        if (isEmpty(provCode)) {
            return null;
        }

        Map<String, String> cityNameMap = null;

        String provUrl = "http://www.weather.com.cn/data/city3jdata/provshi/" + provCode + ".html";
        String cityRestStr = restTemplate.getForObject(provUrl, String.class);
        if (isEmpty(cityRestStr)) {
            return null;
        }

        try {
            Map<String, String> cityMap = JSON.parseObject(cityRestStr, Map.class);
            if (cityMap != null && !cityMap.isEmpty()) {
                cityNameMap = cityMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
            }
        } catch (Exception e) {
            System.out.println("json parse error");
        }

        return cityNameMap;
    }

    /**
     * get all the countries by city code
     *
     * @param cityCode city code
     * @return
     */
    @Retryable(value = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000L, multiplier = 1))
    private Map<String, String> getCountryMap(String cityCode) {

        if (isEmpty(cityCode)) {
            return null;
        }

        Map<String, String> countryNameMap = null;

        String stationUrl = "http://www.weather.com.cn/data/city3jdata/station/" + cityCode + ".html";
        String countyRestStr = restTemplate.getForObject(stationUrl, String.class);
        if (isEmpty(countyRestStr)) {
            return null;
        }

        try {
            Map<String, String> countryMap = JSON.parseObject(countyRestStr, Map.class);
            if (countryMap != null && !countryMap.isEmpty()) {
                countryNameMap = countryMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
            }
        } catch (Exception e) {
            System.out.println("json parse error");
        }

        return countryNameMap;
    }

    /**
     * get weather by country code
     *
     * @param countyCode country code
     * @return
     */
    @Retryable(value = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000L, multiplier = 1))
    private WeatherBean getWeatherBean(String countyCode) {

        if (isEmpty(countyCode)) {
            return null;
        }

        WeatherBean weatherBean = null;

        String skUrl = "http://www.weather.com.cn/data/sk/" + countyCode + ".html";
        String weatherBeanStr = restTemplate.getForObject(skUrl, String.class);
        if (isEmpty(weatherBeanStr)) {
            return null;
        }

        try {
            weatherBean = JSON.parseObject(weatherBeanStr, WeatherBean.class);
        } catch (Exception e) {
            System.out.println("json parse error");
        }

        return weatherBean;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

}
