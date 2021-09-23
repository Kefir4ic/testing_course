package ru.ac.uniyar.testingcourse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class WarmupControllerBlackBoxTest {

//    class SystemMockUp extends MockUp<System> {
//
//        private long currentTime = 0;
//
//        void setCurrentTime(double currentTimeSeconds) {
//            currentTime = (long) (currentTimeSeconds * 1000);
//        }
//
//        void skipTime(double second){
//            currentTime += (long) (second * 1000);
//        }
//
//        @Mock
//        long currentTimeMillis() {
//            return currentTime;
//        }
//    }
//
//    SystemMockUp systemMockUp = new SystemMockUp();

    MockedTimeProvider mockedTimeProvider = new MockedTimeProvider();

    @Test
    void noWarmupCase() {
        WarmupController controller = new WarmupController(mockedTimeProvider);
        controller.markLaserOn();
        assertThat(controller.getRemainingTime()).isEqualTo(120);
    }

    @Test
    void fullWarmupCase() {
        WarmupController controller = new WarmupController(mockedTimeProvider);
        controller.markLaserOn();
        mockedTimeProvider.skipTime(120);
        assertThat(controller.getRemainingTime()).isEqualTo(0);
    }

    @ParameterizedTest
    @CsvSource({
            "60, 60, 120",
            "10, 20, 110", // Остывание дискретный процесс
            "10, 60, 120"
    })
    void ParametrizedWarmupCase(int firstWarmingTime, int coolingTime, int correctRemainingTime){
        WarmupController controller = new WarmupController(mockedTimeProvider);
        controller.markLaserOn();
        mockedTimeProvider.skipTime(firstWarmingTime);
        controller.markLaserOff();
        mockedTimeProvider.skipTime(coolingTime);
        controller.markLaserOn();
        assertThat(controller.getRemainingTime()).isEqualTo(correctRemainingTime);
    }
}
