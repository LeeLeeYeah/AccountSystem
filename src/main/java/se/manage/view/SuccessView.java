package se.manage.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.manage.Sessionable;
import se.manage.user.User;

@Controller
public class SuccessView implements Sessionable {

    @RequestMapping(value = {ManagerView.successPage}, method = {RequestMethod.GET})
    public String indexPage(Model model) {
        model.addAttribute("user", new User());
        return ManagerView.successPage;
    }
}
