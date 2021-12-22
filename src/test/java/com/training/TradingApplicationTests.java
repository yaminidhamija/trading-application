package com.training;

import com.training.controller.TradeController;
import com.training.entity.Trader;
import com.training.model.EquityRequest;
import com.training.model.EquityResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class TradingApplicationTests {

    private String baseUrl = "http://localhost:";

    @Autowired
    TradeController tradeController;

    @LocalServerPort
    int randomServerPort;

    private static RestTemplate restTemplate = null;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }



    @Test
    void contextLoads() {
        Assertions.assertNotNull(tradeController);
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl+randomServerPort;
    }

    @Test
    @Sql(statements = "INSERT INTO trader (id, name, fund_available) VALUES (1, 'Test', 100.0)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM trader",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testAddFund() throws URISyntaxException
    {
        final String baseUrl = this.baseUrl+"/fund";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        Trader trader = new Trader(){{
            setId(1);
            setFundAvailable(100.00);
        }};

        HttpEntity<Trader> request = new HttpEntity<>(trader, headers);

        ResponseEntity<BigDecimal> result = restTemplate.postForEntity(uri, trader, BigDecimal.class);
        //Verify request succeed
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(new BigDecimal(200),result.getBody());
    }

    @Test
    @Sql(statements = "INSERT INTO trader (id, name, fund_available) VALUES (1, 'Test', 1000.0)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT into equity(id,name,unit_price) VALUES(1,'EQUITY1',10)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM trade_holding",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM trader",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM equity",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testBuyEquity() throws URISyntaxException {
        final String baseUrl = this.baseUrl + "/buy";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        EquityRequest equityRequest = new EquityRequest() {{
            setEquityId(1);
            setTraderId(1);
            setUnits(10);
        }};

        HttpEntity<EquityRequest> request = new HttpEntity<>(equityRequest, headers);
        ResponseEntity<EquityResponse> result = null;
       try {
            result = restTemplate.postForEntity(uri, equityRequest, EquityResponse.class);
            Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
            Assertions.assertEquals(10, result.getBody().getTotalHoldings());
        } catch (HttpClientErrorException.BadRequest e) {
            Assertions.assertTrue(e.getMessage().contains(TradeController.TIME_RANGE_MESSAGE));
        }

    }

    @Test
    @Sql(statements = "INSERT INTO trader (id, name, fund_available) VALUES (1, 'Test', 1000.0)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT into equity(id,name,unit_price) VALUES(1,'EQUITY1',10)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT into trade_holding(id,trade_id,equity_id,total_holding) VALUES(1,1,1,10)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM trade_holding",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM trader",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM equity",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSellEquity() throws URISyntaxException {
        final String baseUrl = this.baseUrl + "/sell";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");
        EquityRequest equityRequest = new EquityRequest() {{
            setEquityId(1);
            setTraderId(1);
            setUnits(6);
        }};

        HttpEntity<EquityRequest> request = new HttpEntity<>(equityRequest, headers);
        ResponseEntity<EquityResponse> result = null;
        try {
            result = restTemplate.postForEntity(uri, equityRequest, EquityResponse.class);
            Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
            Assertions.assertEquals(4, result.getBody().getTotalHoldings());
        } catch (HttpClientErrorException.BadRequest e) {
            Assertions.assertTrue(e.getMessage().contains(TradeController.TIME_RANGE_MESSAGE));
        }

    }
}
