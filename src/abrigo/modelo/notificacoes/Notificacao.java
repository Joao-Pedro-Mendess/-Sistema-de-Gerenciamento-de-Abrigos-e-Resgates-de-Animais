
package abrigo.modelo.notificacoes;

import java.time.LocalDateTime;
import abrigo.modelo.usuarios.Usuario;

public class Notificacao {

    private static int proximoId = 1;

    private final int id;
    private String mensagem;
    private LocalDateTime data;
    private boolean lida;
    private Usuario destinatario;

    public Notificacao(String mensagem, Usuario destinatario) {
        this.id = proximoId++;
        this.mensagem = mensagem;
        this.data = LocalDateTime.now();
        this.lida = false;
        this.destinatario = destinatario;
    }

    public int getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getData() {
        return data;
    }

    public boolean isLida() {
        return lida;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void marcarComoLida() {
        this.lida = true;
    }
}