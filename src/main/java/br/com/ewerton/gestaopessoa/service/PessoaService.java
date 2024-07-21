package br.com.ewerton.gestaopessoa.service;

import br.com.ewerton.gestaopessoa.exceptions.CpfDuplicadoException;
import br.com.ewerton.gestaopessoa.exceptions.DatabaseException;
import br.com.ewerton.gestaopessoa.exceptions.ResourceNotFoundException;
import br.com.ewerton.gestaopessoa.model.PessoaDTO;
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

    public PessoaDTO alterarPessoa(Long id, PessoaDTO pessoaDTO) {
        try {
            PessoaModel entity = pessoaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pessoa com ID " + id + " não encontrada."));
            updateData(entity, pessoaDTO);
            PessoaModel pessoaSalva = pessoaRepository.save(entity);
            return toDTO(pessoaSalva);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }
    }

    private void updateData(PessoaModel entity, PessoaDTO dto) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        entity.setDataNascimento(dto.getDataNascimento());
    }

    private PessoaDTO toDTO(PessoaModel model) {
        PessoaDTO dto = new PessoaDTO();
        dto.setNome(model.getNome());
        dto.setEmail(model.getEmail());
        dto.setCpf(model.getCpf());
        dto.setDataNascimento(model.getDataNascimento());
        return dto;
    }

    public PessoaModel excluirPessoa(Long id) {
        PessoaModel pessoa =  pessoaRepository.findById(id).orElse(null);
        if (pessoa != null) {
            pessoaRepository.deleteById(id);
            return pessoa;
        }
        return null;
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
