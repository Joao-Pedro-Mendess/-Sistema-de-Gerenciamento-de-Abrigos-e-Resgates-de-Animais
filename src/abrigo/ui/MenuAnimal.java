
package abrigo.ui;

import java.time.LocalDate;
import java.util.List;

import abrigo.modelo.animais.Animal;
import abrigo.modelo.animais.Porte;
import abrigo.modelo.animais.EstadoSaude;
import abrigo.modelo.animais.StatusAdocao;
import abrigo.servico.AnimalServico;
import abrigo.servico.SistemaAbrigo;
import abrigo.util.Entrada;

public class MenuAnimal {

    private SistemaAbrigo sistema;
    private AnimalServico animalServico;

    public MenuAnimal(SistemaAbrigo sistema) {
        this.sistema = sistema;
        this.animalServico = sistema.getAnimalServico();
    }

    public void exibir() {
        int opcao;

        do {
            System.out.println("\n===== MENU DE ANIMAIS =====");
            System.out.println("1 - Cadastrar animal");
            System.out.println("2 - Listar animais");
            System.out.println("3 - Buscar animal");
            System.out.println("4 - Filtrar animais");
            System.out.println("5 - Editar animal");
            System.out.println("6 - Remover animal");
            System.out.println("7 - Disponibilizar para adoção");
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
                    filtrar();
                    break;
                case 5:
                    editar();
                    break;
                case 6:
                    remover();
                    break;
                case 7:
                    disponibilizar();
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
        System.out.println("\n===== CADASTRO DE ANIMAL =====");

        String nome = Entrada.lerTexto("Nome: ");
        String especie = Entrada.lerTexto("Espécie: ");
        String raca = Entrada.lerTexto("Raça: ");
        int idade = Entrada.lerInteiro("Idade: ");

        Porte porte = selecionarPorte();
        EstadoSaude estadoSaude = selecionarEstadoSaude();

        String data = Entrada.lerTexto("Data do resgate (AAAA-MM-DD): ");

        try {
            LocalDate dataResgate = LocalDate.parse(data);

            Animal animal = new Animal(
                nome,
                especie,
                raca,
                idade,
                porte,
                estadoSaude,
                dataResgate
            );

            animalServico.cadastrar(animal);
            System.out.println("Animal cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar animal: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n===== ANIMAIS CADASTRADOS =====");

        List<Animal> animais = animalServico.listar();

        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return;
        }

        for (Animal animal : animais) {
            exibirAnimal(animal);
        }
    }

    private void buscar() {
        System.out.println("\n===== BUSCAR ANIMAL =====");
        System.out.println("1 - Buscar por ID");
        System.out.println("2 - Buscar por nome");

        int opcao = Entrada.lerInteiro("Escolha: ");

        Animal animal = null;

        if (opcao == 1) {
            int id = Entrada.lerInteiro("ID do animal: ");
            animal = animalServico.buscarPorId(id);

        } else if (opcao == 2) {
            String nome = Entrada.lerTexto("Nome do animal: ");
            animal = animalServico.buscarPorNome(nome);

        } else {
            System.out.println("Opção inválida!");
            return;
        }

        if (animal == null) {
            System.out.println("Animal não encontrado.");
        } else {
            exibirAnimal(animal);
        }
    }

    private void filtrar() {
        System.out.println("\n===== FILTRAR ANIMAIS =====");
        System.out.println("1 - Por espécie");
        System.out.println("2 - Por porte");
        System.out.println("3 - Por status");

        int opcao = Entrada.lerInteiro("Escolha: ");

        List<Animal> resultado;

        switch (opcao) {
            case 1:
                String especie = Entrada.lerTexto("Espécie: ");
                resultado = animalServico.filtrarPorEspecie(especie);
                break;

            case 2:
                Porte porte = selecionarPorte();
                resultado = animalServico.filtrarPorPorte(porte);
                break;

            case 3:
                StatusAdocao status = selecionarStatus();
                resultado = animalServico.filtrarPorStatus(status);
                break;

            default:
                System.out.println("Opção inválida!");
                return;
        }

        if (resultado.isEmpty()) {
            System.out.println("Nenhum animal encontrado.");
        } else {
            for (Animal animal : resultado) {
                exibirAnimal(animal);
            }
        }
    }

    private void editar() {
        System.out.println("\n===== EDITAR ANIMAL =====");

        int id = Entrada.lerInteiro("ID do animal: ");

        Animal animal = animalServico.buscarPorId(id);

        if (animal == null) {
            System.out.println("Animal não encontrado.");
            return;
        }

        System.out.println("Animal encontrado: " + animal.getNome());

        String nome = Entrada.lerTexto("Novo nome: ");
        String especie = Entrada.lerTexto("Nova espécie: ");
        String raca = Entrada.lerTexto("Nova raça: ");
        int idade = Entrada.lerInteiro("Nova idade: ");
        Porte porte = selecionarPorte();

        try {
            animalServico.editar(
                id,
                nome,
                especie,
                raca,
                idade,
                porte
            );

            System.out.println("Animal editado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao editar: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\n===== REMOVER ANIMAL =====");

        int id = Entrada.lerInteiro("ID do animal: ");

        Animal animal = animalServico.buscarPorId(id);

        if (animal == null) {
            System.out.println("Animal não encontrado.");
            return;
        }

        boolean confirmar = Entrada.lerConfirmacao(
            "Deseja realmente remover " + animal.getNome() + "? (s/n): "
        );

        if (!confirmar) {
            System.out.println("Remoção cancelada.");
            return;
        }

        try {
            boolean removido = animalServico.remover(id);

            if (removido) {
                System.out.println("Animal removido com sucesso!");
            }

        } catch (Exception e) {
            System.out.println("Erro ao remover: " + e.getMessage());
        }
    }

    private void disponibilizar() {
        System.out.println("\n===== DISPONIBILIZAR PARA ADOÇÃO =====");

        int id = Entrada.lerInteiro("ID do animal: ");

        try {
            animalServico.disponibilizarParaAdocao(id);
            System.out.println("Animal disponibilizado para adoção!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private Porte selecionarPorte() {
        Porte[] portes = Porte.values();

        System.out.println("\n===== PORTE =====");

        for (int i = 0; i < portes.length; i++) {
            System.out.println((i + 1) + " - " + portes[i]);
        }

        int opcao;

        do {
            opcao = Entrada.lerInteiro("Escolha o porte: ");
        } while (opcao < 1 || opcao > portes.length);

        return portes[opcao - 1];
    }

    private EstadoSaude selecionarEstadoSaude() {
        EstadoSaude[] estados = EstadoSaude.values();

        System.out.println("\n===== ESTADO DE SAÚDE =====");

        for (int i = 0; i < estados.length; i++) {
            System.out.println((i + 1) + " - " + estados[i]);
        }

        int opcao;

        do {
            opcao = Entrada.lerInteiro("Escolha o estado de saúde: ");
        } while (opcao < 1 || opcao > estados.length);

        return estados[opcao - 1];
    }

    private StatusAdocao selecionarStatus() {
        StatusAdocao[] status = StatusAdocao.values();

        System.out.println("\n===== STATUS =====");

        for (int i = 0; i < status.length; i++) {
            System.out.println((i + 1) + " - " + status[i]);
        }

        int opcao;

        do {
            opcao = Entrada.lerInteiro("Escolha o status: ");
        } while (opcao < 1 || opcao > status.length);

        return status[opcao - 1];
    }

    private void exibirAnimal(Animal animal) {
        System.out.println("\n----------------------------");
        System.out.println("ID: " + animal.getId());
        System.out.println("Nome: " + animal.getNome());
        System.out.println("Espécie: " + animal.getEspecie());
        System.out.println("Raça: " + animal.getRaca());
        System.out.println("Idade: " + animal.getIdade());
        System.out.println("Porte: " + animal.getPorte());
        System.out.println("Saúde: " + animal.getEstadoSaude());
        System.out.println("Data do resgate: " + animal.getDataResgate());
        System.out.println("Status: " + animal.getStatus());
        System.out.println("----------------------------");
    }
}