package ua.kiev.prog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ClientController {

    @Autowired
    ClientService clientService;
    @Autowired
    private UserValidator userValidator;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterPage(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        return "register";
    }

    @RequestMapping(value = "/clients/create", method = RequestMethod.POST)
    public String addClient(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){

        userValidator.setClient(true);
        userValidator.validate(clientDTO, bindingResult);
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("Phone is required");
        }

        clientDTO.setRole(UserRole.USER);
        clientService.create(clientDTO);
        return "redirect:/clients/all";
    }

    @RequestMapping(value = "/clients/all", method = RequestMethod.GET)
    public String getAllClients(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<ClientDTO> clients;
        if (user.getAuthorities().contains(
                new SimpleGrantedAuthority(UserRole.MANAGER.toString()))){
            clients = clientService.getAllClients();
            model.addAttribute("clients", clients);
            return "clients_list_manager";
        } else {
            clients = clientService.getAll();
            int numUsers = 0;
            int numManagers = 0;
            for (ClientDTO client:clients) {
                if (client.getRole() == UserRole.MANAGER){
                    numManagers++;
                } else {
                    numUsers++;
                }
            }
            model.addAttribute("clients", clients);
            model.addAttribute("numUsers", numUsers);
            model.addAttribute("numManagers", numManagers);
//            Map<String, Object> attr = new HashMap<>();
//            attr.put("clients", clients);
//            attr.put("numUsers", numUsers);
//            attr.put("numManagers", numManagers);
//            model.addAllAttributes()
            return "clients_list_admin";
        }
    }

    @RequestMapping(value = "admin/clients/edit", method = RequestMethod.GET)
    public String getEditClientPage(
            @RequestParam("clientId") long id,
            @ModelAttribute("client") ClientDTO clientDTO,
            BindingResult bindingResult,
            Model model){
        clientDTO = clientService.getById(id);
        model.addAttribute("client", clientDTO);
        return "edit_client";
    }

    @RequestMapping(value = "admin/clients/edit", method = RequestMethod.POST)
    public String editClient(
            @ModelAttribute("client") ClientDTO clientDTO,
            BindingResult bindingResult,
            Model model){

        userValidator.setClient(true);
        userValidator.validate(clientDTO, bindingResult);
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("Phone is required");
        }

        clientService.edit(clientDTO);
        return "redirect:/clients/all";
    }

    @RequestMapping(value = "/admin/clients/delete")
    public String deleteClient(@RequestParam("clientId") long id){
        clientService.delete(id);
        return "redirect:/clients/all";
    }


    //to replace empty fields into nulls for DB
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
