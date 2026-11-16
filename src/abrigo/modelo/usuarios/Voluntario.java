package abrigo.modelo.usuarios;

import abrigo.excecoes.AnimalIndisponivelException;
import abrigo.modelo.Usuario;
import abrigo.modelo.adocao.PodeSolicitarAdocao;
import abrigo.modelo.adocao.SolicitacaoAdocao;
import abrigo.modelo.animais.Animal;

public class Voluntario extends Usuario implements PodeSolicitarAdocao {

    private String disponibilidade;

    public Voluntario(String nome, String cpf, String email, String disponibilidade) {
        super(nome, cpf, email);
        this.disponibilidade = disponibilidade;
    }

    @Override
    public SolicitacaoAdocao solicitarAdocao(Animal animal) throws AnimalIndisponivelException {
        animal.iniciarProcessoAdocao();
        return new SolicitacaoAdocao(this, animal);
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}