package com.training.repository;

import com.training.entity.Equity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquityRepository extends JpaRepository<Equity,Integer> {
}
