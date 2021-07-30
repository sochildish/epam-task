package com.epam.test.service;

import java.util.Optional;

/**
 * @author xu.song
 * @date 2021/7/29 23:24
 */
public interface TempService {

    /**
     * get temperature by province,city,county
     *
     * @param province province name
     * @param city city name
     * @param county country name
     * @return
     */
    Optional<Integer> getTemperature(String province, String city, String county);
}
