import br.com.ewerton.gestaopessoa.controller.PessoaController;
import br.com.ewerton.gestaopessoa.model.PessoaDTO;
import br.com.ewerton.gestaopessoa.model.PessoaModel;
import br.com.ewerton.gestaopessoa.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class PessoaControllerTest {

    @InjectMocks
    private PessoaController pessoaController;

    @Mock
    private PessoaService pessoaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodasPessoas() {
        // Arrange
        List<PessoaModel> pessoas = Arrays.asList(
                new PessoaModel(1L, "João", "123.456.789-01", "joao@example.com", new Date()),
                new PessoaModel(2L, "Maria", "987.654.321-00", "maria@example.com", new Date())
        );

        when(pessoaService.ListarPessoas()).thenReturn(pessoas);

        // Act
        ResponseEntity<List<PessoaDTO>> response = pessoaController.listarTodasPessoas();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);
    }
}