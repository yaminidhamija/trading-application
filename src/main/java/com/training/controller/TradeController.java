package com.training.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradeController {

    public static final String TIME_RANGE_MESSAGE = "Equity Sell/Purchase is only allowed on Mon-Fri between 9 AM to 5 PM";

//    @Autowired
//    private TradeService tradeService;
//
//    @PostMapping("/fund")
//    public BigDecimal addFund(@RequestBody Trader trader){
//        BigDecimal updatedBalance = tradeService.addFund(trader);
//        return updatedBalance;
//    }
//
//    @PostMapping("/buy")
//    public ResponseEntity buyEquity(@RequestBody EquityRequest equityRequest){
//        boolean isBuyOperationAllowed = TradeUtil.validateTime(LocalDateTime.now());
//        EquityResponse response = new EquityResponse();
//        if(isBuyOperationAllowed){
//            response = tradeService.buyEquity(equityRequest);
//            response.setMessage("OK");
//            return new ResponseEntity(response, HttpStatus.OK);
//        }else{
//            response.setMessage(TIME_RANGE_MESSAGE);
//            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/sell")
//    public ResponseEntity sellEquity(@RequestBody EquityRequest equityRequest) throws Exception {
//        boolean isSellOperationAllowed = TradeUtil.validateTime(LocalDateTime.now());
//        EquityResponse response = new EquityResponse();
//        if(isSellOperationAllowed){
//           response = tradeService.sellEquity(equityRequest);
//           response.setMessage("OK");
//            return new ResponseEntity(response, HttpStatus.OK);
//        }else{
//            response.setMessage(TIME_RANGE_MESSAGE);
//            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
//        }
//
//
//    }
//
//    @ExceptionHandler(value = RecordNotFoundException.class)
//    public ResponseEntity handleRecordNotFoundException(RecordNotFoundException recordNotFoundException){
//        return new ResponseEntity(recordNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(value = FundUnavailableException.class)
//    public ResponseEntity handleFundUnavailableException(FundUnavailableException fundUnavailableException){
//        return new ResponseEntity(fundUnavailableException.getMessage(), HttpStatus.BAD_REQUEST);
//    }


    @GetMapping("/greetings")
    public String message() {
        return "Welcome to javatechie ! app deployed in AWS EKS";
    }

}
