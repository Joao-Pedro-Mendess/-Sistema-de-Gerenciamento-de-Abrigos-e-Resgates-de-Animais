package abrigo.ui;

import abrigo.servico.SistemaAbrigo;
import abrigo.util.Entrada;

public class MenuRelatorio {

    private SistemaAbrigo sistema;

    public MenuRelatorio(SistemaAbrigo sistema) {
        this.sistema = sistema;
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n===== RELATÓRIOS =====");
            System.out.println("1 - Relatório geral do abrigo");
            System.out.println("2 - Relatório de atividades");
            System.out.println("3 - Relatório de saúde");
            System.out.println("0 - Voltar");

            opcao = Entrada.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    relatorioGeral();
                    break;
                case 2:
                    relatorioAtividades();
                    break;
                case 3:
                    relatorioSaude();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void relatorioGeral() {
        System.out.println("\n===== RELATÓRIO GERAL =====");
        System.out.println(sistema.getRelatorioServico().gerarResumo());
    }

    private void relatorioAtividades() {
        System.out.println("\n===== RELATÓRIO DE ATIVIDADES =====");
        System.out.println(sistema.getRelatorioAtividadeServico().gerarResumo());
    }

    private void relatorioSaude() {
        System.out.println("\n===== RELATÓRIO DE SAÚDE =====");
        System.out.println(sistema.getRelatorioSaudeServico().gerarResumo());
    }
}