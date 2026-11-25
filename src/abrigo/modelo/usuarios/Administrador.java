package abrigo.modelo.usuarios;

public class Administrador extends Usuario {

    private String cargo;

    public Administrador(String nome, String cpf, String email, String cargo) {
        super(nome, cpf, email);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}