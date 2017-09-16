
package se.manage.user;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class UserValidator implements Validator {
    public static final String errorMessage = "Character is not valid";
    private Logger logger = Logger.getLogger(UserValidator.class);

    public UserValidator() {
    }

    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        /*User user = (User) target;
        Pattern pattern = Pattern.compile("[a-zA-Z0-9_]*");
        this.logger.info(user);
        if (!pattern.matcher(user.getAccount()).matches()) {
            errors.rejectValue("account", null, errorMessage);
        }

        if (!pattern.matcher(user.getPassword()).matches()) {
            errors.rejectValue("password", null, errorMessage);
        }*/

/*
        if (!pattern.matcher(user.getFullName()).matches()) {
            errors.rejectValue("fullName", null, errorMessage);
        }*/

    }
}
