package se.manage.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.manage.Sessionable;

@Controller
public class IndiLostView implements Sessionable {

    @RequestMapping(value = {ManagerView.indiLostPage}, method = {RequestMethod.GET})
    public String indexPage(Model model) {
        //model.addAttribute("user", "true");
        return ManagerView.indiLostPage;
    }
}
