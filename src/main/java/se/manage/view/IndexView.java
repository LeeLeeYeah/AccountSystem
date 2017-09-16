package se.manage.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.context.WebContext;
import se.manage.Sessionable;
import se.manage.user.User;
import se.manage.user.UserRepository;
import se.manage.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
public class IndexView implements Sessionable {


    @Autowired
    UserService userService;

    @RequestMapping(value = {ManagerView.indexPage}, method = {RequestMethod.GET})
    public String indexPage( Model model) {

        List<User> indiUsers = this.userService.getAllIndi();
        List<User> corUsers= this.userService.getAllCor();


        model.addAttribute("indiUsers", indiUsers);
        model.addAttribute("corUsers", corUsers);
        return ManagerView.indexPage;
    }

}
