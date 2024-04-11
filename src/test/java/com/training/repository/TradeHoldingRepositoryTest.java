//package com.training.repository;
//
//import com.training.entity.Equity;
//import com.training.entity.TradeHolding;
//import com.training.entity.Trader;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class TradeHoldingRepositoryTest {
//
//    public static final String TRADER_NAME = "trade";
//    public static final String EQUITY_NAME = "equity";
//
//
//    @Autowired
//    private TradeHoldingRepository tradeHoldingRepository;
//
//    @Autowired
//    private TradeRepository tradeRepository;
//
//    @Autowired
//    private EquityRepository equityRepository;
//
//    @BeforeEach
//    public void setup(){
//        Trader trader = new Trader();
//        trader.setName(TRADER_NAME);
//        trader.setFundAvailable(100.00);
//        tradeRepository.save(trader);
//
//        Equity equity = new Equity();
//        equity.setName(EQUITY_NAME);
//        equity.setUnitPrice(10.00);
//
//        equityRepository.save(equity);
//
//        TradeHolding tradeHolding = new TradeHolding();
//        tradeHolding.setTotalHolding(10);
//        tradeHolding.setEquity(equity);
//        tradeHolding.setTrader(trader);
//
//        tradeHoldingRepository.save(tradeHolding);
//    }
//
//    @Test
//    public void testFindByTraderIdAndEquityId() {
//        TradeHolding tradeHolding = tradeHoldingRepository.findByTraderIdAndEquityId(tradeRepository.findAll().get(0).getId(), equityRepository.findAll().get(0).getId());
//        Assertions.assertNotNull(tradeHolding);
//        Assertions.assertEquals(10, tradeHolding.getTotalHolding());
//
//    }
//
//    @Test
//    public void testFindByTraderId() {
//        Equity equity = new Equity();
//        equity.setName("test");
//        equity.setUnitPrice(13.00);
//        Equity equityFromDB = equityRepository.save(equity);
//
//        TradeHolding tradeHolding = new TradeHolding();
//        tradeHolding.setTotalHolding(10);
//        tradeHolding.setEquity(equityFromDB);
//        tradeHolding.setTrader(tradeRepository.findAll().get(0));
//
//        tradeHoldingRepository.save(tradeHolding);
//
//        List<TradeHolding> tradeHoldings = tradeHoldingRepository.findByTraderId(tradeRepository.findAll().get(0).getId());
//        Assertions.assertNotNull(tradeHoldings);
//        Assertions.assertEquals(2, tradeHoldings.size());
//
//    }
//
//    @Test
//    public void testFindByTraderIdAndEquityIdNotFound() {
//        TradeHolding tradeHolding = tradeHoldingRepository.findByTraderIdAndEquityId(123212,221231);
//        Assertions.assertNull(tradeHolding);
//    }
//
//    @Test
//    public void testFindByTraderIdNotFound() {
//        Equity equity = new Equity();
//        equity.setName("test");
//        equity.setUnitPrice(13.00);
//        Equity equityFromDB = equityRepository.save(equity);
//
//        TradeHolding tradeHolding = new TradeHolding();
//        tradeHolding.setTotalHolding(10);
//        tradeHolding.setEquity(equityFromDB);
//        tradeHolding.setTrader(tradeRepository.findAll().get(0));
//
//        tradeHoldingRepository.save(tradeHolding);
//
//        List<TradeHolding> tradeHoldings = tradeHoldingRepository.findByTraderId(123212);
//        Assertions.assertEquals(0, tradeHoldings.size()
//        );
//    }
//
//
//
//
//}
