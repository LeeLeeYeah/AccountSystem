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
public class CorperationView implements Sessionable {
    @Autowired
    UserService userService;
    @RequestMapping(value = {ManagerView.corperationPage}, method = {RequestMethod.GET})
    public String indexPage(Model model) {
        model.addAttribute("account", this.userService.generateAccount());
        User u=new User();
        u.setCor();
        model.addAttribute("user", u);
        return ManagerView.corperationPage;
    }
}
