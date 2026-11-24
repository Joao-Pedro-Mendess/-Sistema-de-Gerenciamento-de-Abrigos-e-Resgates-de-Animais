package abrigo.servico;

import java.util.ArrayList;
import java.util.List;

import abrigo.modelo.adocao.SolicitacaoAdocao;
import abrigo.modelo.adocao.StatusSolicitacao;
import abrigo.modelo.animais.Animal;
import abrigo.modelo.usuarios.Administrador;
import abrigo.modelo.usuarios.Adotante;
import abrigo.excecoes.AnimalIndisponivelException;

public class AdocaoServico {

    private List<SolicitacaoAdocao> solicitacoes;
    private NotificacaoServico notificacaoServico;

    public AdocaoServico() {
        solicitacoes = new ArrayList<>();
        notificacaoServico = new NotificacaoServico();
    }

    public void solicitar(Animal animal, Adotante adotante)
            throws AnimalIndisponivelException {

        if (animal == null || adotante == null) {
            throw new IllegalArgumentException(
                "Animal ou adotante inválido!"
            );
        }

        animal.iniciarProcessoAdocao();

        SolicitacaoAdocao solicitacao =
            new SolicitacaoAdocao(adotante, animal);

        solicitacoes.add(solicitacao);

        notificacaoServico.enviar(
            "Sua solicitação de adoção para "
            + animal.getNome() + " foi registrada!",
            adotante
        );
    }

    public List<SolicitacaoAdocao> listar() {
        return new ArrayList<>(solicitacoes);
    }

    public List<SolicitacaoAdocao> listarCandidatos(Animal animal) {
        List<SolicitacaoAdocao> resultado = new ArrayList<>();

        if (animal == null) {
            throw new IllegalArgumentException("Animal inválido!");
        }

        for (SolicitacaoAdocao solicitacao : solicitacoes) {
            if (solicitacao.getAnimal().getId() == animal.getId()
                    && solicitacao.getStatus()
                    == StatusSolicitacao.PENDENTE) {

                resultado.add(solicitacao);
            }
        }

        return resultado;
    }

    public SolicitacaoAdocao buscarPorId(int id) {
        if (id <= 0 || id > solicitacoes.size()) {
            return null;
        }

        return solicitacoes.get(id - 1);
    }

    public void aprovar(int id, Administrador administrador)
            throws AnimalIndisponivelException {

        SolicitacaoAdocao solicitacao = buscarPorId(id);

        if (solicitacao == null) {
            throw new IllegalArgumentException(
                "Solicitação não encontrada!"
            );
        }

        if (administrador == null) {
            throw new IllegalArgumentException(
                "Administrador inválido!"
            );
        }

        solicitacao.aprovar(administrador);

        notificacaoServico.enviar(
            "Sua solicitação de adoção para "
            + solicitacao.getAnimal().getNome()
            + " foi aprovada!",
            solicitacao.getSolicitante()
        );
    }

    public void rejeitar(int id, Administrador administrador) {

        SolicitacaoAdocao solicitacao = buscarPorId(id);

        if (solicitacao == null) {
            throw new IllegalArgumentException(
                "Solicitação não encontrada!"
            );
        }

        if (administrador == null) {
            throw new IllegalArgumentException(
                "Administrador inválido!"
            );
        }

        solicitacao.rejeitar(administrador);

        notificacaoServico.enviar(
            "Sua solicitação de adoção para "
            + solicitacao.getAnimal().getNome()
            + " foi rejeitada.",
            solicitacao.getSolicitante()
        );
    }

    public void registrarDevolucao(int id) {

        SolicitacaoAdocao solicitacao = buscarPorId(id);

        if (solicitacao == null) {
            throw new IllegalArgumentException(
                "Solicitação não encontrada!"
            );
        }

        solicitacao.registrarDevolucao();

        notificacaoServico.enviar(
            "A adoção de "
            + solicitacao.getAnimal().getNome()
            + " foi registrada como devolvida.",
            solicitacao.getSolicitante()
        );
    }
}