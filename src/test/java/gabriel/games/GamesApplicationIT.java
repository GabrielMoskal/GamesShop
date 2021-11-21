package gabriel.games;

import gabriel.games.controller.auth.RegistrationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GamesApplicationIT {

    @Autowired
    private RegistrationController registrationController;

    @Test
    public void contextLoads() {
        assertThat(registrationController).isNotNull();
    }

}
