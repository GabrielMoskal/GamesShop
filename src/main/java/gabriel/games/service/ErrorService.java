package gabriel.games.service;

import gabriel.games.model.dto.ErrorDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorService {
    public List<ErrorDto> toErrorDtos(Errors errors) {
        List<ErrorDto> errorDtos = new ArrayList<>();
        addFieldErrors(errors, errorDtos);
        addObjectErrors(errors, errorDtos);
        return errorDtos;
    }

    private void addFieldErrors(Errors errors, List<ErrorDto> errorDtos) {
        errors.getFieldErrors().forEach(
                fieldError -> errorDtos.add(
                        new ErrorDto(ErrorDto.Type.FIELD_ERROR, fieldError.getField(), fieldError.getDefaultMessage())
                )
        );
    }

    private void addObjectErrors(Errors errors, List<ErrorDto> errorDtos) {
        errors.getGlobalErrors().forEach(
                objectError -> errorDtos.add(
                        new ErrorDto(ErrorDto.Type.OBJECT_ERROR, objectError.getObjectName(), objectError.getDefaultMessage())
                )
        );
    }
}
