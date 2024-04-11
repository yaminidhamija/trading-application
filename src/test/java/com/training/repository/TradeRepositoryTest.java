//package com.training.repository;
//
//import com.training.entity.Equity;
//import com.training.entity.Trader;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class TradeRepositoryTest {
//
//
//    public static final String TRADER_NAME = "trade";
//
//    @Autowired
//    private TradeRepository tradeRepository;
//
//    @BeforeEach
//    public void setup(){
//        Trader trader = new Trader();
//        trader.setName(TRADER_NAME);
//        trader.setFundAvailable(100.00);
//        tradeRepository.save(trader);
//    }
//
//    @Test
//    public void testFindAllandById(){
//        List<Trader> traders = tradeRepository.findAll();
//        Assertions.assertEquals(1,traders.size());
//
//        Optional<Trader> traderFromDB = tradeRepository.findById(traders.get(0).getId());
//        Assertions.assertTrue(traderFromDB.isPresent());
//        Assertions.assertEquals(TRADER_NAME,traderFromDB.get().getName());
//
//    }
//
//    @Test
//    public void testEntityNotFound(){
//        Optional<Trader> trader = tradeRepository.findById(100);
//        Assertions.assertFalse(trader.isPresent());
//    }
//}
