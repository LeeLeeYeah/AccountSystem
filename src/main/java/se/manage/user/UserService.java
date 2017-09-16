package se.manage.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.manage.controller.HttpRequest;
import se.manage.stock.StockService;
import se.manage.user.User;
import se.manage.user.UserRepository;

@Service("userService")
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    StockService stockService;
    public UserService() {
    }

    /*@Transactional
    public User register(String account, String password, String nickName) {
        User user = new User(account, password, nickName);
        return this.register(user);
    }
*/
    @Transactional
    public User iregister(User user) {
        List<User> users = this.userRepository.findByIdentity(user.getIdentity());
        if (!users.isEmpty()) {
            int n = users.size();
            for (int i = 0 ; i< n; i++) {
                User u = users.get(i);
                if (u.getCorperationFlag().equals("false")) {
                    return null;
                }
            }
            this.userRepository.save(user);
            return user;
        } else {
            this.userRepository.save(user);
            return user;
        }
    }

    @Transactional
    public User cregister(User user) {
        List<User> users = this.userRepository.findByIdentity(user.getIdentity());
        if (!users.isEmpty()) {
            int n = users.size();
            for (int i = 0 ; i< n; i++) {
                User u = users.get(i);
                if (u.getCorperationFlag().equals("true")) {
                    return null;
                }
            }
            this.userRepository.save(user);
            return user;
        } else {
            this.userRepository.save(user);
            return user;
        }
    }

    @Transactional
    public int idestroy(String identity) {
        List<User> users = this.userRepository.findByIdentity(identity);
        if (!users.isEmpty()) {
            int n = users.size();
            for (int i = 0 ; i< n; i++) {
                User u = users.get(i);
                if (u.getCorperationFlag().equals("false")) {
                    if(this.stockService.noStock(u.getAccount())
                            //&& noMoney(u.getToken())   这个得依赖别的组，为了调试我先注释掉
                            ) {
                        this.userRepository.delete(u);
                        return 2;//成功
                    }else
                        return 1;//股票没卖完
                }
            }
        }
        return 0; //身份证未开户
    }

    @Transactional
    public int cdestroy(String identity) {
        List<User> users = this.userRepository.findByIdentity(identity);
        if (!users.isEmpty()) {
            int n = users.size();
            for (int i = 0 ; i< n; i++) {
                User u = users.get(i);
                if (u.getCorperationFlag().equals("true")
                    //&& noMoney(u.getToken())   这个得依赖别的组，为了调试我先注释掉
                        ) {
                    if(this.stockService.noStock(u.getAccount())) {
                        this.userRepository.delete(u);
                        return 2;//成功
                    }else
                        return 1;//股票没卖完
                }
            }
        }
        return 0; //身份证未开户
    }

    @Transactional
    public boolean iLost(String identity) {
        List<User> users = this.userRepository.findByIdentity(identity);
        if (!users.isEmpty()) {
            int n = users.size();
            for (int i = 0 ; i< n; i++) {
                User u = users.get(i);
                if (u.getCorperationFlag().equals("false")) {
                    users.get(i).freeze();
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    public boolean iUnLost(String identity) {
        List<User> users = this.userRepository.findByIdentity(identity);
        if (!users.isEmpty()) {
            int n = users.size();
            for (int i = 0 ; i< n; i++) {
                User u = users.get(i);
                if (u.getCorperationFlag().equals("false")) {
                    users.get(i).unFreeze();
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    public boolean cLost(String identity) {
        List<User> users = this.userRepository.findByIdentity(identity);
        if (!users.isEmpty()) {
            int n = users.size();
            for (int i = 0 ; i< n; i++) {
                User u = users.get(i);
                if (u.getCorperationFlag().equals("true")) {
                    users.get(i).freeze();
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    public boolean cUnLost(String identity) {
        List<User> users = this.userRepository.findByIdentity(identity);
        if (!users.isEmpty()) {
            int n = users.size();
            for (int i = 0 ; i< n; i++) {
                User u = users.get(i);
                if (u.getCorperationFlag().equals("true")) {
                    users.get(i).unFreeze();
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    public User login(User user) {
        List<User> users = this.userRepository.findByAccount(user.getAccount());
        if (!users.isEmpty() && Objects.equals(user.getPassword(), users.get(0).getPassword()))
            return users.get(0);
        else
            return null;
    }

    @Transactional
    public User check(String account, String password) {
        List<User> users = this.userRepository.findByAccount(account);
        if (!users.isEmpty() && Objects.equals(password, users.get(0).getPassword()))
            return users.get(0);
        else
            return null;
    }

    @Transactional
    public User findByToken(String token) {
        List<User> users = this.userRepository.findByToken(token);
        if (!users.isEmpty())
            return users.get(0);
        else
            return null;
    }

    @Transactional
    public boolean modify(String account, String orgPasswd, String newPasswd) {
        List<User> users = this.userRepository.findByAccount(account);
        if (!users.isEmpty())
            if(users.get(0).getPassword().equals(orgPasswd)){
                users.get(0).setPassword(newPasswd);
                return true;
            }
        return false;
    }

    @Transactional
    public boolean freezeByToken(String token) {
        List<User> users = this.userRepository.findByToken(token);
        if (!users.isEmpty()) {
            users.get(0).freeze();
            return true;
        }
        return false;
    }

    @Transactional
    public boolean unfreezeByToken(String token) {
        List<User> users = this.userRepository.findByToken(token);
        if (!users.isEmpty()) {
            users.get(0).unFreeze();
            return true;
        }
        return false;
    }

    @Transactional
    public void save(User user) {
        this.userRepository.save(user);
    }

    @Transactional
    public String generateAccount() {
        int x;
        do {
            x = 1000000 + (int) (Math.random() * 8000000);
        }while (this.userRepository.findByAccount(""+x)==null);
        return ""+x;
    }

    @Transactional
    public String generateToken() {
        int x;
        do {
            x = 100000000  + (int) (Math.random() * 800000000);
        }while (this.userRepository.findByToken(""+x)==null);
        return ""+x;
    }


    @Transactional
    public List<User> getAllIndi() {
        List<User> temp;
        List<User> indiUsers;
        List<User> corUsers;
        indiUsers = new ArrayList<>();
        temp = this.userRepository.findByCorperationFlag("false");
        if (!temp.isEmpty()) {
            int n = temp.size();
            for (int i = 0 ; i< n && i<10; i++) {
                User u = temp.get(i);
                indiUsers.add(u);
            }
        }
        return indiUsers;
    }

    @Transactional
    public List<User> getAllCor() {
        List<User> temp;
        List<User> corUsers;
        corUsers = new ArrayList<>();
        corUsers = new ArrayList<>();
        temp = this.userRepository.findByCorperationFlag("true");
        if (!temp.isEmpty()) {
            int n = temp.size();
            for (int i = 0 ; i< n && i<10; i++) {
                User u = temp.get(i);
                corUsers.add(u);
            }
        }
        return corUsers;
    }


    private boolean noMoney(String token){
        String sr= HttpRequest.sendPost("http://localhost:????/capital/check", "token="+token);//2	Capital account business subsystem地址待填写
        int tmp1 = sr.indexOf("\"frozen\"");
        int tmp2 = sr.indexOf("\"unfrozen\"");
        return sr.charAt(tmp1 + 10) == '0' && sr.charAt(tmp1 + 12) == '0';
    }


}
