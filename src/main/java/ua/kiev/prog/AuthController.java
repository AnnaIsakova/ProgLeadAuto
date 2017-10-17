package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class AuthController {

    @Autowired
    ClientService clientService;
    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignUpPage(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        return "sign_up";
    }

    @RequestMapping(value = { "/signup"}, method = RequestMethod.POST)
    public String addClient(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){

        System.out.println(clientDTO);

        userValidator.setClient(false);
        userValidator.validate(clientDTO, bindingResult);
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("Phone and password required");
        }

        clientDTO.setRole(UserRole.MANAGER);
        clientService.create(clientDTO);
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
}
