package pre.chl.mypetstore.service;

import pre.chl.mypetstore.domain.Account;

public interface AccountService {
    Account getAccount(String username);
    Account getAccount(String username, String password);
    void insertAccount(Account account);
    void updateAccount(Account account);
}
