package abrigo.servico;

import java.util.ArrayList;
import java.util.List;

import abrigo.modelo.animais.Animal;
import abrigo.modelo.animais.Porte;
import abrigo.modelo.animais.StatusAdocao;
import abrigo.excecoes.AnimalCriticoException;

public class AnimalServico {

    private List<Animal> animais;

    public AnimalServico() {
        animais = new ArrayList<>();
    }

    public void cadastrar(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal inválido!");
        }

        animais.add(animal);
    }

    public List<Animal> listar() {
        return new ArrayList<>(animais);
    }

    public Animal buscarPorId(int id) {
        for (Animal animal : animais) {
            if (animal.getId() == id) {
                return animal;
            }
        }

        return null;
    }

    public Animal buscarPorNome(String nome) {
        for (Animal animal : animais) {
            if (animal.getNome().equalsIgnoreCase(nome)) {
                return animal;
            }
        }

        return null;
    }

    public List<Animal> filtrarPorEspecie(String especie) {
        List<Animal> resultado = new ArrayList<>();

        for (Animal animal : animais) {
            if (animal.getEspecie().equalsIgnoreCase(especie)) {
                resultado.add(animal);
            }
        }

        return resultado;
    }

    public List<Animal> filtrarPorPorte(Porte porte) {
        List<Animal> resultado = new ArrayList<>();

        for (Animal animal : animais) {
            if (animal.getPorte() == porte) {
                resultado.add(animal);
            }
        }

        return resultado;
    }

    public List<Animal> filtrarPorStatus(StatusAdocao status) {
        List<Animal> resultado = new ArrayList<>();

        for (Animal animal : animais) {
            if (animal.getStatus() == status) {
                resultado.add(animal);
            }
        }

        return resultado;
    }

    public boolean remover(int id) {
        Animal animal = buscarPorId(id);

        if (animal == null) {
            return false;
        }

        if (animal.getStatus() == StatusAdocao.ADOTADO) {
            throw new IllegalArgumentException(
                "Animal adotado não pode ser removido!"
            );
        }

        animais.remove(animal);
        return true;
    }

    public void editar(int id, String novoNome, String novaEspecie,
            String novaRaca, int novaIdade, Porte novoPorte) {

        Animal animal = buscarPorId(id);

        if (animal == null) {
            throw new IllegalArgumentException("Animal não encontrado!");
        }

        animal.setNome(novoNome);
        animal.setEspecie(novaEspecie);
        animal.setRaca(novaRaca);
        animal.setIdade(novaIdade);
        animal.setPorte(novoPorte);
    }

    public void disponibilizarParaAdocao(int id)
            throws AnimalCriticoException {

        Animal animal = buscarPorId(id);

        if (animal == null) {
            throw new IllegalArgumentException("Animal não encontrado!");
        }

        animal.disponibilizarParaAdocao();
    }
}