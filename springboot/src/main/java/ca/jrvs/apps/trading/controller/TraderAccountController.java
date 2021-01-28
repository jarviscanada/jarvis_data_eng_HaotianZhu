package ca.jrvs.apps.trading.controller;


import ca.jrvs.apps.trading.models.Trader;
import ca.jrvs.apps.trading.models.TraderAccountView;
import ca.jrvs.apps.trading.service.TraderAccountService;
import ca.jrvs.apps.trading.utils.HttpResponseExceptionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;



@org.springframework.stereotype.Controller
@RequestMapping("/trader")
public class TraderAccountController extends Controller{

    private TraderAccountService traderAccountService;

    @Autowired
    public TraderAccountController(TraderAccountService traderAccountService) {
        this.traderAccountService = traderAccountService;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(
            path = "/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    public TraderAccountView createTrader(@PathVariable String firstname,
                                          @PathVariable String lastname,
                                          @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob,
                                          @PathVariable String country,
                                          @PathVariable String email){
        try {
            Trader trader = new Trader();
            trader.setEmail(email);
            trader.setLastName(lastname);
            trader.setFirstName(firstname);
            trader.setCountry(country);
            trader.setDob(Date.valueOf(dob));
            return traderAccountService.createTraderAndAccount(trader);
        } catch (Exception e){
            throw HttpResponseExceptionUtils.throwException(e);
        }
    }


    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(
            path = "/",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
    )
    public TraderAccountView createTrader(@RequestBody Trader trader){
        try{
            return traderAccountService.createTraderAndAccount(trader);
        } catch (Exception e){
            throw HttpResponseExceptionUtils.throwException(e);
        }
    }

    @DeleteMapping(path="traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrader(@PathVariable Integer traderId){
        try{
            traderAccountService.deleteTradeById(traderId);
        } catch (Exception e){
            throw HttpResponseExceptionUtils.throwException(e);
        }
    }

    @PutMapping(path="/deposit/traderId/{traderId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public void depositFund(@PathVariable Integer traderId,@PathVariable Double amount){
        try{
            traderAccountService.deposit(traderId,amount);
        } catch (Exception e){
            throw HttpResponseExceptionUtils.throwException(e);
        }
    }


    @PutMapping(path="/withdraw/traderId/{traderId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public void withdrawFund(@PathVariable Integer traderId,@PathVariable Double amount){
        try{
            traderAccountService.widthDraw(traderId,amount);
        } catch (Exception e){
            throw HttpResponseExceptionUtils.throwException(e);
        }
    }
}
