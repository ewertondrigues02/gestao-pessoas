package br.com.ewerton.gestaopessoa.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {
    Optional<PessoaModel> findByCpf(String cpf);
}
