package br.com.ewerton.gestaopessoa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_pessoa")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
public class PessoaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "nome")
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    @Column(name = "cpf")
    private String cpf;

    @NotBlank
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PessoaModel pessoa = (PessoaModel) o;
        return Objects.equals(getId(), pessoa.getId()) && Objects.equals(getNome(), pessoa.getNome()) && Objects.equals(getCpf(), pessoa.getCpf()) && Objects.equals(getEmail(), pessoa.getEmail()) && Objects.equals(getDataNascimento(), pessoa.getDataNascimento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getCpf(), getEmail(), getDataNascimento());
    }
}

