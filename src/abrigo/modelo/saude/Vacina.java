package abrigo.modelo.saude;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Vacina {

    private final String nome;
    private final LocalDate dataAplicacao;
    private final LocalDate proximaDose; // pode ser null

    public Vacina(String nome, LocalDate dataAplicacao, LocalDate proximaDose) {
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
        this.proximaDose = proximaDose;
    }

    // Usado nas notificações: vacina com próxima dose em até "dias" dias (ou já vencida)
    public boolean venceEm(int dias) {
        if (proximaDose == null) {
            return false;
        }
        long restantes = ChronoUnit.DAYS.between(LocalDate.now(), proximaDose);
        return restantes <= dias;
    }

    public String getNome() { return nome; }
    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public LocalDate getProximaDose() { return proximaDose; }
}
