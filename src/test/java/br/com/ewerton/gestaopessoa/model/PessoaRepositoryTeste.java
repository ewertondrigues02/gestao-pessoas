package br.com.ewerton.gestaopessoa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PessoaRepositoryTeste {

    @Autowired
    private PessoaRepository pessoaRepository;

    private PessoaModel pessoa;

    @BeforeEach
    void setUp() {
        pessoa = new PessoaModel();
        pessoa.setNome("João Silva");
        pessoa.setCpf("123.456.789-00");
        pessoa.setEmail("joao@example.com");
        pessoa.setDataNascimento(new Date());
        pessoaRepository.save(pessoa);
    }

    @Test
    void testFindByCpf_Sucesso() {
        Optional<PessoaModel> found = pessoaRepository.findByCpf("123.456.789-00");
        assertTrue(found.isPresent());
        assertEquals(pessoa.getNome(), found.get().getNome());
    }

    @Test
    void testFindByCpf_NaoEncontrado() {
        Optional<PessoaModel> found = pessoaRepository.findByCpf("000.000.000-00");
        assertFalse(found.isPresent());
    }

    @Test
    void testSavePessoa() {
        PessoaModel newPessoa = new PessoaModel();
        newPessoa.setNome("Maria Souza");
        newPessoa.setCpf("987.654.321-00");
        newPessoa.setEmail("maria@example.com");
        newPessoa.setDataNascimento(new Date());

        PessoaModel savedPessoa = pessoaRepository.save(newPessoa);
        assertNotNull(savedPessoa);
        assertEquals(newPessoa.getNome(), savedPessoa.getNome());
    }

    @Test
    void testFindById_Sucesso() {
        Optional<PessoaModel> found = pessoaRepository.findById(pessoa.getId());
        assertTrue(found.isPresent());
        assertEquals(pessoa.getNome(), found.get().getNome());
    }

    @Test
    void testFindById_NaoEncontrado() {
        Optional<PessoaModel> found = pessoaRepository.findById(999L);
        assertFalse(found.isPresent());
    }

    @Test
    void testDeleteById() {
        pessoaRepository.deleteById(pessoa.getId());
        Optional<PessoaModel> found = pessoaRepository.findById(pessoa.getId());
        assertFalse(found.isPresent());
    }
}

