package ua.kiev.prog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String addClient(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){

        userValidator.setClient(true);
        userValidator.validate(clientDTO, bindingResult);
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("Phone is required");
        }

        clientDTO.setRole(UserRole.USER);
        clientService.create(clientDTO);
        return "register";
    }

    @RequestMapping(value = "/clients/all", method = RequestMethod.GET)
    public String getAllClients(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        List<ClientDTO> clients = clientService.getAll();
        model.addAttribute("clients", clients);
        return "clients_list";
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
        return "redirect:/admin/clients/all";
    }

    @RequestMapping(value = "/admin/clients/delete")
    public String deleteClient(@RequestParam("clientId") long id){
        clientService.delete(id);
        return "redirect:/admin/clients/all";
    }


    //to replace empty fields into nulls for DB
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
