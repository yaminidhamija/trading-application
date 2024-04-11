//package com.training.utility;
//
//import org.apache.tomcat.jni.Local;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.*;
//
//@ExtendWith(MockitoExtension.class)
//public class TradeUtilTest {
//
//
//    @Test
//    public void testValidateTimeWithinRange(){
//        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2022,01,12),LocalTime.of(12,30));
//        boolean result = TradeUtil.validateTime(localDateTime);
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    public void testValidateTimeOutsideRange(){
//        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2022,01,12),LocalTime.of(6,30));
//        boolean result = TradeUtil.validateTime(localDateTime);
//        Assertions.assertFalse(result);
//    }
//
//    @Test
//    public void testValidateTimeAtStartTime(){
//        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2022,01,12),LocalTime.of(9,0));
//        boolean result = TradeUtil.validateTime(localDateTime);
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    public void testValidateTimeAtEndTime(){
//        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2022,01,12),LocalTime.of(17,0));
//        boolean result = TradeUtil.validateTime(localDateTime);
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    public void testValidateTimeNullInput(){
//        boolean result = TradeUtil.validateTime(null);
//        Assertions.assertFalse(result);
//    }
//
//    @Test
//    public void testValidateTimeWeekend(){
//        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2021,12,25),LocalTime.of(10,30));
//        boolean result = TradeUtil.validateTime(localDateTime);
//        Assertions.assertFalse(result);
//    }
//
//    @Test
//    public void testValidateTimeWeekday(){
//        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2021,12,25),LocalTime.of(10,30));
//        boolean result = TradeUtil.validateTime(localDateTime);
//        Assertions.assertFalse(result);
//    }
//
//
//}
//
