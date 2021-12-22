package com.training.utility;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class TradeUtil {

    public static boolean validateTime(LocalDateTime localDateTime){
        boolean withinRange = false;
        if(Objects.nonNull(localDateTime)){
            DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
            if(dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.THURSDAY || dayOfWeek == DayOfWeek.FRIDAY){
                //Now check for Time between 9 am to 5 Pm
                LocalDateTime morningTime = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.of(9,0));
                LocalDateTime eveningTime = LocalDateTime.of(localDateTime.toLocalDate(),LocalTime.of(17,0));
                if((localDateTime.isEqual(morningTime) || localDateTime.isAfter(morningTime)) && (localDateTime.isEqual(eveningTime) || localDateTime.isBefore(eveningTime))){
                    withinRange = true;
                }
            }
        }

        return withinRange;
    }


}
