package abrigo.modelo.adocao;

import abrigo.excecoes.AnimalIndisponivelException;
import abrigo.modelo.usuarios.Usuario;
import abrigo.modelo.animais.Animal;
import abrigo.modelo.usuarios.Administrador;

import java.time.LocalDate;

public class SolicitacaoAdocao {

    private final Usuario solicitante;
    private final Animal animal;
    private final LocalDate dataSolicitacao;
    private StatusSolicitacao status;

    public SolicitacaoAdocao(Usuario solicitante, Animal animal) {
        this.solicitante = solicitante;
        this.animal = animal;
        this.dataSolicitacao = LocalDate.now();
        this.status = StatusSolicitacao.PENDENTE;
    }

    // Regra 5: apenas administradores aprovam (o tipo do parâmetro garante isso)
    public void aprovar(Administrador administrador) throws AnimalIndisponivelException {
        verificarPendente();
        animal.concluirAdocao();
        this.status = StatusSolicitacao.APROVADA;
    }

    public void rejeitar(Administrador administrador) {
        verificarPendente();
        animal.reabrirParaAdocao();
        this.status = StatusSolicitacao.REJEITADA;
    }

    // Registro de devolução quando necessário
    public void registrarDevolucao() {
        if (status != StatusSolicitacao.APROVADA) {
            throw new IllegalStateException("Só é possível devolver uma adoção aprovada.");
        }
        animal.reabrirParaAdocao();
        this.status = StatusSolicitacao.DEVOLVIDA;
    }

    private void verificarPendente() {
        if (status != StatusSolicitacao.PENDENTE) {
            throw new IllegalStateException("Esta solicitação já foi analisada.");
        }
    }

    public Usuario getSolicitante() { return solicitante; }
    public Animal getAnimal() { return animal; }
    public LocalDate getDataSolicitacao() { return dataSolicitacao; }
    public StatusSolicitacao getStatus() { return status; }
}
