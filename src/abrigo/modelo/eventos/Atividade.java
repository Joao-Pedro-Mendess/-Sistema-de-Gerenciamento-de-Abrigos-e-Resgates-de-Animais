package abrigo.modelo.eventos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import abrigo.modelo.usuarios.Voluntario;

public class Atividade {

    private static int proximoId = 1;

    private final int id;
    private String nome;
    private String descricao;
    private LocalDate data;
    private String local;
    private int vagas;
    private List<Voluntario> voluntarios;

    public Atividade(String nome, String descricao, LocalDate data,
                     String local, int vagas) {

        this.id = proximoId++;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.vagas = vagas;
        this.voluntarios = new ArrayList<>();
    }

    public void inscreverVoluntario(Voluntario voluntario) {

        if (voluntario == null) {
            throw new IllegalArgumentException("Voluntário inválido!");
        }

        if (data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                "Não é possível se inscrever em uma atividade passada!"
            );
        }

        if (voluntarios.size() >= vagas) {
            throw new IllegalArgumentException("Não há vagas disponíveis!");
        }

        for (Voluntario v : voluntarios) {
            if (v.getCpf().equals(voluntario.getCpf())) {
                throw new IllegalArgumentException(
                    "Voluntário já inscrito!"
                );
            }
        }

        voluntarios.add(voluntario);
    }

    public void cancelarInscricao(String cpf) {

        for (Voluntario voluntario : voluntarios) {
            if (voluntario.getCpf().equals(cpf)) {
                voluntarios.remove(voluntario);
                return;
            }
        }

        throw new IllegalArgumentException(
            "Voluntário não encontrado na atividade!"
        );
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public String getLocal() {
        return local;
    }

    public int getVagas() {
        return vagas;
    }

    public List<Voluntario> getVoluntarios() {
        return new ArrayList<>(voluntarios);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setVagas(int vagas) {
        if (vagas < voluntarios.size()) {
            throw new IllegalArgumentException(
                "Vagas não podem ser menores que o número de inscritos!"
            );
        }
        this.vagas = vagas;
    }
}