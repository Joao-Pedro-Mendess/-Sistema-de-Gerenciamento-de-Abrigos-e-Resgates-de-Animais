package abrigo.excecoes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VacinaDuplicadaException extends Exception {

    public VacinaDuplicadaException(String nomeVacina, LocalDate data) {
        super("A vacina " + nomeVacina + " já foi registrada em "
                + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".");
    }
}
