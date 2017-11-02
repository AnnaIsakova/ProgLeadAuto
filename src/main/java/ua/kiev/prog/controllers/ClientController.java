package ua.kiev.prog.controllers;


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
import ua.kiev.prog.DTO.ClientDTO;
import ua.kiev.prog.entities.UserRole;
import ua.kiev.prog.validators.UserValidator;
import ua.kiev.prog.services.ClientService;

import java.util.List;


@Controller
public class ClientController {

    @Autowired
    ClientService clientService;
    @Autowired
    private UserValidator userValidator;


    @RequestMapping(value = "/clients/create", method = RequestMethod.GET)
    public String getRegisterPage(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        return "register_client_modal :: addUserModal";
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
        addNameToModel(user, model);

        List<ClientDTO> clients;
        if (user.getAuthorities().contains(
                new SimpleGrantedAuthority(UserRole.MANAGER.toString()))){
            clients = clientService.getAllClients();
            model.addAttribute("clients", clients);
            model.addAttribute("role", "Manager");
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
            model.addAttribute("role", "Admin");
            return "clients_list_admin";
        }
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
    public String getClient(@PathVariable("id") long id,
                            @ModelAttribute("client") ClientDTO clientDTO,
                            BindingResult bindingResult,
                            Model model){
        clientDTO = clientService.getById(id);
        model.addAttribute("client", clientDTO);
        return "delete_client_modal :: deleteUserModal";
    }

    @RequestMapping(value = "admin/clients/edit/{id}", method = RequestMethod.GET)
    public String getEditClientModal(
            @PathVariable("id") long id,
            @ModelAttribute("client") ClientDTO clientDTO,
            BindingResult bindingResult,
            Model model){
        clientDTO = clientService.getById(id);
        model.addAttribute("client", clientDTO);
        return "edit_client_modal :: editUserModal";
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

    private void addNameToModel(User user, Model model){
        ClientDTO loggedInUser = clientService.findByPhone(user.getUsername());
        String name = loggedInUser.getName();
        String surname = loggedInUser.getSurname();
        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
    }
}
