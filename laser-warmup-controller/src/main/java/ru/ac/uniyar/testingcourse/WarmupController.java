package ru.ac.uniyar.testingcourse;

/**
 * Класс предназначен для получения остаточного времени прогрева лазера 
 * относительно текущего момента времени. Время прогрева определяется 
 * по следующим правилам:
 * <ul>
 * <li>Время прогрева лазера из холодного состояния равно 2 мин.
 * <li>После выключения лазер полностью охлаждается за 1 мин.
 * <li>Если лазер охладился не полностью, то прогрев его не нужен при условии,
 * что предыдущий прогрев не был прерван.
 * <li>Если лазер охладился не полностью и предыдущий его прогрев был прерван,
 * то его следует прогревать в течение времени, оставшегося на момент прерывания
 * предыдущего прогрева.
 * </ul>
 */
public class WarmupController {
    //! Длительность прогрева из холодного состояния
    private final double warmupDurationFromCold = 120.0;
    //! Длительность промежутка времени, после которого прогрев осуществляется заново
    private final double thresholdDuration = 60.0;
    //! Время начала прогрева
    double warmupStartTime;
    //! Время остановки лазера
    double laserStopTime;
    //! Длительность текущего прогрева
    double RemainingTimeToWarmup;
    //! Состояние лазера
    boolean on;
    // источник времени
    TimeProvider timeProvider;

    /** Обозначить текущий момент в качестве момента включения лазера. */
    public void markLaserOn() {
        on = true;
        double laserActiveDuration = laserStopTime - warmupStartTime;
        warmupStartTime = timeProvider.currentTimeMillis() / 1000.0;
        double laserInactiveDuration = warmupStartTime - laserStopTime;
        if(laserInactiveDuration >= thresholdDuration) {
            RemainingTimeToWarmup = warmupDurationFromCold;
        } else if(laserActiveDuration < RemainingTimeToWarmup) {
            RemainingTimeToWarmup -= laserActiveDuration;
        } else {
            RemainingTimeToWarmup = 0;
        }
    }

    /** Обозначить текущий момент в качестве момента выключения лазера. */
    public void markLaserOff() {
        on = false;
        laserStopTime = timeProvider.currentTimeMillis() / 1000.0;
    }

    /**
     * Получить остаточное время прогрева лазера относительно текущего момента.
     * @return Остаточное время прогрева в секундах.
     * @throws IllegalStateException если лазер выключен.
     */
    public double getRemainingTime() {
        if (!on) throw new IllegalStateException(
                "Getting remaining time when the laser is off is not allowed");
        return Math.max(0, RemainingTimeToWarmup -
                (timeProvider.currentTimeMillis() / 1000.0 - warmupStartTime));
    }

    public WarmupController(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
        warmupStartTime = laserStopTime = - thresholdDuration * 2;
        RemainingTimeToWarmup = 0;
        on = false;
    }

    public  WarmupController(){
        this(new SystemTimeProvider());
    }
}
