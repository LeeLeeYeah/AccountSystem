package se.manage.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.manage.ManageStatus;
import se.manage.Sessionable;
import se.manage.user.User;

@Controller
public class LoginView implements Sessionable {

    @RequestMapping(value = {ManagerView.loginPage}, method = {RequestMethod.GET})
    public String page(@ModelAttribute(Sessionable.Status) ManageStatus status, Model model) {
        model.addAttribute(Sessionable.LogginFlag, "false");
        return ManagerView.loginPage;
    }

    @RequestMapping(value = {"/"}, method = {RequestMethod.GET})
    public String page(Model model) {
        model.addAttribute(Sessionable.LogginFlag, "false");
        return ManagerView.loginPage;
    }
}
