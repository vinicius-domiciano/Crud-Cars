package br.com.domiciano.project.crud.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Data
@NoArgsConstructor
public class CadastroMarcaCarroDto {

    @JsonIgnore
    private Long id;

    private String descricaoMarca;

    @NotEmpty(message = "Necessario informar o nomeMarca")
    @NotNull(message = "Necessario informar o nomeMarca")
    private String nomeMarca;

    @JsonIgnore
    private Calendar dataCadastro;

    @JsonIgnore
    private Calendar dataAtualizacao;

    @JsonGetter("dataCadastro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    @JsonGetter("dataAtualizacao")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    public Calendar getDataAtualizacao() {
        return dataAtualizacao;
    }

    @JsonGetter("id")
    public Long getId() {
        return id;
    }
}
