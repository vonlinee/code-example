package code.sample.spring.transaction.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import code.sample.spring.transaction.business.dao.IAccountDao;
import code.sample.spring.transaction.declaratively.annotation.Main;

@Service
public class AccountServiceImpl {
	
    @Autowired
    private IAccountDao accountDao;

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
    
    public void transferMoney(String out, String in, Double money) {
    	transfer(out, in, money);
    }
    
    @Transactional
    public void transfer(String out, String in, Double money) {
        accountDao.outMoney(out, money);
        int i = 1 / 0;  //此处除0模拟转账发生异常
        accountDao.inMoney(in, money);
    }
}