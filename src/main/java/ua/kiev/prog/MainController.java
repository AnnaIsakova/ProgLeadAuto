package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class MainController {

    @Autowired
    ClientService clientService;
    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/sign_up", method = RequestMethod.GET)
    public String getSignUpPage(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ADMIN);
        roles.add(UserRole.MANAGER);
//        model.addAttribute("roles", roles);
        return "sign_up";
    }

    @RequestMapping(value = { "/sign_up"}, method = RequestMethod.POST)
    public String addClient(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){

        userValidator.validate(clientDTO, bindingResult);
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("User Not Valid");
        }

        Client client = Client.fromDTO(clientDTO);
        //temp workaround
        client.setRole(UserRole.MANAGER);

        clientService.create(client);
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
