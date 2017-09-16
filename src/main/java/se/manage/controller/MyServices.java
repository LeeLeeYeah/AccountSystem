package se.manage.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.manage.Sessionable;
import se.manage.stock.StockRepository;
import se.manage.stock.StockService;
import se.manage.stock.UserStock;
import se.manage.user.User;
import se.manage.user.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/5/18.
 */
@RestController
public class MyServices {
    @Autowired
    UserService userService;
    @Autowired
    StockService stockService;
    @Autowired
    StockRepository stockRepository;

    public class state_Token{
        String state="";
        String token="";
        public String getstate(){return this.state;}
        public String gettoken(){return this.token;}

        public state_Token(String state, String token){this.state=state;this.token=token;}
    }

    public class state_Info{
        String state="";
        String info="";
        public String getstate(){return this.state;}
        public String getinfo(){return this.info;}
        public state_Info(String state, String info){this.state=state;this.info=info;}
    }

    public class state_Account{
        String state="";
        String account="";
        public String getstate(){return this.state;}
        public String getaccount(){return this.account;}
        public state_Account(String state, String account){this.state=state;this.account=account;}
    }

    public class state{
        String state="";
        public String getstate(){return this.state;}
        public state(String state){this.state=state;}
    }

    public class Stock{
        String code="";
        String amount="";
        public String getcode(){return this.code;}
        public String getamount(){return this.amount;}
        public Stock(String code, String amount){this.code=code; this.amount=amount;}
    }


    public class state_Stocks{
        String state="";
        List<Stock> stock;
        public String getstate(){return this.state;}
        public List<Stock>getstock(){return this.stock;}
        public state_Stocks(String state, List<Stock> stocks){this.state=state;this.stock=stocks;}
    }


    @RequestMapping(value = "/stock/login", method = RequestMethod.POST)
    public Object eternalLogin(@RequestParam("account") String account,
                               @RequestParam("password") String password) {
        //User temp = this.userService.login(user);
        User temp = this.userService.check(account, password);
        if (temp != null) {
            if(temp.getFrozenFlag().equals("false"))
                return new state_Token("ok",temp.getToken());
            else
                return new state_Info("error","this account is frozen");
        } else {
            return new state_Info("error","invalid account or password");
        }
    }

    @RequestMapping(value = "/stock/logout", method = RequestMethod.POST)
    public Object eternalLogout(@RequestParam("token") String token) {
        User temp = this.userService.findByToken(token);
        if (temp != null) {
            return new state("ok");
        } else {
            return new state_Info("error","invalid token");
        }
    }

    @RequestMapping(value = "/stock/freeze", method = RequestMethod.POST)
    public Object eternalFreeze(@RequestParam("token") String token) {
        if (this.userService.freezeByToken(token)) {
            return new state("ok");
        } else {
            return new state_Info("error","invalid token");
        }
    }

    @RequestMapping(value = "/stock/defreeze", method = RequestMethod.POST)
    public Object eternalUnfreeze(@RequestParam("token") String token) {
        if (this.userService.unfreezeByToken(token)) {
            return new state("ok");
        } else {
            return new state_Info("error","invalid token");
        }
    }

    @RequestMapping(value = "/stock/increase", method = RequestMethod.POST)
    public Object eternalIncrease(@RequestParam("token") String token,
                                  @RequestParam("code") String code,
                                  @RequestParam("amount") String amount) {
        User temp=this.userService.findByToken(token);

        if (temp!=null) {
            if (temp.getFrozenFlag().equals("false")) {
                if(stockService.adjust(temp.getAccount(), code, amount))
                    return new state("ok");
                else
                    return new state_Info("error","StockNum can't be negative");
            }else
                return new state_Info("error","this account is frozen!");
        } else {
            return new state_Info("error","invalid token");
        }
    }


    @RequestMapping(value = "/stock/decrease", method = RequestMethod.POST)
    public Object eternalDecrease(@RequestParam("token") String token,
                                  @RequestParam("code") String code,
                                  @RequestParam("amount") String amount) {
        User temp=this.userService.findByToken(token);

        if (temp!=null) {
            if (temp.getFrozenFlag().equals("false")) {
                if(stockService.adjust(temp.getAccount(), code, "-"+amount))
                    return new state("ok");
                else
                    return new state_Info("error","StockNum can't be negative");
            }else
                return new state_Info("error","this account is frozen!!!!!");
        } else {
            return new state_Info("error","invalid token");
        }
    }

    @RequestMapping(value = "/stock/check", method = RequestMethod.POST)
    public Object eternalCheck(@RequestParam("token") String token,
                               @RequestParam("code") String code) {
        User temp=this.userService.findByToken(token);
        if (temp!=null) {
            if (temp.getFrozenFlag().equals("false")) {
                return new state_Info("ok",stockService.check(temp.getAccount(),code));
            }else
                return new state_Info("error","this account is frozen!!!!!");
        } else {
            return new state_Info("error","invalid token");
        }
    }

    @RequestMapping(value = "/stock/account", method = RequestMethod.POST)
    public Object eternalAccount(@RequestParam("token") String token) {
        User temp=this.userService.findByToken(token);
        if (temp!=null) {
            return new state_Account("ok",temp.getAccount());
        } else {
            return new state_Info("error","invalid token");
        }
    }

    @RequestMapping(value = "/stock/modify", method = RequestMethod.POST)
    public Object eternalModify(@RequestParam("token") String token,
                                @RequestParam("orgPasswd") String orgPasswd,
                                @RequestParam("newPasswd") String newPasswd) {
        User temp=this.userService.findByToken(token);
        if (temp!=null) {
            if(this.userService.modify(temp.getAccount(),orgPasswd,newPasswd))
                return new state("ok");
            else
                return new state_Info("error", "wrong password");
        } else {
            return new state_Info("error","invalid token");
        }
    }



    @RequestMapping(value = "/stock/query", method = RequestMethod.POST)
    public Object eternalQuery(@RequestParam("token") String token) {
        User temp=this.userService.findByToken(token);
        if (temp!=null) {
            List<UserStock> records= this.stockRepository.findByAccount(temp.getAccount());
            List<Stock> stocks=new ArrayList<>();
            if (!records.isEmpty()) {
                int n = records.size();
                for (int i = 0 ; i< n; i++) {
                    UserStock r = records.get(i);
                    String name=fetchName(r.getCode());
                    stocks.add(new Stock(r.getCode(),stockService.check(temp.getAccount(),r.getCode())));//最后改的地方
                }
            }
            return new state_Stocks("ok",stocks);
        } else {
            return new state_Info("error","invalid token");
        }
    }


    private String fetchName(String code){
        return "search it by yourself!";
    }


}
