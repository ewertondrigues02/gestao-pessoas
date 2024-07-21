package br.com.ewerton.gestaopessoa.controller;

import br.com.ewerton.gestaopessoa.exceptions.CpfDuplicadoException;
import br.com.ewerton.gestaopessoa.exceptions.ResourceNotFoundException;
import br.com.ewerton.gestaopessoa.model.CPFUtils;
import br.com.ewerton.gestaopessoa.model.PessoaDTO;
import br.com.ewerton.gestaopessoa.model.PessoaModel;
import br.com.ewerton.gestaopessoa.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Gestão de Pessoas endpoint")
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Lista todas as pessoas cadastradas no banco de dados")
    @GetMapping("listar")
    public ResponseEntity<List<PessoaDTO>> listarTodasPessoas() {
        List<PessoaModel> list = pessoaService.ListarPessoas();
        List<PessoaDTO> listDTO = list.stream().map(x -> new PessoaDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @Operation(summary = "Inseri novas pessoas e verifica se o cpf é válido")
    @PostMapping("inserir")
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

    @Operation(summary = "Encontra pessoas po id e verifica se a pessoa esta cadastrada")
    @GetMapping("busca/{id}")
    public ResponseEntity<PessoaModel> buscarPessoaPorId(@PathVariable Long id) {
        try {
            PessoaModel pessoaModel = pessoaService.localizarPessoa(id);
            if (pessoaModel == null) {
                throw new ResourceNotFoundException("Pessoa com ID " + id + " não encontrada.");
            }
            return ResponseEntity.ok().body(pessoaModel);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deleta a pessoa por id")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
        PessoaModel pessoaModel = pessoaService.excluirPessoa(id);
        if (pessoaModel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza os dados da pessoa e verifica se o cpf é valido")
    @PutMapping("alterar/{id}")
    public ResponseEntity<PessoaDTO> alterar(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
        try {
            String cpfFormatado = CPFUtils.validarEFormatarCPF(pessoaDTO.getCpf());
            pessoaDTO.setCpf(cpfFormatado);
            PessoaDTO pessoaAtualizadoDTO = pessoaService.alterarPessoa(id, pessoaDTO);
            if (pessoaAtualizadoDTO == null) {
                throw new ResourceNotFoundException("Pessoa com ID " + id + " não encontrada.");
            }
            return ResponseEntity.ok().body(pessoaAtualizadoDTO);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Atualiza os dados da pessoa parcialmente")
    @PatchMapping("/alterar-parcial/{id}")
    public ResponseEntity<PessoaModel> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> atualizacao) {
        try {
            PessoaModel pessoaAtualizadaModel = pessoaService.atualizarParcialmentePessoa(id, atualizacao);
            if (pessoaAtualizadaModel == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(pessoaAtualizadaModel);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Conta quantas pessoas estão cadastradas")
    @GetMapping("/contar")
    public ResponseEntity<Long> quantidadePessoa() {
       Long qtdPessoa = pessoaService.contarPessoa();
        return ResponseEntity.ok().body(qtdPessoa);
    }
}
