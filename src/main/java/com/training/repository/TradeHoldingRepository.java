//package com.training.repository;
//
//import com.training.entity.TradeHolding;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface TradeHoldingRepository extends JpaRepository<TradeHolding,Integer> {
//
//    @Query("select th from TradeHolding th where th.trader.id = ?1 and th.equity.id = ?2")
//    public TradeHolding findByTraderIdAndEquityId(Integer traderId, Integer equityId);
//
//    @Query("select th from TradeHolding th where th.trader.id = ?1")
//    public List<TradeHolding> findByTraderId(Integer traderId);
//}
