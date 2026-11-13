package abrigo.excecoes;

public class AnimalIndisponivelException extends Exception {
    public AnimalIndisponivelException(String nomeAnimal){
        super("O animal " + nomeAnimal + " não está disponível para adoção.");
    }
}
