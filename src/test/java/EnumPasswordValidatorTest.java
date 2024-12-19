import org.junit.jupiter.api.Test;
import ru.fourbarman.EnumPasswordValidator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumPasswordValidatorTest {
    private final EnumPasswordValidator validator = new EnumPasswordValidator();

    @Test
    void validatePassword_NullPassword_ShouldReturnEmptyPasswordError() {
        String password = null;
        Optional<String> result = validator.validatePassword(password);
        assertEquals(Optional.of("password or confirmation must not be empty"), result);
    }

    @Test
    void validatePassword_EmptyPassword_ShouldReturnEmptyPasswordError() {
        String password = "";
        Optional<String> result = validator.validatePassword(password);
        assertEquals(Optional.of("password or confirmation must not be empty"), result);
    }

    @Test
    void processValidatePassword_NullPassword_ShouldReturnEmptyPasswordError() {
        String password = null;
        String confirmation = "Password1!";
        String login = "UserLogin";
        Optional<String> result = validator.processValidatePassword(password, confirmation, login);
        assertEquals(Optional.of("password or confirmation must not be empty"), result);
    }

    @Test
    void processValidatePassword_EmptyPassword_ShouldReturnEmptyPasswordError() {
        String password = "";
        String confirmation = "Password1!";
        String login = "UserLogin";
        Optional<String> result = validator.processValidatePassword(password, confirmation, login);
        assertEquals(Optional.of("password or confirmation must not be empty"), result);
    }

    @Test
    void processValidatePassword_NullConfirmation_ShouldReturnPasswordMismatchError() {
        String password = "Password1!";
        String confirmation = null;
        String login = "UserLogin";
        Optional<String> result = validator.processValidatePassword(password, confirmation, login);
        assertEquals(Optional.of("password mismatch"), result);
    }

    @Test
    void processValidatePassword_EmptyConfirmation_ShouldReturnPasswordMismatchError() {
        String password = "Password1!";
        String confirmation = "";
        String login = "UserLogin";
        Optional<String> result = validator.processValidatePassword(password, confirmation, login);
        assertEquals(Optional.of("password mismatch"), result);
    }

    @Test
    void processValidatePassword_NullPasswordAndConfirmation_ShouldReturnEmptyPasswordError() {
        String password = null;
        String confirmation = null;
        String login = "UserLogin";
        Optional<String> result = validator.processValidatePassword(password, confirmation, login);
        assertEquals(Optional.of("password or confirmation must not be empty"), result);
    }

    @Test
    void processValidatePassword_EmptyPasswordAndConfirmation_ShouldReturnEmptyPasswordError() {
        String password = "";
        String confirmation = "";
        String login = "UserLogin";
        Optional<String> result = validator.processValidatePassword(password, confirmation, login);
        assertEquals(Optional.of("password or confirmation must not be empty"), result);
    }
}
