package abrigo.servico;

public class SistemaAbrigo {

    private UsuarioServico usuarioServico;
    private AnimalServico animalServico;
    private AdocaoServico adocaoServico;
    private AtividadeServico atividadeServico;
    private SaudeServico saudeServico;
    private NotificacaoServico notificacaoServico;
    private RelatorioServico relatorioServico;
    private RelatorioAtividadeServico relatorioAtividadeServico;
    private RelatorioSaudeServico relatorioSaudeServico;

    public SistemaAbrigo() {

        usuarioServico = new UsuarioServico();
        animalServico = new AnimalServico();
        adocaoServico = new AdocaoServico();
        atividadeServico = new AtividadeServico();
        saudeServico = new SaudeServico();
        notificacaoServico = new NotificacaoServico();

        relatorioServico = new RelatorioServico(
            animalServico, adocaoServico
        );

        relatorioAtividadeServico = new RelatorioAtividadeServico(
            atividadeServico
        );

        relatorioSaudeServico = new RelatorioSaudeServico(
            animalServico
        );
    }

    public UsuarioServico getUsuarioServico() {
        return usuarioServico;
    }

    public AnimalServico getAnimalServico() {
        return animalServico;
    }

    public AdocaoServico getAdocaoServico() {
        return adocaoServico;
    }

    public AtividadeServico getAtividadeServico() {
        return atividadeServico;
    }

    public SaudeServico getSaudeServico() {
        return saudeServico;
    }

    public NotificacaoServico getNotificacaoServico() {
        return notificacaoServico;
    }

    public RelatorioServico getRelatorioServico() {
        return relatorioServico;
    }

    public RelatorioAtividadeServico getRelatorioAtividadeServico() {
        return relatorioAtividadeServico;
    }

    public RelatorioSaudeServico getRelatorioSaudeServico() {
        return relatorioSaudeServico;
    }
}