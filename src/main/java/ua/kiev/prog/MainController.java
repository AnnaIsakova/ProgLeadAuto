package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class MainController {

    @Autowired
    ClientService clientService;

//
//    @RequestMapping("/")
//    public String index(Model model){
////        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////        String login = user.getUsername();
////
////        ClientDTO dbUser = clientService.findByPhone(login);
////
////        model.addAttribute("login", login);
////        model.addAttribute("roles", user.getAuthorities());
////        model.addAttribute("email", dbUser.getEmail());
////        model.addAttribute("phone", dbUser.getPhone());
//
//        return "index";
//    }

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
        System.out.println(clientDTO);
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
