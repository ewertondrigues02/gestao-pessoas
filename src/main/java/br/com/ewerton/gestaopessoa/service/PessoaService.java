package br.com.ewerton.gestaopessoa.service;

import br.com.ewerton.gestaopessoa.exceptions.CpfDuplicadoException;
import br.com.ewerton.gestaopessoa.exceptions.DatabaseException;
import br.com.ewerton.gestaopessoa.exceptions.ResourceNotFoundException;
import br.com.ewerton.gestaopessoa.model.PessoaModel;
import br.com.ewerton.gestaopessoa.model.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<PessoaModel> ListarPessoas(){
        return pessoaRepository.findAll();
    }

    public PessoaModel inserirPessoa(PessoaModel pessoa){
        if (pessoaRepository.findByCpf(pessoa.getCpf()).isPresent()) {
            throw new CpfDuplicadoException("CPF já cadastrado: " + pessoa.getCpf());
        }
        return pessoaRepository.save(pessoa);
    }

    public PessoaModel localizarPessoa(Long id){
        Optional<PessoaModel> obj = pessoaRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public PessoaModel alterarPessoa(Long id, PessoaModel pessoa){
        try {
            PessoaModel entity = pessoaRepository.getReferenceById(id);
            updateData(entity, pessoa);
            return pessoaRepository.save(entity);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }
    }

    private void updateData(PessoaModel entity, PessoaModel obj) {
        entity.setNome(obj.getNome());
        entity.setEmail(obj.getEmail());
        entity.setCpf(obj.getCpf());
        entity.setDataNascimento(obj.getDataNascimento());

    }

    public void excluirPessoa(Long id){
        pessoaRepository.deleteById(id);
    }

    public PessoaModel atualizarParcialmentePessoa(Long id, Map<String, Object> atualizacao){
        PessoaModel pessoa = pessoaRepository.getReferenceById(id);
        atualizacao.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(PessoaModel.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, pessoa, value);
            }
        });
        return pessoaRepository.save(pessoa);
    }

    public Long contarPessoa() {
        return pessoaRepository.count();
    }

}
