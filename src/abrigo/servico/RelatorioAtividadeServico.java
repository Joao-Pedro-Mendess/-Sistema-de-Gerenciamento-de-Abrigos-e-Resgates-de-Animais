package abrigo.servico;

import java.util.List;

import abrigo.modelo.eventos.Atividade;

public class RelatorioAtividadeServico {

    private AtividadeServico atividadeServico;

    public RelatorioAtividadeServico(AtividadeServico atividadeServico) {
        if (atividadeServico == null) {
            throw new IllegalArgumentException(
                "Serviço de atividades inválido!"
            );
        }

        this.atividadeServico = atividadeServico;
    }

    public int contarAtividades() {
        return atividadeServico.listar().size();
    }

    public int contarInscricoes() {
        int total = 0;

        List<Atividade> atividades = atividadeServico.listar();

        for (Atividade atividade : atividades) {
            total += atividade.getVoluntarios().size();
        }

        return total;
    }

    public int contarInscricoesPorAtividade(int id) {
        Atividade atividade = atividadeServico.buscarPorId(id);

        if (atividade == null) {
            throw new IllegalArgumentException(
                "Atividade não encontrada!"
            );
        }

        return atividade.getVoluntarios().size();
    }

    public String gerarResumo() {
        return "===== RELATÓRIO DE ATIVIDADES =====\n"
            + "Total de atividades: " + contarAtividades() + "\n"
            + "Total de inscrições: " + contarInscricoes();
    }

    public String gerarResumoAtividade(int id) {
        Atividade atividade = atividadeServico.buscarPorId(id);

        if (atividade == null) {
            throw new IllegalArgumentException(
                "Atividade não encontrada!"
            );
        }

        return "===== RELATÓRIO DA ATIVIDADE =====\n"
            + "Nome: " + atividade.getNome() + "\n"
            + "Descrição: " + atividade.getDescricao() + "\n"
            + "Data: " + atividade.getData() + "\n"
            + "Local: " + atividade.getLocal() + "\n"
            + "Vagas: " + atividade.getVagas() + "\n"
            + "Inscritos: " + atividade.getVoluntarios().size();
    }
}