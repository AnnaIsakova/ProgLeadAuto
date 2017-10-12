package ua.kiev.prog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Controller
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterPage(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addClient(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        clientDTO.setRole(UserRole.USER);
        Client client = Client.fromDTO(clientDTO);
        System.out.println(client.getPassword());
        clientService.create(client);
        return "register";
    }

    //to replace empty fields into nulls for DB
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
