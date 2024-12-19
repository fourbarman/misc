package ru.fourbarman;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/*
    EnumPasswordValidator

    1. Validates password.
    2. Checks if password not equals login and equals password confirm.

 */
public class EnumPasswordValidator {
    private static final Pattern LENGTH_PATTERN = Pattern.compile("^.{8,}$");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*[0-9].*");
    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern SPECIAL_CHARACTERS = Pattern.compile(".*[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_{|}~].*");
    private static final Pattern ALLOWED_CHARACTERS = Pattern.compile("^[A-Za-z0-9!\"#$%&'()*+,-./:;<=>?@\\[\\]^_{|}~]+$");
    private static final Predicate<String> NON_EMPTY_PASSWORD_PREDICATE = p -> p != null && !p.isEmpty();
    private static final BiPredicate<String, String> STRINGS_MATCH_BIPREDICATE = String::equals;

    private static final String LENGTH_PATTERN_ERROR = "length < 8";
    private static final String DIGIT_PATTERN_ERROR = "no digits";
    private static final String LOWER_CASE_PATTERN_ERROR = "no lower case";
    private static final String UPPER_CASE_PATTERN_ERROR = "no upper case";
    private static final String SPECIAL_CHARACTERS_ERROR = "no special characters";
    private static final String ALLOWED_CHARACTERS_ERROR = "not allowed characters";
    private static final String PASSWORDS_MISMATCH_ERROR = "password mismatch";
    private static final String EMPTY_PASSWORD_ERROR = "password or confirmation must not be empty";
    private static final String PASSWORD_EQUALS_LOGIN_ERROR = "password must not be equal to login";

    public enum ValidationRules {
        NON_EMPTY(NON_EMPTY_PASSWORD_PREDICATE, EMPTY_PASSWORD_ERROR),
        LENGTH(LENGTH_PATTERN.asPredicate(), LENGTH_PATTERN_ERROR),
        DIGIT(DIGIT_PATTERN.asPredicate(), DIGIT_PATTERN_ERROR),
        LOWER_CASE(LOWER_CASE_PATTERN.asPredicate(), LOWER_CASE_PATTERN_ERROR),
        UPPER_CASE(UPPER_CASE_PATTERN.asPredicate(), UPPER_CASE_PATTERN_ERROR),
        SPECIAL_CHARS(SPECIAL_CHARACTERS.asPredicate(), SPECIAL_CHARACTERS_ERROR),
        ALLOWED_CHARS(ALLOWED_CHARACTERS.asPredicate(), ALLOWED_CHARACTERS_ERROR);

        private final Predicate<String> matcher;
        private final String description;

        ValidationRules(Predicate<String> matcher, String description) {
            this.matcher = matcher;
            this.description = description;
        }

        public String validate(String password) {
            return matcher.test(password) ? null : description;
        }
    }

    public Optional<String> validatePassword(String password) {

        for (ValidationRules rule : ValidationRules.values()) {
            String error = rule.validate(password);
            if (error != null) {
                return Optional.of(error);
            }
        }
        return Optional.empty();
    }

    public Optional<String> validatePassLogin(String password, String login) {
        Optional<String> passwordError = validatePassword(password);
        if (passwordError.isPresent()) {
            return passwordError; // password != null
        }
        if (STRINGS_MATCH_BIPREDICATE.test(password, login)) {
            return Optional.of(PASSWORD_EQUALS_LOGIN_ERROR);
        }
        return Optional.empty();
    }

    public Optional<String> processValidatePassword(String password, String confirmation, String login) {
        Optional<String> validationError = validatePassLogin(password, login);
        if (validationError.isPresent()) {
            return validationError;
        }

        if (!STRINGS_MATCH_BIPREDICATE.test(password, confirmation)) {
            return Optional.of(PASSWORDS_MISMATCH_ERROR);
        }
        return Optional.empty();
    }
}





























