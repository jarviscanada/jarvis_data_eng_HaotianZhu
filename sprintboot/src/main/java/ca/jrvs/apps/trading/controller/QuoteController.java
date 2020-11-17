package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.models.IexQuote;
import ca.jrvs.apps.trading.models.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.utils.HttpResponseExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.stereotype.Controller
@RequestMapping("/quote")
public class QuoteController extends Controller {

    private QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping(path = "/iex/ticker/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote getQuote(@PathVariable String ticker){
        try{
            return quoteService.findIexQuoteByTicker(ticker);
        } catch (Exception e){
            throw HttpResponseExceptionUtils.throwException(e);
        }
    }

    @PutMapping(path = "/iexMarketData")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> updateMarketData(){
        try{
            return quoteService.updateMarketData();
        }catch (Exception e){
            throw  HttpResponseExceptionUtils.throwException(e);
        }
    }

    @PutMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Quote putQuote(@RequestBody Quote quote){
        try{
            return quoteService.saveQuote(quote);
        }catch (Exception e){
            throw  HttpResponseExceptionUtils.throwException(e);
        }
    }


    @PostMapping(path = "/tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Quote createQuote(@PathVariable String tickerId){
        try{
            return quoteService.saveQuote(tickerId);
        }catch (Exception e){
            throw  HttpResponseExceptionUtils.throwException(e);
        }
    }

    @GetMapping(path = "/dailyList")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getDailyList(){
        try{
            return quoteService.findQuotes();
        }catch (Exception e){
            throw  HttpResponseExceptionUtils.throwException(e);
        }
    }


}
