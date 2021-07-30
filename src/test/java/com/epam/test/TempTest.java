package com.epam.test;

import static org.junit.Assert.assertEquals;

import com.epam.test.service.TempService;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xu.song
 * @date 2021/7/29 23:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TempTest {

    @Autowired
    private TempService tempService;

    @Test
    public void test() {
        // 2021年7月29日 江苏苏州吴江 温度为24
        Optional<Integer> temperatureV = Optional.of(24);
        Optional<Integer> temperature = tempService.getTemperature("江苏", "苏州", "吴江");
        assertEquals(temperatureV, temperature);
    }

    @Test
    public void testBoundary() {
        System.out.println(tempService.getTemperature("江苏", "苏州", "吴江"));
        System.out.println(tempService.getTemperature("江苏", "苏州", "浦东"));
        System.out.println(tempService.getTemperature("江苏", "芜湖", "吴江"));
        System.out.println(tempService.getTemperature("纽约", "苏州", "吴江"));
    }

    @Test
    public void testException() {
        System.out.println(tempService.getTemperature(null, "苏州", "吴江"));
        System.out.println(tempService.getTemperature("江苏", null, "浦东"));
        System.out.println(tempService.getTemperature("江苏", "芜湖", null));
    }
}
