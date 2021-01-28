package ca.jrvs.apps.trading.service;


import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.List;


@org.springframework.stereotype.Service
public class TraderAccountService extends Service {

    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;


    @Autowired
    public TraderAccountService(TraderDao traderDao, AccountDao accountDao, PositionDao positionDao,
                                SecurityOrderDao securityOrderDao){
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    private void validateTrader(Trader trader) throws IllegalArgumentException{
        if(trader == null){
            throw new IllegalArgumentException("trader is null");
        }
        if(trader.getLastName() == null){
            throw new IllegalArgumentException("trader.lastName is null");
        }
        if(trader.getFirstName() == null){
            throw new IllegalArgumentException("trader.firstName is null");
        }
        if(trader.getCountry() == null){
            throw new IllegalArgumentException("trader.country is null");
        }
        if(trader.getEmail() == null){
            throw new IllegalArgumentException("trader.email is null");
        }
        if(trader.getDob() == null) {
            throw new IllegalArgumentException("trader.dob is null");
        }
    }

    public TraderAccountView createTraderAndAccount(Trader trader) throws IllegalArgumentException{
        validateTrader(trader);
        this.traderDao.save(trader);
        Account account = new Account();
        account.setAmount(0d);
        account.setTraderId(trader.getId());
        this.accountDao.save(account);

        return new TraderAccountView(trader, account);
    }

    public TraderAccountView getTraderAndAccount(Integer traderID) throws IllegalArgumentException{
        Trader trader = this.traderDao.findById(traderID)
                .orElseThrow(() -> new IllegalArgumentException("trader id= "+traderID+" not found"));
        Account account = this.accountDao.findByTraderId(traderID)
                .orElseThrow(() -> new IllegalArgumentException("account not found"));
        return new TraderAccountView(trader, account);
    }

    public void deleteTradeById(Integer id) throws IllegalArgumentException{
        if(id == null){
            throw new IllegalArgumentException("id is null");
        }

        Trader trader = this.traderDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("trader id= "+id+" not found"));

        Account account = this.accountDao.findByTraderId(trader.getId())
                .orElseThrow(() -> new IllegalArgumentException("account not found"));

        if(account.getAmount() != 0){
            throw new IllegalArgumentException("account amount is not 0");
        }

        List<Position> positions = this.positionDao.findAllByAccountId(trader.getId());
        if(positions.stream().anyMatch(position -> position.getPosition()>0)){
            throw new IllegalArgumentException("account has some positions");
        }

        List<SecurityOrder> orders = this.securityOrderDao.findAllByAccountId(account.getId());

        // delete
        this.securityOrderDao.deleteAll(orders);
        this.accountDao.delete(account);
        this.traderDao.delete(trader);
    }

    public Account deposit(Integer tradeId, double fund) throws IllegalArgumentException{
        if(tradeId == null || fund <= 0){
            throw new IllegalArgumentException("tradeId should not be null and fund should > 0");
        }
        Account account = accountDao.findByTraderId(tradeId)
                .orElseThrow(()->new DataRetrievalFailureException("no such account"));
        account.depositAmount(fund);
        accountDao.save(account);
        return account;
    }

    public Account widthDraw(Integer tradeId, double fund) throws IllegalArgumentException{
        if(tradeId == null || fund <= 0){
            throw new IllegalArgumentException("tradeId should not be null and fund should > 0");
        }
        Account account = accountDao.findByTraderId(tradeId)
                .orElseThrow(()->new DataRetrievalFailureException("no such trader"));
        if(account.getAmount() < fund){
            throw new IllegalArgumentException("insufficient fund");
        }
        account.withdrawAmount(fund);
        accountDao.save(account);
        return account;
    }

}
