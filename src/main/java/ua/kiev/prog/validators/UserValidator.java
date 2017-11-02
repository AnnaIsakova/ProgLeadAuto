package ua.kiev.prog.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.kiev.prog.DTO.ClientDTO;

@Component
public class UserValidator implements Validator {

    private boolean isClient = true;

    @Override
    public boolean supports(Class<?> aClass) {
        return ClientDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ClientDTO clientDTO = (ClientDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty");
        if (!isClient){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        }
    }

    public void setClient(boolean client) {
        isClient = client;
    }
}

