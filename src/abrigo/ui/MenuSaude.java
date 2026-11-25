package abrigo.ui;

import java.time.LocalDate;
import java.util.List;

import abrigo.modelo.animais.Animal;
import abrigo.modelo.saude.Vacina;
import abrigo.modelo.saude.Consulta;
import abrigo.modelo.usuarios.Usuario;
import abrigo.modelo.usuarios.Veterinario;
import abrigo.servico.SistemaAbrigo;
import abrigo.util.Entrada;

public class MenuSaude {

    private SistemaAbrigo sistema;

    public MenuSaude(SistemaAbrigo sistema) {
        this.sistema = sistema;
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n===== MENU DE SAÚDE =====");
            System.out.println("1 - Registrar vacina");
            System.out.println("2 - Registrar consulta");
            System.out.println("3 - Visualizar histórico médico");
            System.out.println("0 - Voltar");

            opcao = Entrada.lerInteiro("Escolha: ");

            switch (opcao) {
                case 1:
                    registrarVacina();
                    break;
                case 2:
                    registrarConsulta();
                    break;
                case 3:
                    visualizarHistorico();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private Animal selecionarAnimal() {
        List<Animal> animais = sistema.getAnimalServico().listar();

        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return null;
        }

        System.out.println("\n===== ANIMAIS =====");

        for (Animal animal : animais) {
            System.out.println("ID: " + animal.getId()
                    + " | Nome: " + animal.getNome()
                    + " | Espécie: " + animal.getEspecie());
        }

        int id = Entrada.lerInteiro("ID do animal: ");

        Animal animal = sistema.getAnimalServico().buscarPorId(id);

        if (animal == null) {
            System.out.println("Animal não encontrado.");
        }

        return animal;
    }

    private Veterinario selecionarVeterinario() {
        List<Usuario> usuarios =
            sistema.getUsuarioServico().listarPorTipo(Veterinario.class);

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum veterinário cadastrado.");
            return null;
        }

        System.out.println("\n===== VETERINÁRIOS =====");

        for (int i = 0; i < usuarios.size(); i++) {
            Veterinario veterinario = (Veterinario) usuarios.get(i);

            System.out.println((i + 1) + " - "
                    + veterinario.getNome()
                    + " | Especialidade: "
                    + veterinario.getEspecialidade());
        }

        int escolha = Entrada.lerInteiro("Selecione o veterinário: ");

        if (escolha < 1 || escolha > usuarios.size()) {
            System.out.println("Seleção inválida!");
            return null;
        }

        return (Veterinario) usuarios.get(escolha - 1);
    }

    private void registrarVacina() {
        System.out.println("\n===== REGISTRAR VACINA =====");

        Animal animal = selecionarAnimal();

        if (animal == null) {
            return;
        }

        Veterinario veterinario = selecionarVeterinario();

        if (veterinario == null) {
            return;
        }

        String nome = Entrada.lerTexto("Nome da vacina: ");
        String dataAplicacaoTexto =
            Entrada.lerTexto("Data da aplicação (AAAA-MM-DD): ");

        String proximaDoseTexto =
            Entrada.lerTexto("Data da próxima dose (ou deixe vazio): ");

        try {
            LocalDate dataAplicacao = LocalDate.parse(dataAplicacaoTexto);

            LocalDate proximaDose = null;

            if (!proximaDoseTexto.trim().isEmpty()) {
                proximaDose = LocalDate.parse(proximaDoseTexto);
            }

            Vacina vacina = new Vacina(
                nome, dataAplicacao, proximaDose
            );

            sistema.getSaudeServico().registrarVacina(
                animal, vacina, veterinario
            );

            System.out.println("Vacina registrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao registrar vacina: "
                    + e.getMessage());
        }
    }

    private void registrarConsulta() {
        System.out.println("\n===== REGISTRAR CONSULTA =====");

        Animal animal = selecionarAnimal();

        if (animal == null) {
            return;
        }

        Veterinario veterinario = selecionarVeterinario();

        if (veterinario == null) {
            return;
        }

        String descricao = Entrada.lerTexto("Descrição da consulta: ");
        String tratamento = Entrada.lerTexto("Tratamento: ");

        try {
            sistema.getSaudeServico().registrarConsulta(
                animal, descricao, tratamento, veterinario
            );

            System.out.println("Consulta registrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao registrar consulta: "
                    + e.getMessage());
        }
    }

    private void visualizarHistorico() {
        System.out.println("\n===== HISTÓRICO MÉDICO =====");

        Animal animal = selecionarAnimal();

        if (animal == null) {
            return;
        }

        System.out.println("\nAnimal: " + animal.getNome());

        List<Vacina> vacinas =
            animal.getHistoricoMedico().getVacinas();

        List<Consulta> consultas =
            animal.getHistoricoMedico().getConsultas();

        System.out.println("\n===== VACINAS =====");

        if (vacinas.isEmpty()) {
            System.out.println("Nenhuma vacina registrada.");
        } else {
            for (Vacina vacina : vacinas) {
                System.out.println("----------------------------");
                System.out.println("Vacina: " + vacina.getNome());
                System.out.println("Aplicação: "
                        + vacina.getDataAplicacao());
                System.out.println("Próxima dose: "
                        + (vacina.getProximaDose() == null
                            ? "Não informada"
                            : vacina.getProximaDose()));
            }
        }

        System.out.println("\n===== CONSULTAS =====");

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta registrada.");
        } else {
            for (Consulta consulta : consultas) {
                System.out.println("----------------------------");
                System.out.println("Data: " + consulta.getData());
                System.out.println("Descrição: "
                        + consulta.getDescricao());
                System.out.println("Tratamento: "
                        + consulta.getTratamento());
                System.out.println("Veterinário: "
                        + consulta.getVeterinario().getNome());
            }
        }

        System.out.println("----------------------------");
    }
}