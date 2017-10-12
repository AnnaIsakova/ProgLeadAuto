package ua.kiev.prog;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return ClientDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ClientDTO clientDTO = (ClientDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty");
    }
}

