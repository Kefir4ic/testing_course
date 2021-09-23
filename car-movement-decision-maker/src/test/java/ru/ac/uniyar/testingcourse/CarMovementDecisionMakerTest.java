package ru.ac.uniyar.testingcourse;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CarMovementDecisionMakerTest {
    private final  CarMovementDecisionMaker decisionMarker = new CarMovementDecisionMaker();

    boolean toBoolean(String bool) {
        if (bool.equals("true")) return true;
        if (bool.equals("false")) return false;
        if (bool.equals("on")) return true;
        if (bool.equals("off")) return false;
        throw  new ArgumentConversionException("Not is Boolean");
    }

    Ternar toTernar(String signal) {
        if (signal.equals("on"))
            return Ternar.ON;
        if (signal.equals("off"))
            return Ternar.OFF;
        if (signal.equals("blinking"))
            return Ternar.BLINKING;
        throw new ArgumentConversionException("Not is Ternary");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/traffic.csv",numLinesToSkip=1)
    void testTrafficLights(String name,String red, String yellow, String green, String go, String prepare,String caution){
        boolean red_bool = toBoolean(red);
        Ternar yellow_ternary = toTernar(yellow);
        Ternar green_ternary = toTernar(green);
        boolean go_bool = toBoolean(go);
        boolean prepare_bool = toBoolean(prepare);
        boolean caution_bool = toBoolean(caution);
        decisionMarker.setTrafficLightState(red_bool, yellow_ternary, green_ternary);
        assertThat(decisionMarker.isToGo()).isEqualTo(go_bool);
        assertThat(decisionMarker.isToBePrepared()).isEqualTo(prepare_bool);
        assertThat(decisionMarker.isToBeCautious()).isEqualTo(caution_bool);
    }
}
