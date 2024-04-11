//package com.training.service;
//
//import com.training.entity.Equity;
//import com.training.entity.TradeHolding;
//import com.training.entity.Trader;
//import com.training.exception.FundUnavailableException;
//import com.training.exception.RecordNotFoundException;
//import com.training.model.EquityRequest;
//import com.training.model.EquityResponse;
//import com.training.repository.EquityRepository;
//import com.training.repository.TradeHoldingRepository;
//import com.training.repository.TradeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.EntityNotFoundException;
//import javax.transaction.Transactional;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Objects;
//
//@Service
//@Transactional
//public class TradeService {
//
//    @Autowired
//    private TradeRepository tradeRepository;
//
//    @Autowired
//    private EquityRepository equityRepository;
//
//    @Autowired
//    private TradeHoldingRepository tradeHoldingRepository;
//
//    public BigDecimal addFund(Trader trade) {
//        BigDecimal updatedBalance = new BigDecimal(trade.getFundAvailable());
//        if (Objects.nonNull(trade.getId())) {
//            try {
//                Trader traderFromDB = getTraderDetails(trade.getId());
//                updatedBalance = updatedBalance.add(new BigDecimal(traderFromDB.getFundAvailable()));
//            } catch (EntityNotFoundException e) {
//                throw new RecordNotFoundException("Trader Details Not Found");
//            }
//        }
//        tradeRepository.save(trade);
//        return updatedBalance;
//    }
//
//    private Trader getTraderDetails(int id) {
//        Trader trader = tradeRepository.getById(id);
//        return trader;
//    }
//
//    public EquityResponse buyEquity(EquityRequest equityRequest) throws RecordNotFoundException {
//        EquityResponse equityResponse = new EquityResponse();
//        //Get Equity Unit Price
//        try {
//            Equity equityDetails = equityRepository.getById(equityRequest.getEquityId());
//            Integer totalUnits = equityRequest.getUnits();
//            //check fund availability
//            Double fundNeeded = equityDetails.getUnitPrice() * equityRequest.getUnits();
//            Trader trader = getTraderDetails(equityRequest.getTraderId());
//            if (trader.getFundAvailable() >= fundNeeded) {
//                //Check if there's existing record
//                TradeHolding tradeHolding = tradeHoldingRepository.findByTraderIdAndEquityId(equityRequest.getTraderId(), equityRequest.getEquityId());
//                if (Objects.nonNull(tradeHolding)) {
//                    totalUnits = equityRequest.getUnits() + tradeHolding.getTotalHolding();
//                    tradeHolding.setTotalHolding(totalUnits);
//                    tradeHoldingRepository.save(tradeHolding);
//                } else {
//                    //Add New Entry to trade holding table
//                    tradeHolding = new TradeHolding();
//                    tradeHolding.setEquity(equityDetails);
//                    tradeHolding.setTrader(trader);
//                    tradeHolding.setTotalHolding(equityRequest.getUnits());
//                    tradeHoldingRepository.save(tradeHolding);
//                }
//                equityResponse.setEquityId(equityRequest.getEquityId());
//                equityResponse.setEquityName(equityDetails.getName());
//                equityResponse.setUnitPrice(equityDetails.getUnitPrice());
//                equityResponse.setTotalHoldings(totalUnits);
//
//                //Update Trader's Balance
//                trader.setFundAvailable(trader.getFundAvailable() - fundNeeded);
//                tradeRepository.save(trader);
//            } else {
//                throw new FundUnavailableException("Insufficient Funds");
//            }
//
//        } catch (EntityNotFoundException e) {
//            throw new RecordNotFoundException("Equity/Traders Details not found");
//        }
//
//        return equityResponse;
//    }
//
//    public EquityResponse sellEquity(EquityRequest equityRequest) {
//        EquityResponse response = new EquityResponse();
//
//        //Check Equity belongs to trader
//        TradeHolding tradeHolding = tradeHoldingRepository.findByTraderIdAndEquityId(equityRequest.getTraderId(), equityRequest.getEquityId());
//        if (Objects.nonNull(tradeHolding)) {
//            //Update Equity holding
//            tradeHolding.setTotalHolding(tradeHolding.getTotalHolding() - equityRequest.getUnits());
//            tradeHoldingRepository.save(tradeHolding);
//
//            //Fetch Trader and Equity details for Updating Fund
//            Trader trader = tradeHolding.getTrader();
//            Equity equity = tradeHolding.getEquity();
//            trader.setFundAvailable(trader.getFundAvailable() + (equity.getUnitPrice() * equityRequest.getUnits()));
//            tradeRepository.save(trader);
//
//            response.setTotalHoldings(tradeHolding.getTotalHolding());
//            response.setEquityName(equity.getName());
//            response.setEquityId(equityRequest.getEquityId());
//            response.setUnitPrice(equity.getUnitPrice());
//        } else {
//            throw new RecordNotFoundException("Equity for Trader doesn't exist");
//        }
//        return response;
//    }
//}
