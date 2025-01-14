package sample.spring.transaction.propagation;

import java.math.BigDecimal;

public interface IAccountService {

    void batchTransferMoney(String from, String to, double money, int num);

    void transferMoney(String from, String to, BigDecimal money, boolean throwException);

    void batchTransferMoney1(String from, String to, double money, int num);

    void batchTransferMoneyNoTransactional(String from, String to, double money, int num);
}
