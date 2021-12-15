package gabriel.games.service;

import gabriel.games.model.dto.ErrorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ErrorServiceTest {

    private ErrorService errorService;
    private Errors errors;

    @BeforeEach
    public void setUp() {
        this.errorService = new ErrorService();
        this.errors = mock(Errors.class);
    }

    @Test
    public void makeErrors_FieldErrorsGiven_ShouldReturnListOfFieldErrors() {
        List<ErrorDto> expectedErrorDtos = makeExpectedErrorDtos(ErrorDto.Type.FIELD_ERROR, 2);
        stubFieldErrors(expectedErrorDtos);
        List<ErrorDto> actualErrorDtos = errorService.toErrorDtos(errors);

        assertEquals(expectedErrorDtos, actualErrorDtos);
    }

    private List<ErrorDto> makeExpectedErrorDtos(ErrorDto.Type type, int count) {
        List<ErrorDto> errorDtos = new ArrayList<>();
        while(count > 0) {
            errorDtos.add(new ErrorDto(type, "name" + count, "message" + count));
            count--;
        }
        return errorDtos;
    }

    private void stubFieldErrors(List<ErrorDto> errorDtos) {
        List<FieldError> fieldErrors = makeFieldErrors(errorDtos);
        when(errors.getFieldErrors()).thenReturn(fieldErrors);
    }

    private List<FieldError> makeFieldErrors(List<ErrorDto> errorDtos) {
        List<FieldError> fieldErrors = new ArrayList<>();
        errorDtos.forEach(
                error -> fieldErrors.add(new FieldError("objectName", error.getName(), error.getMessage()))
        );
        return fieldErrors;
    }

    @Test
    public void makeErrors_ObjectErrorsGiven_ShouldReturnListOfObjectErrors() {
        List<ErrorDto> expectedErrorDtos = makeExpectedErrorDtos(ErrorDto.Type.OBJECT_ERROR, 2);
        stubObjectErrors(expectedErrorDtos);
        List<ErrorDto> actualErrorDtos = errorService.toErrorDtos(errors);

        assertEquals(expectedErrorDtos, actualErrorDtos);
    }

    private void stubObjectErrors(List<ErrorDto> errorDtos) {
        List<ObjectError> objectErrors = makeObjectErrors(errorDtos);
        when(errors.getGlobalErrors()).thenReturn(objectErrors);
    }

    private List<ObjectError> makeObjectErrors(List<ErrorDto> errorDtos) {
        List<ObjectError> objectErrors = new ArrayList<>();
        for (ErrorDto error : errorDtos) {
            objectErrors.add(new ObjectError(error.getName(), error.getMessage()));
        }
        return objectErrors;
    }

    @Test
    public void makeErrors_FieldAndObjectErrorsGiven_ShouldReturnListOfObjectErrors() {
        List<ErrorDto> fieldDtos = makeExpectedErrorDtos(ErrorDto.Type.FIELD_ERROR, 3);
        List<ErrorDto> objectDtos = makeExpectedErrorDtos(ErrorDto.Type.OBJECT_ERROR, 2);
        stubFieldErrors(fieldDtos);
        stubObjectErrors(objectDtos);
        List<ErrorDto> expectedErrorDtos = combineLists(fieldDtos, objectDtos);
        List<ErrorDto> actualErrorDtos = errorService.toErrorDtos(errors);

        assertEquals(expectedErrorDtos, actualErrorDtos);
    }

    private List<ErrorDto> combineLists(List<ErrorDto> first, List<ErrorDto> second) {
        List<ErrorDto> combined = new ArrayList<>();
        combined.addAll(first);
        combined.addAll(second);
        return combined;
    }
}
