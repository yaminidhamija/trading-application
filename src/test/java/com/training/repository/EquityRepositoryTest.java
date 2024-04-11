//package com.training.repository;
//
//import com.training.entity.Equity;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class EquityRepositoryTest {
//
//    public static final String EQUITY_NAME = "equity";
//
//    @Autowired
//    private EquityRepository equityRepository;
//
//    @BeforeEach
//    public void setup(){
//        Equity equity = new Equity();
//        equity.setName(EQUITY_NAME);
//        equity.setUnitPrice(10.00);
//
//        equityRepository.save(equity);
//    }
//
//    @Test
//    public void testFindAllAndById(){
//        List<Equity> equities = equityRepository.findAll();
//        Assertions.assertEquals(1,equities.size());
//
//        Equity equity1 = equityRepository.getById(equities.get(0).getId());
//        Assertions.assertNotNull(equity1);
//        Assertions.assertEquals(EQUITY_NAME,equity1.getName());
//    }
//
//    @Test
//    public void testEntityNotFound(){
//        List<Equity> equities = equityRepository.findAll();
//        Optional<Equity> equity = equityRepository.findById(100);
//        Assertions.assertEquals(1,equities.size());
//        Assertions.assertFalse(equity.isPresent());
//    }
//}
