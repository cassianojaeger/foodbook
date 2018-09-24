package br.ufrgs.foodbook;

import br.ufrgs.foodbook.model.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.security.Principal;

@Controller
public class HomeController {

    @Qualifier("userDetailsServiceImpl")
    @Resource
    UserDetailsService userDetailsService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("project", "Foodbook");

        return "index";
    }

    @ResponseBody
    @RequestMapping("/user")
    public User getLoggedUser(Principal principal) {
//        return principal;
        return (User) userDetailsService.loadUserByUsername("foodUser");
    }

}
