package ru.geekbrains.vklimovich.Tournament;

public enum DayOfWeek {
    MONDAY(8.25),
    TUESDAY(8.25),
    WEDNESDAY(8.25),
    THURSDAY(8.25),
    FRIDAY(7),
    SATURDAY(0),
    SUNDAY(0);

    private double workHours;
    DayOfWeek(double workHours){
        this.workHours = workHours;
    }

    public static double getWorkingHours(DayOfWeek day){
        double workingHours = 0;
        for(DayOfWeek curDay: values())
            if (curDay.ordinal() >= day.ordinal())
                workingHours += curDay.workHours;

        return workingHours;
    }

    public static boolean isWorkDay(DayOfWeek day){
        return day.workHours > 0;
    }
}
