package ru.ac.uniyar.testingcourse;

public class SystemTimeProvider implements TimeProvider{
    public long currentTimeMillis(){
        return System.currentTimeMillis();
    }

}
