package se.manage.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import se.manage.ManageStatus;
import se.manage.Sessionable;
import se.manage.user.PassMatcher;
import se.manage.user.User;
import se.manage.user.UserService;
import se.manage.user.UserValidator;

import javax.validation.Valid;

@Controller
public class UserController implements Sessionable {
    @Autowired
    UserService userService;
    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    PassMatcher passMatcher;

    @InitBinder({"user"})
    public void initBinder(DataBinder binder) {
        binder.addValidators(new UserValidator());
    }

    @RequestMapping(value = {"/login.do"}, method = {RequestMethod.POST})
    public String login(@Valid @ModelAttribute("LogginFlag") String LogginFlag, BindingResult result,
                        @ModelAttribute(Sessionable.Status) ManageStatus status, Model model,
                        @RequestParam("account") String account,
                        @RequestParam("password") String password) {
        if (result.hasErrors()) {
            this.logger.info(result);
            status.status = 2;
            return "redirect:/login.html";
        } else {
            //User temp = this.userService.login(user);
            if (account.equals("root") && account.equals("root")){//temp != null) {
                this.logger.info( "login succeeded");
                status.status = 0;
                model.addAttribute(Sessionable.LogginFlag, "true");
                return "redirect:/index.html";
            } else {
                this.logger.info( "login failed");
                status.status = 1;
                return "redirect:/login.html";
            }
        }
    }

    @RequestMapping(value = {"/register.do"}, method = {RequestMethod.POST})
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result,
                           @ModelAttribute(Sessionable.Status) ManageStatus status,
                           @ModelAttribute(Sessionable.account) String account,
                           Model model) {
        if (result.hasErrors()) {
            this.logger.info(result);
            status.status = 2;
            return "redirect:/individual.html";
        } else {
            user.setAccount(account);
            user.setToken(this.userService.generateToken());
            User temp = this.userService.iregister(user);
            if (temp != null) {
                this.logger.info(temp + " register successes");
                status.status = 0;
                model.addAttribute(Sessionable.User, temp);
                return "redirect:/success.html";
            } else {
                this.logger.info(user + " register failed");
                status.status = ManageStatus.identity_aldeady_exist;
                return "redirect:/individual.html";
            }
        }
    }

    @RequestMapping(value = {"/corRegister.do"}, method = {RequestMethod.POST})
    public String corRegister(@Valid @ModelAttribute("user") User user, BindingResult result,
                           @ModelAttribute(Sessionable.Status) ManageStatus status,
                              @ModelAttribute(Sessionable.account) String account, Model model) {
        if (result.hasErrors()) {
            this.logger.info(result);
            status.status = 2;
            return "redirect:/corperation.html";
        } else {
            user.setAccount(account);
            user.setCor();
            User temp = this.userService.cregister(user);
            if (temp != null) {
                this.logger.info(temp + " register successes");
                status.status = 0;
                model.addAttribute(Sessionable.User, temp);
                return "redirect:/success.html";
            } else {
                this.logger.info(user + " register failed");
                status.status = ManageStatus.identity_aldeady_exist;
                return "redirect:/corperation.html";
            }
        }
    }

    @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
    public String logout(//@ModelAttribute(Sessionable.User) String user, SessionStatus status
                         Model model) {
//        status.setComplete();
        model.addAttribute(Sessionable.LogginFlag, "false");
        return "redirect:/";
    }




    @RequestMapping(value = "/indiDestroy.do", method = RequestMethod.POST)
    public String indiDestroy(@Valid @ModelAttribute("user") User user, BindingResult result,
                              @ModelAttribute(Sessionable.Status) ManageStatus status, Model model,
                              @RequestParam("identity") String identity) {
        int tmp=this.userService.idestroy(identity);
        if(tmp==2)
            return "redirect:/success.html";
        else if(tmp==1){//没卖完
            status.status = 1;
            return "redirect:/indiDestroy.html";
        }else {
            status.status = 2;
            return "redirect:/indiDestroy.html";
        }

    }

    @RequestMapping(value = "/corDestroy.do", method = RequestMethod.POST)
    public String corDestroy(@Valid @ModelAttribute("user") User user, BindingResult result,
                             @ModelAttribute(Sessionable.Status) ManageStatus status, Model model,
                             @RequestParam("identity") String identity) {
        int tmp=this.userService.cdestroy(identity);
        if(tmp==2)
            return "redirect:/success.html";
        else if(tmp==1){//没卖完
            status.status = 1;
            return "redirect:/corDestroy.html";
        }else {
            status.status = 2;
            return "redirect:/corDestroy.html";
        }

    }

    @RequestMapping(value = "/indiLost.do", method = RequestMethod.POST)
    public String indiLost(@Valid @ModelAttribute("user") User user, BindingResult result,
                           @ModelAttribute(Sessionable.Status) ManageStatus status, Model model,
                           @RequestParam("identity") String identity) {
        if(this.userService.iLost(identity))
            return "redirect:/success.html";
        else {
            status.status = 2;
            return "redirect:/indiLost.html";
        }
    }

    @RequestMapping(value = "/corLost.do", method = RequestMethod.POST)
    public String corLost(@Valid @ModelAttribute("user") User user, BindingResult result,
                          @ModelAttribute(Sessionable.Status) ManageStatus status, Model model,
                          @RequestParam("identity") String identity) {
        if(this.userService.cLost(identity))
            return "redirect:/success.html";
        else {
            return "redirect:/index.html";
        }
    }

    @RequestMapping(value = "/indiUnLost.do", method = RequestMethod.POST)
    public String indiUnLost(@Valid @ModelAttribute("user") User user, BindingResult result,
                           @ModelAttribute(Sessionable.Status) ManageStatus status, Model model,
                           @RequestParam("identity") String identity) {
        if(this.userService.iUnLost(identity))
            return "redirect:/success.html";
        else {
            return "redirect:/index.html";
        }
    }

    @RequestMapping(value = "/corUnLost.do", method = RequestMethod.POST)
    public String corUnLost(@Valid @ModelAttribute("user") User user, BindingResult result,
                          @ModelAttribute(Sessionable.Status) ManageStatus status, Model model,
                          @RequestParam("identity") String identity) {
        if(this.userService.cLost(identity))
            return "redirect:/success.html";
        else {
            return "redirect:/index.html";
        }
    }


}
