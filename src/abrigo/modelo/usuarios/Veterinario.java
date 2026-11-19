package abrigo.modelo.usuarios;


public class Veterinario extends Usuario {

    private String crmv;
    private String especialidade;

    public Veterinario(String nome, String cpf, String email, String crmv, String especialidade) {
        super(nome, cpf, email);
        this.crmv = crmv;
        this.especialidade = especialidade;
    }

    public String getCrmv() {
        return crmv;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
