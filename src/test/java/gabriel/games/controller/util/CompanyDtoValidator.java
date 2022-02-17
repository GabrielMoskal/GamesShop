package gabriel.games.controller.util;

import gabriel.games.model.api.dto.CompanyDto;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

public class CompanyDtoValidator {

    private JsonValidator jsonValidator;
    private CompanyDto expected;

    public void validate(ResultActions resultActions, CompanyDto expected, String path) {
        setMembers(resultActions, expected);

        validateCompanyDto();
        validateTypes();
        jsonValidator.validateJsonLinks(path);
    }

    private void setMembers(ResultActions resultActions, CompanyDto expected) {
        this.jsonValidator = new JsonValidator(resultActions);
        this.expected = expected;
    }

    private void validateCompanyDto() {
        jsonValidator.expect("name", expected.getName());
    }

    private void validateTypes() {
        List<String> types = expected.getTypes();
        types.forEach((type) -> {
            String typePath = "types[" + types.indexOf(type) + "]";
            jsonValidator.expect(typePath, type);
        });
    }
}
