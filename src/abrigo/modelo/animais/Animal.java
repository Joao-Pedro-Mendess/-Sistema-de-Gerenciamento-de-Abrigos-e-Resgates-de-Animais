package abrigo.modelo.animais;

import abrigo.excecoes.AnimalCriticoException;
import abrigo.excecoes.AnimalIndisponivelException;
import abrigo.modelo.saude.HistoricoMedico;

import java.time.LocalDate;

public class Animal {

    private static int proximoId = 1;

    private final int id;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private Porte porte;
    private EstadoSaude estadoSaude;
    private final LocalDate dataResgate;
    private StatusAdocao status;
    private final HistoricoMedico historicoMedico = new HistoricoMedico();

    public Animal(String nome, String especie, String raca, int idade,
                  Porte porte, EstadoSaude estadoSaude, LocalDate dataResgate) {
        this.id = proximoId++;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.porte = porte;
        this.estadoSaude = estadoSaude;
        this.dataResgate = dataResgate;
        this.status = StatusAdocao.INDISPONIVEL;
    }

    // Regra 6: animal em tratamento crítico não pode ser disponibilizado
    public void disponibilizarParaAdocao() throws AnimalCriticoException {
        if (estadoSaude == EstadoSaude.CRITICO) {
            throw new AnimalCriticoException(nome);
        }
        this.status = StatusAdocao.DISPONIVEL;
    }

    // Chamado quando alguém solicita a adoção (regra 3)
    public void iniciarProcessoAdocao() throws AnimalIndisponivelException {
        if (status != StatusAdocao.DISPONIVEL) {
            throw new AnimalIndisponivelException(nome);
        }
        this.status = StatusAdocao.EM_PROCESSO;
    }

    // Chamado quando o administrador aprova a adoção (regra 5)
    public void concluirAdocao() throws AnimalIndisponivelException {
        if (status != StatusAdocao.EM_PROCESSO) {
            throw new AnimalIndisponivelException(nome);
        }
        this.status = StatusAdocao.ADOTADO;
    }

    // Adoção rejeitada ou animal devolvido
    public void reabrirParaAdocao() {
        this.status = StatusAdocao.DISPONIVEL;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getRaca() { return raca; }
    public int getIdade() { return idade; }
    public Porte getPorte() { return porte; }
    public EstadoSaude getEstadoSaude() { return estadoSaude; }
    public LocalDate getDataResgate() { return dataResgate; }
    public StatusAdocao getStatus() { return status; }
    public HistoricoMedico getHistoricoMedico() { return historicoMedico; }

    public void setNome(String nome) { this.nome = nome; }
    public void setEspecie(String especie) { this.especie = especie; }
    public void setRaca(String raca) { this.raca = raca; }
    public void setIdade(int idade) { this.idade = idade; }
    public void setPorte(Porte porte) { this.porte = porte; }
    public void setEstadoSaude(EstadoSaude estadoSaude) { this.estadoSaude = estadoSaude; }
}
