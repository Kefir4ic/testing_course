package ru.ac.uniyar.testingcourse;

public class MockedTimeProvider implements TimeProvider{
    private long mockedTime;

    MockedTimeProvider(){
        this.mockedTime = 0;
    }

    public long currentTimeMillis(){
        return mockedTime;
    }

    void skipTime(double second){
        mockedTime += (long) (second * 1000);
    }

}
