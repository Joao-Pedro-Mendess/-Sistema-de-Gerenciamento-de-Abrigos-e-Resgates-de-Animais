package abrigo;

import abrigo.servico.SistemaAbrigo;
import abrigo.util.Entrada;
import abrigo.ui.MenuUsuario;
import abrigo.ui.MenuAnimal;
import abrigo.ui.MenuAdocao;
import abrigo.ui.MenuSaude;
import abrigo.ui.MenuAtividade;
import abrigo.ui.MenuNotificacao;
import abrigo.ui.MenuRelatorio;

public class Main {

    public static void main(String[] args) {
        SistemaAbrigo sistema = new SistemaAbrigo();

        MenuUsuario menuUsuario = new MenuUsuario(sistema);
        MenuAnimal menuAnimal = new MenuAnimal(sistema);
        MenuAdocao menuAdocao = new MenuAdocao(sistema);
        MenuSaude menuSaude = new MenuSaude(sistema);
        MenuAtividade menuAtividade = new MenuAtividade(sistema);
        MenuNotificacao menuNotificacao = new MenuNotificacao(sistema);
        MenuRelatorio menuRelatorio = new MenuRelatorio(sistema);

        int opcao;

        do {
            System.out.println("\n===== SISTEMA DE GERENCIAMENTO DE ABRIGOS =====");
            System.out.println("1 - Gerenciar usuários");
            System.out.println("2 - Gerenciar animais");
            System.out.println("3 - Gerenciar adoções");
            System.out.println("4 - Gerenciar saúde");
            System.out.println("5 - Gerenciar atividades");
            System.out.println("6 - Notificações");
            System.out.println("7 - Relatórios");
            System.out.println("0 - Sair");

            opcao = Entrada.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    menuUsuario.exibir();
                    break;
                case 2:
                    menuAnimal.exibir();
                    break;
                case 3:
                    menuAdocao.exibir();
                    break;
                case 4:
                    menuSaude.exibir();
                    break;
                case 5:
                    menuAtividade.exibir();
                    break;
                case 6:
                    menuNotificacao.exibir();
                    break;
                case 7:
                    menuRelatorio.exibir();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}