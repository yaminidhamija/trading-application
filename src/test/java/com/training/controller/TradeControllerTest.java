package com.training.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.entity.Trader;
import com.training.exception.FundUnavailableException;
import com.training.exception.RecordNotFoundException;
import com.training.model.EquityRequest;
import com.training.model.EquityResponse;
import com.training.service.TradeService;
import com.training.utility.TradeUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
class TradeControllerTest {

    @MockBean
    TradeService tradeService;

    @Autowired
    TradeController tradeController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testAddFunds() throws Exception {
        Trader tradeToAddFund = new Trader(){{
            setId(1);
            setName("Test");
            setFundAvailable(100.00);
        }};
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(tradeToAddFund);
        when(tradeService.addFund(any(Trader.class))).thenReturn(BigDecimal.valueOf(100.0));

        mockMvc.perform(post("/fund").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string("100.0"));
    }

    @Test
    void testBuyEquity() throws Exception {
        EquityRequest equityRequest = new EquityRequest(){{
            setEquityId(1);
            setTraderId(1);
            setUnits(10);
        }};
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(equityRequest);

        if (TradeUtil.validateTime(LocalDateTime.now())) {
            var equityResponse = new EquityResponse(){{
                setEquityId(1);
                setEquityName("Test");
                setTotalHoldings(100);
                setUnitPrice(5.00);
                setMessage("OK");
            }};

            when(tradeService.buyEquity(any(EquityRequest.class))).thenReturn(equityResponse);

            mockMvc.perform(post("/buy").content(json).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(equityResponse)));

        } else {
            var equityResponse = new EquityResponse(){{;
                setMessage(TradeController.TIME_RANGE_MESSAGE);
            }};
            mockMvc.perform(post("/buy").content(json).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError()).andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(equityResponse)));

        }
    }

    @Test
    void testSellEquity() throws Exception {
        EquityRequest equityRequest = new EquityRequest(){{
            setEquityId(1);
            setTraderId(1);
            setUnits(10);
        }};
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(equityRequest);
        if (TradeUtil.validateTime(LocalDateTime.now())) {
            var equityResponse = new EquityResponse(){{
                setEquityId(1);
                setEquityName("Test");
                setTotalHoldings(100);
                setUnitPrice(5.00);
                setMessage("OK");
            }};

            when(tradeService.sellEquity(any(EquityRequest.class))).thenReturn(equityResponse);

            mockMvc.perform(post("/sell").content(json).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(equityResponse)));

        } else {
            var equityResponse = new EquityResponse(){{;
                setMessage(TradeController.TIME_RANGE_MESSAGE);
            }};
            mockMvc.perform(post("/sell").content(json).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError()).andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(equityResponse)));

        }
    }

    @Test
    void testBuyEquityOnTime() throws Exception {
        EquityRequest equityRequest = new EquityRequest() {{
            setEquityId(1);
            setTraderId(1);
            setUnits(10);
        }};
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(equityRequest);
        try (MockedStatic<TradeUtil> utilities = Mockito.mockStatic(TradeUtil.class)) {
            utilities.when(() -> TradeUtil.validateTime(any(LocalDateTime.class))).thenReturn(true);


            var equityResponse = new EquityResponse() {{
                setEquityId(1);
                setEquityName("Test");
                setTotalHoldings(100);
                setUnitPrice(5.00);
                setMessage("OK");
            }};

            when(tradeService.buyEquity(any(EquityRequest.class))).thenReturn(equityResponse);

            mockMvc.perform(post("/buy").content(json).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(equityResponse)));

        }
    }

    @Test
    void testBuyEquityOutsideTime() throws Exception {
        EquityRequest equityRequest = new EquityRequest() {{
            setEquityId(1);
            setTraderId(1);
            setUnits(10);
        }};
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(equityRequest);
        try (MockedStatic<TradeUtil> utilities = Mockito.mockStatic(TradeUtil.class)) {
            utilities.when(() -> TradeUtil.validateTime(any(LocalDateTime.class))).thenReturn(false);


            var equityResponse = new EquityResponse(){{;
                setMessage(TradeController.TIME_RANGE_MESSAGE);
            }};
            mockMvc.perform(post("/buy").content(json).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError()).andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(equityResponse)));

        }
    }


    @Test
    void testSellEquityOnTime() throws Exception {
        EquityRequest equityRequest = new EquityRequest() {{
            setEquityId(1);
            setTraderId(1);
            setUnits(10);
        }};
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(equityRequest);
        try (MockedStatic<TradeUtil> utilities = Mockito.mockStatic(TradeUtil.class)) {
            utilities.when(() -> TradeUtil.validateTime(any(LocalDateTime.class))).thenReturn(true);


            var equityResponse = new EquityResponse() {{
                setEquityId(1);
                setEquityName("Test");
                setTotalHoldings(100);
                setUnitPrice(5.00);
                setMessage("OK");
            }};

            when(tradeService.sellEquity(any(EquityRequest.class))).thenReturn(equityResponse);

            mockMvc.perform(post("/sell").content(json).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(equityResponse)));

        }
    }

    @Test
    void testSellEquityOutsideTime() throws Exception {
        EquityRequest equityRequest = new EquityRequest() {{
            setEquityId(1);
            setTraderId(1);
            setUnits(10);
        }};
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(equityRequest);
        try (MockedStatic<TradeUtil> utilities = Mockito.mockStatic(TradeUtil.class)) {
            utilities.when(() -> TradeUtil.validateTime(any(LocalDateTime.class))).thenReturn(false);


            var equityResponse = new EquityResponse(){{;
                setMessage(TradeController.TIME_RANGE_MESSAGE);
            }};
            mockMvc.perform(post("/sell").content(json).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError()).andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(equityResponse)));

        }
    }



    @Test
    public void testRecordNotFoundException(){
           ResponseEntity response = tradeController.handleRecordNotFoundException(new RecordNotFoundException());
           Assertions.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testFundUnavailableException(){
        ResponseEntity response = tradeController.handleFundUnavailableException(new FundUnavailableException());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }


}
