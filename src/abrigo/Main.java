package abrigo;

import abrigo.servico.SistemaAbrigo;
import abrigo.util.Entrada;
import abrigo.ui.MenuUsuario;

public class Main {

    public static void main(String[] args) {

        SistemaAbrigo sistema = new SistemaAbrigo();
        MenuUsuario menuUsuario = new MenuUsuario(sistema);

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
                    System.out.println("Módulo de animais.");
                    break;
                case 3:
                    System.out.println("Módulo de adoções.");
                    break;
                case 4:
                    System.out.println("Módulo de saúde.");
                    break;
                case 5:
                    System.out.println("Módulo de atividades.");
                    break;
                case 6:
                    System.out.println("Módulo de notificações.");
                    break;
                case 7:
                    System.out.println("Módulo de relatórios.");
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