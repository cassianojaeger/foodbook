package br.ufrgs.foodbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("project", "Foodbook");
        return "index";
    }

    @ResponseBody
    @RequestMapping("/user")
    public Principal getLoggedUser(Principal principal) {
        return principal;
    }
}
