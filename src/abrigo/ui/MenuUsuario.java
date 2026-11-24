package abrigo.ui;

import abrigo.servico.SistemaAbrigo;
import abrigo.util.Entrada;
import abrigo.modelo.usuarios.*;

public class MenuUsuario {

    private SistemaAbrigo sistema;

    public MenuUsuario(SistemaAbrigo sistema) {
        this.sistema = sistema;
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n===== GERENCIAMENTO DE USUÁRIOS =====");
            System.out.println("1 - Cadastrar adotante");
            System.out.println("2 - Cadastrar voluntário");
            System.out.println("3 - Cadastrar veterinário");
            System.out.println("4 - Cadastrar administrador");
            System.out.println("5 - Listar usuários");
            System.out.println("0 - Voltar");

            opcao = Entrada.lerInteiro("Escolha: ");

            try {
                switch (opcao) {
                    case 1:
                        cadastrarAdotante();
                        break;
                    case 2:
                        cadastrarVoluntario();
                        break;
                    case 3:
                        cadastrarVeterinario();
                        break;
                    case 4:
                        cadastrarAdministrador();
                        break;
                    case 5:
                        listarUsuarios();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);
    }

    private void cadastrarAdotante() {
        String nome = Entrada.lerTexto("Nome: ");
        String cpf = Entrada.lerTexto("CPF: ");
        String email = Entrada.lerTexto("E-mail: ");
        String moradia = Entrada.lerTexto("Tipo de moradia: ");
        String preferencias = Entrada.lerTexto("Preferências: ");

        Adotante adotante = new Adotante(
            nome, cpf, email, moradia, preferencias
        );

        sistema.getUsuarioServico().cadastrar(adotante);
        System.out.println("Adotante cadastrado com sucesso!");
    }

    private void cadastrarVoluntario() {
        String nome = Entrada.lerTexto("Nome: ");
        String cpf = Entrada.lerTexto("CPF: ");
        String email = Entrada.lerTexto("E-mail: ");
        String disponibilidade = Entrada.lerTexto("Disponibilidade: ");

        Voluntario voluntario = new Voluntario(
            nome, cpf, email, disponibilidade
        );

        sistema.getUsuarioServico().cadastrar(voluntario);
        System.out.println("Voluntário cadastrado com sucesso!");
    }

    private void cadastrarVeterinario() {
        String nome = Entrada.lerTexto("Nome: ");
        String cpf = Entrada.lerTexto("CPF: ");
        String email = Entrada.lerTexto("E-mail: ");
        String crmv = Entrada.lerTexto("CRMV: ");
        String especialidade = Entrada.lerTexto("Especialidade: ");

        Veterinario veterinario = new Veterinario(
            nome, cpf, email, crmv, especialidade
        );

        sistema.getUsuarioServico().cadastrar(veterinario);
        System.out.println("Veterinário cadastrado com sucesso!");
    }

    private void cadastrarAdministrador() {
        String nome = Entrada.lerTexto("Nome: ");
        String cpf = Entrada.lerTexto("CPF: ");
        String email = Entrada.lerTexto("E-mail: ");
        String cargo = Entrada.lerTexto("Cargo: ");

        Administrador administrador = new Administrador(
            nome, cpf, email, cargo
        );

        sistema.getUsuarioServico().cadastrar(administrador);
        System.out.println("Administrador cadastrado com sucesso!");
    }

    private void listarUsuarios() {
        if (sistema.getUsuarioServico().listar().isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        for (Usuario usuario : sistema.getUsuarioServico().listar()) {
            System.out.println("-----------------------");
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("CPF: " + usuario.getCpf());
            System.out.println("E-mail: " + usuario.getEmail());
            System.out.println("Tipo: " + usuario.getClass().getSimpleName());
        }
    }
}