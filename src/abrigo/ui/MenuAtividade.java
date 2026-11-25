
package abrigo.ui;

import java.time.LocalDate;
import java.util.List;

import abrigo.modelo.eventos.Atividade;
import abrigo.modelo.usuarios.Usuario;
import abrigo.modelo.usuarios.Voluntario;
import abrigo.servico.SistemaAbrigo;
import abrigo.util.Entrada;

public class MenuAtividade {

    private SistemaAbrigo sistema;

    public MenuAtividade(SistemaAbrigo sistema) {
        this.sistema = sistema;
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n===== MENU DE ATIVIDADES =====");
            System.out.println("1 - Cadastrar atividade");
            System.out.println("2 - Listar atividades");
            System.out.println("3 - Buscar atividade");
            System.out.println("4 - Editar atividade");
            System.out.println("5 - Remover atividade");
            System.out.println("6 - Inscrever voluntário");
            System.out.println("7 - Cancelar inscrição");
            System.out.println("0 - Voltar");

            opcao = Entrada.lerInteiro("Escolha: ");

            switch (opcao) {
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    editar();
                    break;
                case 5:
                    remover();
                    break;
                case 6:
                    inscrever();
                    break;
                case 7:
                    cancelarInscricao();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n===== CADASTRAR ATIVIDADE =====");

        String nome = Entrada.lerTexto("Nome: ");
        String descricao = Entrada.lerTexto("Descrição: ");
        String dataTexto = Entrada.lerTexto("Data (AAAA-MM-DD): ");
        String local = Entrada.lerTexto("Local: ");
        int vagas = Entrada.lerInteiro("Quantidade de vagas: ");

        try {
            LocalDate data = LocalDate.parse(dataTexto);

            if (vagas <= 0) {
                System.out.println("A quantidade de vagas deve ser positiva.");
                return;
            }

            Atividade atividade = new Atividade(
                nome, descricao, data, local, vagas
            );

            sistema.getAtividadeServico().cadastrar(atividade);

            System.out.println("Atividade cadastrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void listar() {
        List<Atividade> atividades =
            sistema.getAtividadeServico().listar();

        if (atividades.isEmpty()) {
            System.out.println("Nenhuma atividade cadastrada.");
            return;
        }

        System.out.println("\n===== ATIVIDADES =====");

        for (Atividade atividade : atividades) {
            exibirAtividade(atividade);
        }
    }

    private void buscar() {
        int id = Entrada.lerInteiro("ID da atividade: ");

        Atividade atividade =
            sistema.getAtividadeServico().buscarPorId(id);

        if (atividade == null) {
            System.out.println("Atividade não encontrada.");
        } else {
            exibirAtividade(atividade);
        }
    }

    private void editar() {
        System.out.println("\n===== EDITAR ATIVIDADE =====");

        int id = Entrada.lerInteiro("ID da atividade: ");

        Atividade atividade =
            sistema.getAtividadeServico().buscarPorId(id);

        if (atividade == null) {
            System.out.println("Atividade não encontrada.");
            return;
        }

        String nome = Entrada.lerTexto("Novo nome: ");
        String descricao = Entrada.lerTexto("Nova descrição: ");
        String dataTexto = Entrada.lerTexto("Nova data (AAAA-MM-DD): ");
        String local = Entrada.lerTexto("Novo local: ");
        int vagas = Entrada.lerInteiro("Nova quantidade de vagas: ");

        try {
            LocalDate data = LocalDate.parse(dataTexto);

            sistema.getAtividadeServico().editar(
                id, nome, descricao, data, local, vagas
            );

            System.out.println("Atividade editada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao editar: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\n===== REMOVER ATIVIDADE =====");

        int id = Entrada.lerInteiro("ID da atividade: ");

        Atividade atividade =
            sistema.getAtividadeServico().buscarPorId(id);

        if (atividade == null) {
            System.out.println("Atividade não encontrada.");
            return;
        }

        boolean confirmar = Entrada.lerConfirmacao(
            "Deseja remover " + atividade.getNome() + "? (s/n): "
        );

        if (!confirmar) {
            System.out.println("Remoção cancelada.");
            return;
        }

        boolean removida = sistema.getAtividadeServico().remover(id);

        if (removida) {
            System.out.println("Atividade removida com sucesso!");
        }
    }

    private void inscrever() {
        System.out.println("\n===== INSCRIÇÃO EM ATIVIDADE =====");

        int id = Entrada.lerInteiro("ID da atividade: ");

        Atividade atividade =
            sistema.getAtividadeServico().buscarPorId(id);

        if (atividade == null) {
            System.out.println("Atividade não encontrada.");
            return;
        }

        List<Usuario> usuarios =
            sistema.getUsuarioServico().listarPorTipo(Voluntario.class);

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum voluntário cadastrado.");
            return;
        }

        System.out.println("\n===== VOLUNTÁRIOS =====");

        for (int i = 0; i < usuarios.size(); i++) {
            Voluntario voluntario = (Voluntario) usuarios.get(i);

            System.out.println((i + 1) + " - "
                    + voluntario.getNome()
                    + " | CPF: " + voluntario.getCpf());
        }

        int escolha = Entrada.lerInteiro("Selecione o voluntário: ");

        if (escolha < 1 || escolha > usuarios.size()) {
            System.out.println("Seleção inválida!");
            return;
        }

        Voluntario voluntario =
            (Voluntario) usuarios.get(escolha - 1);

        try {
            sistema.getAtividadeServico().inscreverVoluntario(
                id, voluntario
            );

            System.out.println("Inscrição realizada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro na inscrição: " + e.getMessage());
        }
    }

    private void cancelarInscricao() {
        System.out.println("\n===== CANCELAR INSCRIÇÃO =====");

        int id = Entrada.lerInteiro("ID da atividade: ");

        Atividade atividade =
            sistema.getAtividadeServico().buscarPorId(id);

        if (atividade == null) {
            System.out.println("Atividade não encontrada.");
            return;
        }

        List<Voluntario> inscritos = atividade.getVoluntarios();

        if (inscritos.isEmpty()) {
            System.out.println("Não há voluntários inscritos.");
            return;
        }

        System.out.println("\n===== VOLUNTÁRIOS INSCRITOS =====");

        for (Voluntario voluntario : inscritos) {
            System.out.println("Nome: " + voluntario.getNome()
                    + " | CPF: " + voluntario.getCpf());
        }

        String cpf = Entrada.lerTexto("CPF do voluntário: ");

        try {
            sistema.getAtividadeServico().cancelarInscricao(id, cpf);
            System.out.println("Inscrição cancelada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cancelar: " + e.getMessage());
        }
    }

    private void exibirAtividade(Atividade atividade) {
        System.out.println("\n----------------------------");
        System.out.println("ID: " + atividade.getId());
        System.out.println("Nome: " + atividade.getNome());
        System.out.println("Descrição: " + atividade.getDescricao());
        System.out.println("Data: " + atividade.getData());
        System.out.println("Local: " + atividade.getLocal());
        System.out.println("Vagas: " + atividade.getVagas());
        System.out.println("Inscritos: "
                + atividade.getVoluntarios().size());

        System.out.println("Voluntários:");

        if (atividade.getVoluntarios().isEmpty()) {
            System.out.println("Nenhum inscrito.");
        } else {
            for (Voluntario voluntario : atividade.getVoluntarios()) {
                System.out.println("- " + voluntario.getNome());
            }
        }

        System.out.println("----------------------------");
    }
}