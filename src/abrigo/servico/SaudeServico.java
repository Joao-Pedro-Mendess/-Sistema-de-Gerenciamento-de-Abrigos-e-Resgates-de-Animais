package abrigo.servico;

import abrigo.modelo.animais.Animal;
import abrigo.modelo.saude.Vacina;
import abrigo.modelo.usuarios.Usuario;
import abrigo.excecoes.PermissaoNegadaException;
import abrigo.excecoes.VacinaDuplicadaException;

public class SaudeServico {

    public void registrarVacina(Animal animal, Vacina vacina,
            Usuario autor)
            throws VacinaDuplicadaException,
                   PermissaoNegadaException {

        if (animal == null) {
            throw new IllegalArgumentException(
                "Animal não encontrado!"
            );
        }

        animal.getHistoricoMedico()
              .registrarVacina(vacina, autor);
    }

    public void registrarConsulta(Animal animal,
            String descricao, String tratamento,
            Usuario autor)
            throws PermissaoNegadaException {

        if (animal == null) {
            throw new IllegalArgumentException(
                "Animal não encontrado!"
            );
        }

        animal.getHistoricoMedico()
              .registrarConsulta(descricao, tratamento, autor);
    }
}