package abrigo.servico;

import java.util.List;

import abrigo.modelo.animais.Animal;
import abrigo.modelo.saude.Consulta;
import abrigo.modelo.saude.Vacina;

public class RelatorioSaudeServico {

    private AnimalServico animalServico;

    public RelatorioSaudeServico(AnimalServico animalServico) {
        if (animalServico == null) {
            throw new IllegalArgumentException(
                "Serviço de animais inválido!"
            );
        }

        this.animalServico = animalServico;
    }

    public int contarVacinas() {
        int total = 0;

        for (Animal animal : animalServico.listar()) {
            total += animal.getHistoricoMedico()
                    .getVacinas().size();
        }

        return total;
    }

    public int contarConsultas() {
        int total = 0;

        for (Animal animal : animalServico.listar()) {
            total += animal.getHistoricoMedico()
                    .getConsultas().size();
        }

        return total;
    }

    public String gerarRelatorioAnimal(int id) {
        Animal animal = animalServico.buscarPorId(id);

        if (animal == null) {
            throw new IllegalArgumentException(
                "Animal não encontrado!"
            );
        }

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("===== RELATÓRIO DE SAÚDE =====\n");
        relatorio.append("Animal: ").append(animal.getNome()).append("\n");
        relatorio.append("Estado de saúde: ")
                .append(animal.getEstadoSaude()).append("\n\n");

        relatorio.append("VACINAS:\n");

        List<Vacina> vacinas = animal.getHistoricoMedico()
                .getVacinas();

        if (vacinas.isEmpty()) {
            relatorio.append("Nenhuma vacina registrada.\n");
        } else {
            for (Vacina vacina : vacinas) {
                relatorio.append("- ")
                        .append(vacina.getNome())
                        .append(" | ")
                        .append(vacina.getDataAplicacao())
                        .append("\n");
            }
        }

        relatorio.append("\nCONSULTAS:\n");

        List<Consulta> consultas = animal.getHistoricoMedico()
                .getConsultas();

        if (consultas.isEmpty()) {
            relatorio.append("Nenhuma consulta registrada.\n");
        } else {
            for (Consulta consulta : consultas) {
                relatorio.append("- ")
                        .append(consulta.getData())
                        .append(" | ")
                        .append(consulta.getDescricao())
                        .append(" | Tratamento: ")
                        .append(consulta.getTratamento())
                        .append("\n");
            }
        }

        return relatorio.toString();
    }

    public String gerarResumo() {
        return "===== RESUMO DE SAÚDE =====\n"
            + "Total de vacinas registradas: " + contarVacinas() + "\n"
            + "Total de consultas registradas: " + contarConsultas();
    }
}
