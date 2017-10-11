package ua.kiev.prog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(){
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterPage(){
        return "register";
    }

    @RequestMapping(value = "/sign_up", method = RequestMethod.GET)
    public String getSignUpPage(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ADMIN);
        roles.add(UserRole.MANAGER);
//        model.addAttribute("roles", roles);
        return "sign_up";
    }

    @RequestMapping(value = { "/sign_up" }, method = RequestMethod.POST)
    public String addClient(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        System.out.println(clientDTO);
        Client client = Client.fromDTO(clientDTO);
        //temp workaround
        client.setRole(UserRole.MANAGER);
        clientService.create(client);
        return "redirect:/login";
    }

    //to replace empty fields into nulls for DB
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

//    private boolean isValidClient(ClientDTO clientDTO){
//        if (clientDTO.getPhone() == null || clientDTO.getPhone().isEmpty()) return false;
//    }
}
