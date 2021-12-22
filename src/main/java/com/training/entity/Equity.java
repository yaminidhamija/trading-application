package com.training.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Equity {

    private Integer id;
    private String name;
    private Double unitPrice;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @OneToMany(mappedBy = "equity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public Set<TradeHolding> getTradeHolding() {
        return tradeHolding;
    }

    public void setTradeHolding(Set<TradeHolding> tradeHolding) {
        this.tradeHolding = tradeHolding;
    }
}
