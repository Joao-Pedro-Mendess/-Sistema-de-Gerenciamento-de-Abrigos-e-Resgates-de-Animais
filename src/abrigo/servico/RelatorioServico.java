package abrigo.servico;

import java.util.List;

import abrigo.modelo.animais.Animal;
import abrigo.modelo.animais.StatusAdocao;
import abrigo.modelo.adocao.SolicitacaoAdocao;
import abrigo.modelo.adocao.StatusSolicitacao;

public class RelatorioServico {

    private AnimalServico animalServico;
    private AdocaoServico adocaoServico;

    public RelatorioServico(AnimalServico animalServico,
            AdocaoServico adocaoServico) {

        if (animalServico == null || adocaoServico == null) {
            throw new IllegalArgumentException(
                "Serviços inválidos!"
            );
        }

        this.animalServico = animalServico;
        this.adocaoServico = adocaoServico;
    }

    public int contarAnimais() {
        return animalServico.listar().size();
    }

    public int contarDisponiveis() {
        return animalServico.filtrarPorStatus(
            StatusAdocao.DISPONIVEL
        ).size();
    }

    public int contarAdotados() {
        return animalServico.filtrarPorStatus(
            StatusAdocao.ADOTADO
        ).size();
    }

    public int contarEmProcesso() {
        return animalServico.filtrarPorStatus(
            StatusAdocao.EM_PROCESSO
        ).size();
    }

    public int contarSolicitacoesPendentes() {
        int contador = 0;

        List<SolicitacaoAdocao> solicitacoes =
            adocaoServico.listar();

        for (SolicitacaoAdocao solicitacao : solicitacoes) {
            if (solicitacao.getStatus()
                    == StatusSolicitacao.PENDENTE) {
                contador++;
            }
        }

        return contador;
    }

    public String gerarResumo() {
        return "===== RELATÓRIO DO ABRIGO =====\n"
            + "Total de animais: " + contarAnimais() + "\n"
            + "Disponíveis para adoção: " + contarDisponiveis() + "\n"
            + "Adotados: " + contarAdotados() + "\n"
            + "Em processo de adoção: " + contarEmProcesso() + "\n"
            + "Solicitações pendentes: "
            + contarSolicitacoesPendentes();
    }
}