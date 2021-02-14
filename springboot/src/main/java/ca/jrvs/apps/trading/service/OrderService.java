package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.dao.*;
import ca.jrvs.apps.trading.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;


    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao, QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }


    private void validMarketOrder(MarketOrderDto marketOrderDto) throws IllegalArgumentException{
        if(marketOrderDto.getSize() == 0){
            throw new IllegalArgumentException("size should be non-zero");
        }
        if(!this.quoteDao.existsById(marketOrderDto.getTicker())){
            throw new IllegalArgumentException("ticker does not exist");
        }
        if(!this.accountDao.existsById(marketOrderDto.getAccountId())){
            throw new IllegalArgumentException("account does not exist");
        }
    }

    private void buyerStockHelper(Account account, SecurityOrder order){
        if(account.getAmount() < order.getPrice()*order.getSize()){
            throw new IllegalArgumentException("Account doesn't have enough amount");
        }
        account.withdrawAmount(Math.abs(order.getPrice()*order.getSize()));
        order.setStatus("FILLED");
        accountDao.save(account);
        securityOrderDao.save(order);
    }

    private void sellerStockHelper(Account account, SecurityOrder order, Position position){
        if(position.getPosition() < order.getSize()){
            throw new IllegalArgumentException("don't have enough position");
        }
        account.depositAmount(Math.abs(order.getPrice()*order.getSize()));
        accountDao.save(account);
        order.setStatus("FILLED");
        securityOrderDao.save(order);
    }

    public SecurityOrder executeMarketOrder(MarketOrderDto marketOrderDto) throws IllegalArgumentException{
        validMarketOrder(marketOrderDto);
        Boolean isBuyer = marketOrderDto.getSize() > 0;

        Quote quote = quoteDao.findById(marketOrderDto.getTicker())
                .orElseThrow(()->new IllegalArgumentException("quote does not exist"));
        Account account = accountDao.findById(marketOrderDto.getAccountId())
                .orElseThrow(()->new IllegalArgumentException("account does not exist"));

        SecurityOrder order = new SecurityOrder();
        order.setTicker(marketOrderDto.getTicker());
        order.setAccountId(marketOrderDto.getAccountId());
        order.setStatus("processing");
        order.setSize(marketOrderDto.getSize());
        order.setNotes(marketOrderDto.getNote());

        if(isBuyer){
            order.setPrice(quote.getAskPrice());
            securityOrderDao.save(order);
            buyerStockHelper(account, order);
        } else {
            order.setPrice(quote.getBidPrice());
            securityOrderDao.save(order);
            Position position = positionDao.findAllByAccountIdAndTicker(account.getId(), quote.getTicker());
            sellerStockHelper(account, order, position);
        }

        return order;
    }
}
