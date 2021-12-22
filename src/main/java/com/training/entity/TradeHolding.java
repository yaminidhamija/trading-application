package com.training.entity;

import javax.persistence.*;

@Entity
@Table(name = "trade_holding")
public class TradeHolding {

    private Integer id;

    private Equity equity;

    private Trader trader;

    private Integer totalHolding;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equity_id", nullable = false)
    public Equity getEquity() {
        return equity;
    }

    public void setEquity(Equity equity) {
        this.equity = equity;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trade_id", nullable = false)
    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public Integer getTotalHolding() {
        return totalHolding;
    }

    public void setTotalHolding(Integer totalHolding) {
        this.totalHolding = totalHolding;
    }
}
