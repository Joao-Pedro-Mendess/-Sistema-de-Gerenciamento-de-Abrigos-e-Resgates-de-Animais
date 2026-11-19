package abrigo.modelo.saude;

import abrigo.excecoes.PermissaoNegadaException;
import abrigo.excecoes.VacinaDuplicadaException;
import abrigo.modelo.usuarios.Usuario;
import abrigo.modelo.usuarios.Veterinario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoricoMedico {

    private final List<Vacina> vacinas = new ArrayList<>();
    private final List<Consulta> consultas = new ArrayList<>();

    // Regras 2 e 4: sem vacina duplicada na mesma data; só veterinário altera
    public void registrarVacina(Vacina vacina, Usuario autor)
            throws VacinaDuplicadaException, PermissaoNegadaException {
        verificarVeterinario(autor);
        for (Vacina existente : vacinas) {
            if (existente.getNome().equalsIgnoreCase(vacina.getNome())
                    && existente.getDataAplicacao().equals(vacina.getDataAplicacao())) {
                throw new VacinaDuplicadaException(vacina.getNome(), vacina.getDataAplicacao());
            }
        }
        vacinas.add(vacina);
    }

    // Regra 4: só veterinário registra consultas e tratamentos
    public void registrarConsulta(String descricao, String tratamento, Usuario autor)
            throws PermissaoNegadaException {
        verificarVeterinario(autor);
        consultas.add(new Consulta(LocalDate.now(), descricao, tratamento, (Veterinario) autor));
    }

    private void verificarVeterinario(Usuario autor) throws PermissaoNegadaException {
        if (!(autor instanceof Veterinario)) {
            String nome = (autor == null) ? "desconhecido" : autor.getNome();
            throw new PermissaoNegadaException(nome);
        }
    }

    // Listas protegidas: ninguém altera o histórico sem passar pelos métodos acima
    public List<Vacina> getVacinas() {
        return Collections.unmodifiableList(vacinas);
    }

    public List<Consulta> getConsultas() {
        return Collections.unmodifiableList(consultas);
    }
}
