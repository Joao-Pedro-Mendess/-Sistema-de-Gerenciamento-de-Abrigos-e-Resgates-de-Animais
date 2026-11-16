package abrigo.modelo.usuarios;

import abrigo.excecoes.AnimalIndisponivelException;
import abrigo.modelo.Usuario;
import abrigo.modelo.adocao.PodeSolicitarAdocao;
import abrigo.modelo.adocao.SolicitacaoAdocao;
import abrigo.modelo.animais.Animal;

public class Adotante extends Usuario implements PodeSolicitarAdocao {

    private String tipoMoradia;
    private String preferencias;

    public Adotante(String nome, String cpf, String email, String tipoMoradia, String preferencias) {
        super(nome, cpf, email);
        this.tipoMoradia = tipoMoradia;
        this.preferencias = preferencias;
    }

    @Override
    public SolicitacaoAdocao solicitarAdocao(Animal animal) throws AnimalIndisponivelException {
        animal.iniciarProcessoAdocao();
        return new SolicitacaoAdocao(this, animal);
    }

    public String getTipoMoradia() {
        return tipoMoradia;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setTipoMoradia(String tipoMoradia) {
        this.tipoMoradia = tipoMoradia;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }
}
