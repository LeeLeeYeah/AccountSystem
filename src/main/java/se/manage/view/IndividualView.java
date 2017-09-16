package se.manage.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.manage.Sessionable;
import se.manage.user.User;
import se.manage.user.UserService;


@Controller
public class IndividualView implements Sessionable {
    @Autowired
    UserService userService;
    @RequestMapping(value = {ManagerView.individualPage}, method = {RequestMethod.GET})
    public String indexPage(Model model) {
        model.addAttribute("account", this.userService.generateAccount());
        model.addAttribute("user", new User());
        return ManagerView.individualPage;
    }
}
