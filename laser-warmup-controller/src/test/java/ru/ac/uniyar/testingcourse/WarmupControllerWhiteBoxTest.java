package ru.ac.uniyar.testingcourse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import  static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WarmupControllerWhiteBoxTest {

    MockedTimeProvider mockedTimeProvider = new MockedTimeProvider();
    WarmupController controller = new WarmupController(mockedTimeProvider);

    @Test
    void testGetRemainingTimeWhenOff(){
        controller.markLaserOff();
        assertThatThrownBy(() -> controller.getRemainingTime())
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "60, 60, 120",
            "10, 20, 110",
            "10, 60, 120",
            "300, 0, 0"
    })
    void testGetRemainingTimeWhenOn(int firstWarmingTime, int coolingTime, int correctRemainingTime){
        controller.markLaserOn();
        mockedTimeProvider.skipTime(firstWarmingTime);
        controller.markLaserOff();
        mockedTimeProvider.skipTime(coolingTime);
        controller.markLaserOn();
        assertThat(controller.getRemainingTime()).isEqualTo(correctRemainingTime);
    }
}
