package com.training.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Trader {

    private Integer id;
    private String name;
    private Double fundAvailable;

    private Set<TradeHolding> tradeHolding;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFundAvailable() {
        return fundAvailable;
    }

    public void setFundAvailable(Double fundAvailable) {
        this.fundAvailable = fundAvailable;
    }

    @OneToMany(mappedBy = "trader", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public Set<TradeHolding> getTradeHolding() {
        return tradeHolding;
    }

    public void setTradeHolding(Set<TradeHolding> tradeHolding) {
        this.tradeHolding = tradeHolding;
    }
}
