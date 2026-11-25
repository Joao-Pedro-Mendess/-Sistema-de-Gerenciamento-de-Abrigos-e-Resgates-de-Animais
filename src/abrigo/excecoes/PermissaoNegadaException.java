package abrigo.excecoes;

public class PermissaoNegadaException extends Exception {

    public PermissaoNegadaException(String nomeUsuario) {
        super("O usuário " + nomeUsuario + " não tem permissão para alterar informações médicas.");
    }
}
