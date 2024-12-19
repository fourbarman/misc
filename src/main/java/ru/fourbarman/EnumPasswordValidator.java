package ru.fourbarman;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/*
    EnumPasswordValidator

    1. Validates password.
    2. Checks if password not equals login and equals password confirm.

 */
public class EnumPasswordValidator {
    public enum ValidationRules {
        NON_EMPTY(p -> p != null && !p.isEmpty(), "password or confirmation must not be empty"),
        LENGTH(Pattern.compile("^.{8,}$").asPredicate(), "length < 8"),
        DIGIT(Pattern.compile("\\d").asPredicate(), "no digits"),
        LOWER_CASE(Pattern.compile("[a-z]").asPredicate(), "no lower case"),
        UPPER_CASE(Pattern.compile("[A-Z]").asPredicate(), "no upper case"),
        SPECIAL_CHARS(Pattern.compile("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_{|}~]").asPredicate(), "no special characters"),
        ALLOWED_CHARS(Pattern.compile("^[A-Za-z0-9!\"#$%&'()*+,-./:;<=>?@\\[\\]^_{|}~]+$").asPredicate(), "not allowed characters");

        private final Predicate<String> condition;
        private final String errorMessage;

        ValidationRules(Predicate<String> condition, String errorMessage) {
            this.condition = condition;
            this.errorMessage = errorMessage;
        }

        public Optional<String> validate(String password) {
            return condition.test(password) ? Optional.empty() : Optional.of(errorMessage);
        }
    }

    public Optional<String> validatePassword(String password) {

        for (ValidationRules rule : ValidationRules.values()) {
            Optional<String> error = rule.validate(password);
            if (error.isPresent()) {
                return error;
            }
        }
        return Optional.empty();
    }

    public Optional<String> processValidatePasswordConfirm(String password, String confirmation, String login) {

        Optional<String> passwordError = validatePassword(password);
        if (passwordError.isPresent()) {
            return passwordError; // password != null
        }

        if (password.equalsIgnoreCase(login)) {
            return Optional.of("password must not be equal to login");
        }

        if (!password.equals(confirmation)) {
            return Optional.of("password mismatch");
        }

        return Optional.empty();
    }
}





























