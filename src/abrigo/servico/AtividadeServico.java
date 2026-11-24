package abrigo.servico;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import abrigo.modelo.eventos.Atividade;
import abrigo.modelo.usuarios.Voluntario;

public class AtividadeServico {

    private List<Atividade> atividades;
    private NotificacaoServico notificacaoServico;

    public AtividadeServico() {
        atividades = new ArrayList<>();
        notificacaoServico = new NotificacaoServico();
    }

    public void cadastrar(Atividade atividade) {
        if (atividade == null) {
            throw new IllegalArgumentException("Atividade inválida!");
        }

        atividades.add(atividade);
    }

    public List<Atividade> listar() {
        return new ArrayList<>(atividades);
    }

    public Atividade buscarPorId(int id) {
        for (Atividade atividade : atividades) {
            if (atividade.getId() == id) {
                return atividade;
            }
        }

        return null;
    }

    public boolean remover(int id) {
        Atividade atividade = buscarPorId(id);

        if (atividade == null) {
            return false;
        }

        atividades.remove(atividade);
        return true;
    }

    public void editar(int id, String novoNome, String novaDescricao,
                       LocalDate novaData, String novoLocal, int novasVagas) {

        Atividade atividade = buscarPorId(id);

        if (atividade == null) {
            throw new IllegalArgumentException("Atividade não encontrada!");
        }

        if (novasVagas < atividade.getVoluntarios().size()) {
            throw new IllegalArgumentException(
                "Vagas não podem ser menores que o número de inscritos!"
            );
        }

        atividade.setNome(novoNome);
        atividade.setDescricao(novaDescricao);
        atividade.setData(novaData);
        atividade.setLocal(novoLocal);
        atividade.setVagas(novasVagas);
    }

    public void inscreverVoluntario(int idAtividade, Voluntario voluntario) {
        Atividade atividade = buscarPorId(idAtividade);

        if (atividade == null) {
            throw new IllegalArgumentException("Atividade não encontrada!");
        }

        atividade.inscreverVoluntario(voluntario);

        notificacaoServico.enviar(
            "Sua inscrição na atividade " + atividade.getNome()
            + " foi confirmada!",
            voluntario
        );
    }

    public void cancelarInscricao(int idAtividade, String cpf) {
        Atividade atividade = buscarPorId(idAtividade);

        if (atividade == null) {
            throw new IllegalArgumentException("Atividade não encontrada!");
        }

        atividade.cancelarInscricao(cpf);
    }
}