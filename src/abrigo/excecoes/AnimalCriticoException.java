package abrigo.excecoes;

public class AnimalCriticoException extends Exception{
    public AnimalCriticoException(String nomeAnimal){
        super("O animal " + nomeAnimal + " está em tratamento crítico e não pode ser liberado para adoção.");
    }
}
