package com.epam.test.controller;

import com.epam.test.service.TempService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xu.song
 * @date 2021/7/29 23:23
 */
@RestController
public class TempController {

    @Autowired
    private TempService tempService;

    @GetMapping(value = {"/temperature"})
    public Optional<Integer> getTemperature(@RequestParam String province, @RequestParam String city, @RequestParam String county) {
        return tempService.getTemperature(province, city, county);
    }

}
