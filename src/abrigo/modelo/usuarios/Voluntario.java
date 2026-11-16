package abrigo.modelo.usuarios;

import abrigo.modelo.Usuario;

public class Voluntario extends Usuario {

    private String disponibilidade;

    public Voluntario(String nome, String cpf, String email, String disponibilidade) {
        super(nome, cpf, email);
        this.disponibilidade = disponibilidade;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}