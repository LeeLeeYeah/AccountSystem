package se.manage;

import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes({Sessionable.Status, Sessionable.User, Sessionable.LogginFlag, Sessionable.account})
public interface Sessionable {
    String Status = "curStatus";
    String User = "curUser";
    String LogginFlag = "LogginFlag";
    String account="account";
}
