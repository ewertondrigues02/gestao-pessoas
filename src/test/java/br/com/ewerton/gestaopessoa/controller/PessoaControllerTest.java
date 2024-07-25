package br.com.ewerton.gestaopessoa.controller;

import br.com.ewerton.gestaopessoa.controller.PessoaController;
import br.com.ewerton.gestaopessoa.exceptions.CpfDuplicadoException;
import br.com.ewerton.gestaopessoa.exceptions.ResourceNotFoundException;
import br.com.ewerton.gestaopessoa.model.CPFUtils;
import br.com.ewerton.gestaopessoa.model.PessoaDTO;
import br.com.ewerton.gestaopessoa.model.PessoaModel;
import br.com.ewerton.gestaopessoa.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        List<PessoaModel> pessoas = Arrays.asList(new PessoaModel(1L, "João", "12345678900", "joao@example.com", null));
        when(pessoaService.ListarPessoas()).thenReturn(pessoas);

        ResponseEntity<List<PessoaDTO>> response = pessoaController.listarTodasPessoas();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("João", response.getBody().get(0).getNome());
    }

    @Test
    public void testInserirPessoa() {
        PessoaDTO pessoaDTO = new PessoaDTO(1L, "João", "12345678900", "joao@example.com", null);
        PessoaModel pessoaModel = new PessoaModel(1L, "João", "12345678900", "joao@example.com", null);

        when(pessoaService.inserirPessoa(any(PessoaModel.class))).thenReturn(pessoaModel);

        ResponseEntity<PessoaDTO> response = pessoaController.inserir(pessoaDTO);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals("João", response.getBody().getNome());
    }

    @Test
    public void testInserirPessoaCpfDuplicado() {
        PessoaDTO pessoaDTO = new PessoaDTO(1L, "João", "12345678900", "joao@example.com", null);

        when(pessoaService.inserirPessoa(any(PessoaModel.class))).thenThrow(CpfDuplicadoException.class);

        ResponseEntity<PessoaDTO> response = pessoaController.inserir(pessoaDTO);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    public void testBuscarPessoaPorId() {
        PessoaModel pessoa = new PessoaModel(1L, "João", "12345678900", "joao@example.com", null);
        when(pessoaService.localizarPessoa(anyLong())).thenReturn(pessoa);

        ResponseEntity<PessoaModel> response = pessoaController.buscarPessoaPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("João", response.getBody().getNome());
    }

    @Test
    public void testBuscarPessoaPorIdNaoEncontrada() {
        when(pessoaService.localizarPessoa(anyLong())).thenReturn(null);

        ResponseEntity<PessoaModel> response = pessoaController.buscarPessoaPorId(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testExcluirPessoa() {
        PessoaModel pessoa = new PessoaModel(1L, "João", "12345678900", "joao@example.com", null);
        when(pessoaService.excluirPessoa(anyLong())).thenReturn(pessoa);

        ResponseEntity<Void> response = pessoaController.excluirPessoa(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void testExcluirPessoaNaoEncontrada() {
        when(pessoaService.excluirPessoa(anyLong())).thenReturn(null);

        ResponseEntity<Void> response = pessoaController.excluirPessoa(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testAlterarPessoa() {
        PessoaDTO pessoaDTO = new PessoaDTO(1L, "João", "12345678900", "joao@example.com", null);
        PessoaDTO pessoaAtualizadaDTO = new PessoaDTO(1L, "João Silva", "12345678900", "joao.silva@example.com", null);

        when(pessoaService.alterarPessoa(anyLong(), any(PessoaDTO.class))).thenReturn(pessoaAtualizadaDTO);

        ResponseEntity<PessoaDTO> response = pessoaController.alterar(1L, pessoaDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("João Silva", response.getBody().getNome());
    }

    @Test
    public void testAlterarPessoaNaoEncontrada() {
        PessoaDTO pessoaDTO = new PessoaDTO(1L, "João", "12345678900", "joao@example.com", null);

        when(pessoaService.alterarPessoa(anyLong(), any(PessoaDTO.class))).thenReturn(null);

        ResponseEntity<PessoaDTO> response = pessoaController.alterar(1L, pessoaDTO);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testAtualizarParcialmentePessoa() {
        PessoaModel pessoaAtualizada = new PessoaModel(1L, "João Silva", "12345678900", "joao.silva@example.com", null);

        when(pessoaService.atualizarParcialmentePessoa(anyLong(), any(Map.class))).thenReturn(pessoaAtualizada);

        ResponseEntity<PessoaModel> response = pessoaController.atualizarParcialmente(1L, Map.of("nome", "João Silva"));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("João Silva", response.getBody().getNome());
    }

    @Test
    public void testAtualizarParcialmentePessoaNaoEncontrada() {
        when(pessoaService.atualizarParcialmentePessoa(anyLong(), any(Map.class))).thenReturn(null);

        ResponseEntity<PessoaModel> response = pessoaController.atualizarParcialmente(1L, Map.of("nome", "João Silva"));

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testQuantidadePessoa() {
        long expectedCount = 5L;
        when(pessoaService.contarPessoa()).thenReturn(expectedCount);

        ResponseEntity<Long> response = pessoaController.quantidadePessoa();

        assertEquals(expectedCount, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
