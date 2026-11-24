package abrigo.servico;

import java.util.ArrayList;
import java.util.List;

import abrigo.modelo.notificacoes.Notificacao;
import abrigo.modelo.usuarios.Usuario;

public class NotificacaoServico {

    private static List<Notificacao> notificacoes = new ArrayList<>();

    public void enviar(String mensagem, Usuario destinatario) {
        if (destinatario == null) {
            throw new IllegalArgumentException("Destinatário inválido!");
        }

        notificacoes.add(new Notificacao(mensagem, destinatario));
    }

    public List<Notificacao> listar() {
        return new ArrayList<>(notificacoes);
    }

    public List<Notificacao> listarPorUsuario(Usuario usuario) {
        List<Notificacao> resultado = new ArrayList<>();

        for (Notificacao n : notificacoes) {
            if (n.getDestinatario().getCpf().equals(usuario.getCpf())) {
                resultado.add(n);
            }
        }

        return resultado;
    }

    public List<Notificacao> listarNaoLidas(Usuario usuario) {
        List<Notificacao> resultado = new ArrayList<>();

        for (Notificacao n : notificacoes) {
            if (n.getDestinatario().getCpf().equals(usuario.getCpf())
                    && !n.isLida()) {
                resultado.add(n);
            }
        }

        return resultado;
    }

    public Notificacao buscarPorId(int id) {
        for (Notificacao n : notificacoes) {
            if (n.getId() == id) {
                return n;
            }
        }

        return null;
    }

    public void marcarComoLida(int id, Usuario usuario) {
        Notificacao n = buscarPorId(id);

        if (n == null) {
            throw new IllegalArgumentException(
                "Notificação não encontrada!"
            );
        }

        if (!n.getDestinatario().getCpf().equals(usuario.getCpf())) {
            throw new IllegalArgumentException(
                "Você não pode alterar a notificação de outro usuário!"
            );
        }

        n.marcarComoLida();
    }

    public boolean remover(int id) {
        Notificacao n = buscarPorId(id);

        if (n == null) {
            return false;
        }

        notificacoes.remove(n);
        return true;
    }

    public int contarNaoLidas(Usuario usuario) {
        return listarNaoLidas(usuario).size();
    }
}