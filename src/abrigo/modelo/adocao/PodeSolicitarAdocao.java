package abrigo.modelo.adocao;

import abrigo.excecoes.AnimalIndisponivelException;
import abrigo.modelo.animais.Animal;

public interface PodeSolicitarAdocao {

    SolicitacaoAdocao solicitarAdocao(Animal animal) throws AnimalIndisponivelException;
}