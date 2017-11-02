package ua.kiev.prog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.kiev.prog.DTO.ClientDTO;
import ua.kiev.prog.entities.UserRole;
import ua.kiev.prog.validators.UserValidator;
import ua.kiev.prog.services.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class AuthController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignUpPage(@ModelAttribute("client") ClientDTO clientDTO, BindingResult bindingResult, Model model){
        return "sign_up";
    }

    @RequestMapping(value = { "/signup"}, method = RequestMethod.POST)
    public String addClient(@ModelAttribute("client") ClientDTO clientDTO,
                            BindingResult bindingResult,
                            Model model,
                            HttpServletRequest request){

        userValidator.setClient(false);
        userValidator.validate(clientDTO, bindingResult);
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("Phone and password required");
        }

        String passFromForm = clientDTO.getPassword();
        clientDTO.setRole(UserRole.MANAGER);
        clientService.create(clientDTO);

        authenticateUserAndSetSession(clientDTO, passFromForm, request);
        return "redirect:/clients/all";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    private void authenticateUserAndSetSession(ClientDTO user, String passFromForm, HttpServletRequest request) {

        String username = user.getPhone();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, passFromForm, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));

        Authentication authenticatedUser = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            System.out.println("Line Authentication 4");

        }

        HttpSession session = request.getSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());// creates context for that session.
        session.setAttribute("username", user.getPhone());
        session.setAttribute("authorities", usernamePasswordAuthenticationToken.getAuthorities());
    }
}
