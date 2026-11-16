package abrigo.modelo.saude;

import abrigo.modelo.usuarios.Veterinario;

import java.time.LocalDate;

public class Consulta {

    private final LocalDate data;
    private final String descricao;
    private final String tratamento;
    private final Veterinario veterinario;

    public Consulta(LocalDate data, String descricao, String tratamento, Veterinario veterinario) {
        this.data = data;
        this.descricao = descricao;
        this.tratamento = tratamento;
        this.veterinario = veterinario;
    }

    public LocalDate getData() { return data; }
    public String getDescricao() { return descricao; }
    public String getTratamento() { return tratamento; }
    public Veterinario getVeterinario() { return veterinario; }
}
