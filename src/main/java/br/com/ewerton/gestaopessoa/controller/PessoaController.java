package br.com.ewerton.gestaopessoa.controller;

import br.com.ewerton.gestaopessoa.exceptions.CpfDuplicadoException;
import br.com.ewerton.gestaopessoa.model.CPFUtils;
import br.com.ewerton.gestaopessoa.model.PessoaDTO;
import br.com.ewerton.gestaopessoa.model.PessoaModel;
import br.com.ewerton.gestaopessoa.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarTodasPessoas() {
        List<PessoaModel> list = pessoaService.ListarPessoas();
        List<PessoaDTO> listDTO = list.stream().map(x -> new PessoaDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> inserir(@RequestBody PessoaDTO pessoaDTO) {
        try {
            String cpfFormatado = CPFUtils.validarEFormatarCPF(pessoaDTO.getCpf());
            pessoaDTO.setCpf(cpfFormatado);
            PessoaModel pessoaModel = new PessoaModel(pessoaDTO.getId(), pessoaDTO.getNome(), pessoaDTO.getCpf(), pessoaDTO.getEmail(), pessoaDTO.getDataNascimento());
            pessoaService.inserirPessoa(pessoaModel);
            PessoaDTO savedPessoaDTO = new PessoaDTO(pessoaModel.getId(), pessoaModel.getNome(), pessoaModel.getCpf(), pessoaModel.getEmail(), pessoaModel.getDataNascimento());
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoaDTO.getId()).toUri();
            return ResponseEntity.created(uri).body(savedPessoaDTO);
        } catch (CpfDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
