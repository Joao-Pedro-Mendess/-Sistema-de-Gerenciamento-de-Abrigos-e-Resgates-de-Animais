package abrigo.ui;

import abrigo.servico.SistemaAbrigo;
import abrigo.modelo.notificacoes.Notificacao;
import abrigo.modelo.usuarios.Usuario;
import abrigo.util.Entrada;

import java.util.List;

public class MenuNotificacao {

    private SistemaAbrigo sistema;

    public MenuNotificacao(SistemaAbrigo sistema) {
        this.sistema = sistema;
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n===== NOTIFICAÇÕES =====");
            System.out.println("1 - Listar notificações");
            System.out.println("2 - Buscar notificações por usuário");
            System.out.println("3 - Marcar notificação como lida");
            System.out.println("4 - Remover notificação");
            System.out.println("0 - Voltar");

            opcao = Entrada.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    listar();
                    break;
                case 2:
                    buscarPorUsuario();
                    break;
                case 3:
                    marcarComoLida();
                    break;
                case 4:
                    remover();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void listar() {
        List<Notificacao> notificacoes =
                sistema.getNotificacaoServico().listar();

        if (notificacoes.isEmpty()) {
            System.out.println("Nenhuma notificação cadastrada.");
            return;
        }

        for (Notificacao n : notificacoes) {
            exibirNotificacao(n);
        }
    }

    private void buscarPorUsuario() {
        String cpf = Entrada.lerTexto("CPF do usuário: ");

        Usuario usuario = sistema.getUsuarioServico().buscarPorCpf(cpf);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        List<Notificacao> notificacoes =
                sistema.getNotificacaoServico().listarPorUsuario(usuario);

        if (notificacoes.isEmpty()) {
            System.out.println("Nenhuma notificação encontrada.");
            return;
        }

        for (Notificacao n : notificacoes) {
            exibirNotificacao(n);
        }
    }

    private void marcarComoLida() {
        listar();

        int id = Entrada.lerInteiro("ID da notificação: ");

        Notificacao notificacao =
                sistema.getNotificacaoServico().buscarPorId(id);

        if (notificacao == null) {
            System.out.println("Notificação não encontrada.");
            return;
        }

        sistema.getNotificacaoServico().marcarComoLida(
                id, notificacao.getDestinatario()
        );

        System.out.println("Notificação marcada como lida.");
    }

    private void remover() {
        listar();

        int id = Entrada.lerInteiro("ID da notificação: ");

        boolean removida = sistema.getNotificacaoServico().remover(id);

        if (removida) {
            System.out.println("Notificação removida.");
        } else {
            System.out.println("Notificação não encontrada.");
        }
    }

    private void exibirNotificacao(Notificacao n) {
        System.out.println("\nID: " + n.getId());
        System.out.println("Destinatário: " + n.getDestinatario().getNome());
        System.out.println("Mensagem: " + n.getMensagem());
        System.out.println("Data: " + n.getData());
        System.out.println("Status: " + (n.isLida() ? "Lida" : "Não lida"));
    }
}