package com.training.service;

import com.training.entity.Equity;
import com.training.entity.TradeHolding;
import com.training.entity.Trader;
import com.training.exception.FundUnavailableException;
import com.training.exception.RecordNotFoundException;
import com.training.model.EquityRequest;
import com.training.model.EquityResponse;
import com.training.repository.EquityRepository;
import com.training.repository.TradeHoldingRepository;
import com.training.repository.TradeRepository;
import com.training.service.TradeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private EquityRepository equityRepository;

    @Mock
    private TradeHoldingRepository tradeHoldingRepository;

    @Test
    public void testAddFund(){
        Trader trader = new Trader();
        trader.setId(1);
        trader.setFundAvailable(10.00);

        Trader traderFromDB = new Trader();
        traderFromDB.setId(1);
        traderFromDB.setFundAvailable(5.00);

        when(tradeRepository.getById(1)).thenReturn(traderFromDB);
        when(tradeRepository.save(trader)).thenReturn(traderFromDB);

        BigDecimal result = tradeService.addFund(trader);
        Assertions.assertEquals(new BigDecimal(15),result);

        verify(tradeRepository,times(1)).save(trader);
    }

    @Test
    public void testAddFundForNewUser(){
        Trader trader = new Trader();
        trader.setFundAvailable(10.00);
        trader.setName("test");

        Trader traderFromDB = new Trader();
        traderFromDB.setId(1);
        traderFromDB.setFundAvailable(5.00);
        traderFromDB.setId(1);
        when(tradeRepository.save(trader)).thenReturn(traderFromDB);

        BigDecimal result = tradeService.addFund(trader);
        Assertions.assertEquals(new BigDecimal(10),result);

        verify(tradeRepository,times(1)).save(trader);
    }

    @Test
    public void testAddFundForInvalidTrader(){
        Trader trader = new Trader();
        trader.setFundAvailable(10.00);
        trader.setName("test");
        trader.setId(1);
        when(tradeRepository.getById(1)).thenThrow(EntityNotFoundException.class);
        Assertions.assertThrows(RecordNotFoundException.class,()->tradeService.addFund(trader));
    }

    @Test
    public void testBuyEquityNew() {
        EquityRequest equityRequest = new EquityRequest();
        equityRequest.setEquityId(1);
        equityRequest.setTraderId(1);
        equityRequest.setUnits(2);

        Equity equity = new Equity();
        equity.setId(1);
        equity.setUnitPrice(5.00);
        equity.setName("EQUITY");

        Trader trader = new Trader();
        trader.setId(1);
        trader.setName("TRADE");
        trader.setFundAvailable(100.00);

        when(equityRepository.getById(equityRequest.getEquityId())).thenReturn(equity);
        when(tradeRepository.getById(equityRequest.getTraderId())).thenReturn(trader);

        ArgumentCaptor<TradeHolding> tradeHoldingArgumentCaptor = ArgumentCaptor.forClass(TradeHolding.class);
        when(tradeHoldingRepository.findByTraderIdAndEquityId(anyInt(), anyInt())).thenReturn(null);
        when(tradeHoldingRepository.save(tradeHoldingArgumentCaptor.capture())).thenReturn(new TradeHolding());

        ArgumentCaptor<Trader> traderArgumentCaptor = ArgumentCaptor.forClass(Trader.class);
        when(tradeRepository.save(traderArgumentCaptor.capture())).thenReturn(trader);

        EquityResponse equityResponse = tradeService.buyEquity(equityRequest);
        TradeHolding tradeHolding = tradeHoldingArgumentCaptor.getValue();
        Trader trader1 = traderArgumentCaptor.getValue();

        Assertions.assertNotNull(equityResponse);
        Assertions.assertEquals(equityRequest.getUnits(),equityResponse.getTotalHoldings());
        Assertions.assertEquals(equityRequest.getUnits(),tradeHolding.getTotalHolding());
        Assertions.assertEquals(90,trader1.getFundAvailable());
    }

    @Test
    public void testBuyEquityExisting() {
        EquityRequest equityRequest = new EquityRequest();
        equityRequest.setEquityId(1);
        equityRequest.setTraderId(1);
        equityRequest.setUnits(2);

        Equity equity = new Equity();
        equity.setId(1);
        equity.setUnitPrice(5.00);
        equity.setName("EQUITY");

        Trader trader = new Trader();
        trader.setId(1);
        trader.setName("TRADE");
        trader.setFundAvailable(100.00);

        when(equityRepository.getById(equityRequest.getEquityId())).thenReturn(equity);
        when(tradeRepository.getById(equityRequest.getTraderId())).thenReturn(trader);

        TradeHolding tradeHolding = new TradeHolding();
        tradeHolding.setEquity(equity);
        tradeHolding.setTrader(trader);
        tradeHolding.setTotalHolding(2);
        when(tradeHoldingRepository.findByTraderIdAndEquityId(anyInt(), anyInt())).thenReturn(tradeHolding);

        ArgumentCaptor<Trader> traderArgumentCaptor = ArgumentCaptor.forClass(Trader.class);
        when(tradeRepository.save(traderArgumentCaptor.capture())).thenReturn(trader);

        EquityResponse equityResponse = tradeService.buyEquity(equityRequest);
        Trader trader1 = traderArgumentCaptor.getValue();

        Assertions.assertNotNull(equityResponse);
        Assertions.assertEquals(4,equityResponse.getTotalHoldings());
        Assertions.assertEquals(4,tradeHolding.getTotalHolding());
        Assertions.assertEquals(90,trader1.getFundAvailable());
    }

    @Test
    public void testBuyEquityExistingOnFundEqualToAmountNeeded() {
        EquityRequest equityRequest = new EquityRequest();
        equityRequest.setEquityId(1);
        equityRequest.setTraderId(1);
        equityRequest.setUnits(20);

        Equity equity = new Equity();
        equity.setId(1);
        equity.setUnitPrice(5.00);
        equity.setName("EQUITY");

        Trader trader = new Trader();
        trader.setId(1);
        trader.setName("TRADE");
        trader.setFundAvailable(100.00);

        when(equityRepository.getById(equityRequest.getEquityId())).thenReturn(equity);
        when(tradeRepository.getById(equityRequest.getTraderId())).thenReturn(trader);

        TradeHolding tradeHolding = new TradeHolding();
        tradeHolding.setEquity(equity);
        tradeHolding.setTrader(trader);
        tradeHolding.setTotalHolding(2);
        when(tradeHoldingRepository.findByTraderIdAndEquityId(anyInt(), anyInt())).thenReturn(tradeHolding);

        ArgumentCaptor<Trader> traderArgumentCaptor = ArgumentCaptor.forClass(Trader.class);
        when(tradeRepository.save(traderArgumentCaptor.capture())).thenReturn(trader);

        EquityResponse equityResponse = tradeService.buyEquity(equityRequest);
        Trader trader1 = traderArgumentCaptor.getValue();

        Assertions.assertNotNull(equityResponse);
        Assertions.assertEquals(22,equityResponse.getTotalHoldings());
        Assertions.assertEquals(22,tradeHolding.getTotalHolding());
        Assertions.assertEquals(0,trader1.getFundAvailable());
    }

    @Test
    public void testBuyEquityInsufficientFunds() {
        EquityRequest equityRequest = new EquityRequest();
        equityRequest.setEquityId(1);
        equityRequest.setTraderId(1);
        equityRequest.setUnits(2);

        Equity equity = new Equity();
        equity.setId(1);
        equity.setUnitPrice(5.00);
        equity.setName("EQUITY");

        Trader trader = new Trader();
        trader.setId(1);
        trader.setName("TRADE");
        trader.setFundAvailable(5.00);

        when(equityRepository.getById(equityRequest.getEquityId())).thenReturn(equity);
        when(tradeRepository.getById(equityRequest.getTraderId())).thenReturn(trader);

       Assertions.assertThrows(FundUnavailableException.class,()->tradeService.buyEquity(equityRequest));
    }

    @Test
    public void testBuyEquityInvalidTrader() {
        EquityRequest equityRequest = new EquityRequest();
        equityRequest.setEquityId(1);
        equityRequest.setTraderId(1);
        equityRequest.setUnits(2);

        Equity equity = new Equity();
        equity.setId(1);
        equity.setUnitPrice(5.00);
        equity.setName("EQUITY");

        when(equityRepository.getById(equityRequest.getEquityId())).thenReturn(equity);

        when(tradeRepository.getById(equityRequest.getTraderId())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(RecordNotFoundException.class,()->tradeService.buyEquity(equityRequest));
    }

    @Test
    public void testBuyEquityInvalidEquity() {
        EquityRequest equityRequest = new EquityRequest();
        equityRequest.setEquityId(1);
        equityRequest.setTraderId(1);
        equityRequest.setUnits(2);

        when(equityRepository.getById(equityRequest.getEquityId())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(RecordNotFoundException.class,()->tradeService.buyEquity(equityRequest));
    }

    @Test
    public void testSellEquity() {
        EquityRequest equityRequest = new EquityRequest();
        equityRequest.setEquityId(1);
        equityRequest.setTraderId(1);
        equityRequest.setUnits(2);

        Equity equity = new Equity();
        equity.setId(1);
        equity.setUnitPrice(5.00);
        equity.setName("EQUITY");

        Trader trader = new Trader();
        trader.setId(1);
        trader.setName("TRADE");
        trader.setFundAvailable(100.00);

        TradeHolding tradeHolding = new TradeHolding();
        tradeHolding.setTotalHolding(4);
        tradeHolding.setTrader(trader);
        tradeHolding.setEquity(equity);

        when(tradeHoldingRepository.findByTraderIdAndEquityId(equityRequest.getTraderId(), equityRequest.getEquityId())).thenReturn(tradeHolding);

        ArgumentCaptor<Trader> traderArgumentCaptor = ArgumentCaptor.forClass(Trader.class);
        when(tradeRepository.save(traderArgumentCaptor.capture())).thenReturn(trader);

        EquityResponse equityResponse = tradeService.sellEquity(equityRequest);
        Assertions.assertNotNull(equityResponse);
        Assertions.assertEquals(2,equityResponse.getTotalHoldings());
        Assertions.assertEquals(110,traderArgumentCaptor.getValue().getFundAvailable());
    }

    @Test
    public void testSellEquityInvalidDetails() {
        EquityRequest equityRequest = new EquityRequest();
        equityRequest.setEquityId(1);
        equityRequest.setTraderId(1);
        equityRequest.setUnits(2);

        Equity equity = new Equity();
        equity.setId(1);
        equity.setUnitPrice(5.00);
        equity.setName("EQUITY");

        Trader trader = new Trader();
        trader.setId(1);
        trader.setName("TRADE");
        trader.setFundAvailable(100.00);

        TradeHolding tradeHolding = new TradeHolding();
        tradeHolding.setTotalHolding(4);
        tradeHolding.setTrader(trader);
        tradeHolding.setEquity(equity);

        when(tradeHoldingRepository.findByTraderIdAndEquityId(equityRequest.getTraderId(), equityRequest.getEquityId())).thenReturn(null);

       Assertions.assertThrows(RecordNotFoundException.class, () -> tradeService.sellEquity(equityRequest));
    }
}
