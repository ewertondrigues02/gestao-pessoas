package br.com.ewerton.gestaopessoa.service;

import br.com.ewerton.gestaopessoa.exceptions.CpfDuplicadoException;
import br.com.ewerton.gestaopessoa.exceptions.ResourceNotFoundException;
import br.com.ewerton.gestaopessoa.model.PessoaDTO;
import br.com.ewerton.gestaopessoa.model.PessoaModel;
import br.com.ewerton.gestaopessoa.model.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTeste {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    private PessoaModel pessoa;
    private PessoaDTO pessoaDTO;

    @BeforeEach
    void setUp() {
        pessoa = new PessoaModel();
        pessoa.setId(1L);
        pessoa.setNome("João Silva");
        pessoa.setCpf("123.456.789-00");
        pessoa.setEmail("joao@example.com");
        pessoa.setDataNascimento(new Date());

        pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome("João Silva");
        pessoaDTO.setCpf("123.456.789-00");
        pessoaDTO.setEmail("joao@example.com");
        pessoaDTO.setDataNascimento(new Date());
    }

    @Test
    void testListarPessoas() {
        when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));
        List<PessoaModel> pessoas = pessoaService.ListarPessoas();
        assertFalse(pessoas.isEmpty());
        assertEquals(1, pessoas.size());
    }

    @Test
    void testInserirPessoa_Sucesso() {
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.save(any(PessoaModel.class))).thenReturn(pessoa);

        PessoaModel result = pessoaService.inserirPessoa(pessoa);
        assertNotNull(result);
        assertEquals(pessoa.getCpf(), result.getCpf());
    }

    @Test
    void testInserirPessoa_CpfDuplicado() {
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.of(pessoa));
        assertThrows(CpfDuplicadoException.class, () -> pessoaService.inserirPessoa(pessoa));
    }

    @Test
    void testLocalizarPessoa_Sucesso() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        PessoaModel result = pessoaService.localizarPessoa(1L);
        assertNotNull(result);
        assertEquals(pessoa.getNome(), result.getNome());
    }

    @Test
    void testLocalizarPessoa_NaoEncontrado() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> pessoaService.localizarPessoa(1L));
    }

    @Test
    void testAlterarPessoa_Sucesso() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(PessoaModel.class))).thenReturn(pessoa);

        PessoaDTO updatedPessoaDTO = new PessoaDTO();
        updatedPessoaDTO.setNome("João Atualizado");
        updatedPessoaDTO.setCpf("123.456.789-00");
        updatedPessoaDTO.setEmail("joao.atualizado@example.com");
        updatedPessoaDTO.setDataNascimento(new Date());

        PessoaDTO result = pessoaService.alterarPessoa(1L, updatedPessoaDTO);
        assertNotNull(result);
        assertEquals(updatedPessoaDTO.getNome(), result.getNome());
    }

    @Test
    void testAlterarPessoa_NaoEncontrado() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> pessoaService.alterarPessoa(1L, pessoaDTO));
    }

    @Test
    void testExcluirPessoa_Sucesso() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        doNothing().when(pessoaRepository).deleteById(anyLong());

        PessoaModel result = pessoaService.excluirPessoa(1L);
        assertNotNull(result);
        verify(pessoaRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testExcluirPessoa_NaoEncontrado() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());
        PessoaModel result = pessoaService.excluirPessoa(1L);
        assertNull(result);
    }

    @Test
    void testAtualizarParcialmentePessoa_Sucesso() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(PessoaModel.class))).thenReturn(pessoa);

        Map<String, Object> atualizacao = new HashMap<>();
        atualizacao.put("nome", "João Atualizado Parcialmente");

        PessoaModel result = pessoaService.atualizarParcialmentePessoa(1L, atualizacao);
        assertNotNull(result);
        assertEquals("João Atualizado Parcialmente", result.getNome());
    }

    @Test
    void testAtualizarParcialmentePessoa_NaoEncontrado() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());
        Map<String, Object> atualizacao = new HashMap<>();
        atualizacao.put("nome", "João Atualizado Parcialmente");

        PessoaModel result = pessoaService.atualizarParcialmentePessoa(1L, atualizacao);
        assertNull(result);
    }

    @Test
    void testContarPessoa() {
        when(pessoaRepository.count()).thenReturn(10L);
        Long count = pessoaService.contarPessoa();
        assertEquals(10L, count);
    }
}
