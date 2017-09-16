package se.manage.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.manage.Sessionable;

@Controller
public class ManagerView implements Sessionable {
    public static final String errorPage = "error.html";
    public static final String indexPage = "index.html";
    public static final String loginPage = "login.html";


    public static final String individualPage = "individual.html";
    public static final String corperationPage = "corperation.html";
    public static final String successPage = "success.html";
    public static final String indiDestroyPage = "indiDestroy.html";
    public static final String corDestroyPage = "corDestroy.html";
    public static final String indiLostPage = "indiLost.html";
    public static final String corLostPage = "corLost.html";
    public static final String indiUnLostPage = "indiUnLost.html";
    public static final String corUnLostPage = "corUnLost.html";
    public static final String retrievePage = "retrieve.html";
    public static final String securityQuestionPage = "securityQuestion.html";
    public static final String retrieveByIDPage = "retrieveByID.html";
    public static final String resetPasswordPage = "resetPassword.html";

    @RequestMapping(value = {"/error"}, method = {RequestMethod.GET})
    public String error() {
        return errorPage;
    }
}
