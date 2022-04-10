package gabriel.games.service;

import gabriel.games.model.api.Company;
import gabriel.games.repository.CompanyRepository;
import gabriel.games.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompanyServiceTest {

    private CompanyService service;
    private CompanyRepository repository;

    @BeforeEach
    public void setUp() {
        repository = mock(CompanyRepository.class);
        service = new CompanyService(repository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test_name_1", "test_name_2"})
    public void findByName_ValidNameGiven_ShouldReturnExistingCompany(String input) {
        Company expected = mock(Company.class);
        when(repository.findByName(input)).thenReturn(Optional.of(expected));

        Company actual = service.findByName(input);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(repository).findByName(input);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void findByName_InvalidNameGiven_ShouldThrowException() {
        Exception actual = assertThrows(ObjectNotFoundException.class, () -> service.findByName("non_existent"));

        assertEquals("Company with given name not found.", actual.getMessage());
    }

    @Test
    public void save_ValidNameGiven_ShouldReturnValidGame() {
        Company expected = mock(Company.class);
        when(repository.save(expected)).thenReturn(expected);

        Company actual = service.save(expected);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(repository).save(expected);
        verifyNoMoreInteractions(repository);
    }
}
