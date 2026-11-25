
package abrigo.ui;

import java.util.List;

import abrigo.modelo.adocao.SolicitacaoAdocao;
import abrigo.modelo.adocao.StatusSolicitacao;
import abrigo.modelo.animais.Animal;
import abrigo.modelo.animais.StatusAdocao;
import abrigo.modelo.usuarios.Administrador;
import abrigo.modelo.usuarios.Adotante;
import abrigo.modelo.usuarios.Usuario;
import abrigo.excecoes.AnimalIndisponivelException;
import abrigo.servico.SistemaAbrigo;
import abrigo.util.Entrada;

public class MenuAdocao {

    private SistemaAbrigo sistema;

    public MenuAdocao(SistemaAbrigo sistema) {
        this.sistema = sistema;
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n===== MENU DE ADOÇÕES =====");
            System.out.println("1 - Solicitar adoção");
            System.out.println("2 - Listar solicitações");
            System.out.println("3 - Aprovar solicitação");
            System.out.println("4 - Rejeitar solicitação");
            System.out.println("5 - Registrar devolução");
            System.out.println("0 - Voltar");

            opcao = Entrada.lerInteiro("Escolha: ");

            switch (opcao) {
                case 1:
                    solicitarAdocao();
                    break;
                case 2:
                    listarSolicitacoes();
                    break;
                case 3:
                    aprovar();
                    break;
                case 4:
                    rejeitar();
                    break;
                case 5:
                    registrarDevolucao();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void solicitarAdocao() {
        System.out.println("\n===== SOLICITAR ADOÇÃO =====");

        List<Adotante> adotantes =
            sistema.getUsuarioServico().listarPorTipo(Adotante.class)
                .stream()
                .map(usuario -> (Adotante) usuario)
                .toList();

        if (adotantes.isEmpty()) {
            System.out.println("Nenhum adotante cadastrado.");
            return;
        }

        System.out.println("\n===== ADOTANTES =====");

        for (int i = 0; i < adotantes.size(); i++) {
            System.out.println((i + 1) + " - "
                    + adotantes.get(i).getNome()
                    + " | CPF: " + adotantes.get(i).getCpf());
        }

        int escolhaAdotante = Entrada.lerInteiro("Selecione o adotante: ");

        if (escolhaAdotante < 1 || escolhaAdotante > adotantes.size()) {
            System.out.println("Seleção inválida!");
            return;
        }

        Adotante adotante = adotantes.get(escolhaAdotante - 1);

        List<Animal> animaisDisponiveis =
            sistema.getAnimalServico().filtrarPorStatus(StatusAdocao.DISPONIVEL);

        if (animaisDisponiveis.isEmpty()) {
            System.out.println("Não há animais disponíveis para adoção.");
            return;
        }

        System.out.println("\n===== ANIMAIS DISPONÍVEIS =====");

        for (int i = 0; i < animaisDisponiveis.size(); i++) {
            Animal animal = animaisDisponiveis.get(i);

            System.out.println((i + 1) + " - " + animal.getNome()
                    + " | Espécie: " + animal.getEspecie()
                    + " | Porte: " + animal.getPorte()
                    + " | ID: " + animal.getId());
        }

        int escolhaAnimal = Entrada.lerInteiro("Selecione o animal: ");

        if (escolhaAnimal < 1 || escolhaAnimal > animaisDisponiveis.size()) {
            System.out.println("Seleção inválida!");
            return;
        }

        Animal animal = animaisDisponiveis.get(escolhaAnimal - 1);

        try {
            sistema.getAdocaoServico().solicitar(animal, adotante);
            System.out.println("Solicitação de adoção registrada!");

        } catch (Exception e) {
            System.out.println("Erro ao solicitar adoção: " + e.getMessage());
        }
    }

    private void listarSolicitacoes() {
        System.out.println("\n===== SOLICITAÇÕES DE ADOÇÃO =====");

        List<SolicitacaoAdocao> solicitacoes =
            sistema.getAdocaoServico().listar();

        if (solicitacoes.isEmpty()) {
            System.out.println("Nenhuma solicitação cadastrada.");
            return;
        }

        for (int i = 0; i < solicitacoes.size(); i++) {
            SolicitacaoAdocao solicitacao = solicitacoes.get(i);

            System.out.println("\n----------------------------");
            System.out.println("ID: " + (i + 1));
            System.out.println("Adotante: "
                    + solicitacao.getSolicitante().getNome());
            System.out.println("CPF: "
                    + solicitacao.getSolicitante().getCpf());
            System.out.println("Animal: "
                    + solicitacao.getAnimal().getNome());
            System.out.println("Data: "
                    + solicitacao.getDataSolicitacao());
            System.out.println("Status: "
                    + solicitacao.getStatus());
            System.out.println("----------------------------");
        }
    }

    private Administrador selecionarAdministrador() {
        List<Administrador> administradores =
            sistema.getUsuarioServico().listarPorTipo(Administrador.class)
                .stream()
                .map(usuario -> (Administrador) usuario)
                .toList();

        if (administradores.isEmpty()) {
            System.out.println("Nenhum administrador cadastrado.");
            return null;
        }

        System.out.println("\n===== ADMINISTRADORES =====");

        for (int i = 0; i < administradores.size(); i++) {
            System.out.println((i + 1) + " - "
                    + administradores.get(i).getNome());
        }

        int escolha = Entrada.lerInteiro("Selecione o administrador: ");

        if (escolha < 1 || escolha > administradores.size()) {
            System.out.println("Seleção inválida!");
            return null;
        }

        return administradores.get(escolha - 1);
    }

    private void aprovar() {
        System.out.println("\n===== APROVAR ADOÇÃO =====");

        listarSolicitacoesPendentes();

        int id = Entrada.lerInteiro("ID da solicitação: ");

        Administrador administrador = selecionarAdministrador();

        if (administrador == null) {
            return;
        }

        try {
            sistema.getAdocaoServico().aprovar(id, administrador);
            System.out.println("Adoção aprovada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao aprovar: " + e.getMessage());
        }
    }

    private void rejeitar() {
        System.out.println("\n===== REJEITAR ADOÇÃO =====");

        listarSolicitacoesPendentes();

        int id = Entrada.lerInteiro("ID da solicitação: ");

        Administrador administrador = selecionarAdministrador();

        if (administrador == null) {
            return;
        }

        try {
            sistema.getAdocaoServico().rejeitar(id, administrador);
            System.out.println("Solicitação rejeitada!");

        } catch (Exception e) {
            System.out.println("Erro ao rejeitar: " + e.getMessage());
        }
    }

    private void registrarDevolucao() {
        System.out.println("\n===== REGISTRAR DEVOLUÇÃO =====");

        listarSolicitacoesAprovadas();

        int id = Entrada.lerInteiro("ID da solicitação: ");

        try {
            sistema.getAdocaoServico().registrarDevolucao(id);
            System.out.println("Devolução registrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao registrar devolução: "
                    + e.getMessage());
        }
    }

    private void listarSolicitacoesPendentes() {
        List<SolicitacaoAdocao> solicitacoes =
            sistema.getAdocaoServico().listar();

        System.out.println("\n===== SOLICITAÇÕES PENDENTES =====");

        boolean encontrou = false;

        for (int i = 0; i < solicitacoes.size(); i++) {
            SolicitacaoAdocao solicitacao = solicitacoes.get(i);

            if (solicitacao.getStatus() == StatusSolicitacao.PENDENTE) {
                System.out.println("ID: " + (i + 1)
                        + " | Adotante: "
                        + solicitacao.getSolicitante().getNome()
                        + " | Animal: "
                        + solicitacao.getAnimal().getNome());

                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma solicitação pendente.");
        }
    }

    private void listarSolicitacoesAprovadas() {
        List<SolicitacaoAdocao> solicitacoes =
            sistema.getAdocaoServico().listar();

        System.out.println("\n===== ADOÇÕES APROVADAS =====");

        boolean encontrou = false;

        for (int i = 0; i < solicitacoes.size(); i++) {
            SolicitacaoAdocao solicitacao = solicitacoes.get(i);

            if (solicitacao.getStatus() == StatusSolicitacao.APROVADA) {
                System.out.println("ID: " + (i + 1)
                        + " | Adotante: "
                        + solicitacao.getSolicitante().getNome()
                        + " | Animal: "
                        + solicitacao.getAnimal().getNome());

                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma adoção aprovada.");
        }
    }
}