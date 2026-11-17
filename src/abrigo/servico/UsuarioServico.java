package abrigo.servicos;

import java.util.ArrayList;
import java.util.List;

import abrigo.modelo.usuarios.Usuario;
import abrigo.util.ValidadorCpf;

public class UsuarioServico {

    private List<Usuario> usuarios;

    public UsuarioServico() {
        usuarios = new ArrayList<>();
    }

    public boolean cpfExiste(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return true;
            }
        }

        return false;
    }

    public boolean emailExiste(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }

        return false;
    }

    public void cadastrar(Usuario usuario) {
        if (!ValidadorCpf.validar(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF inválido!");
        }

        if (cpfExiste(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }

        if (emailExiste(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }

        usuarios.add(usuario);
    }

    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }

    public Usuario buscarPorCpf(String cpf) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return usuario;
            }
        }

        return null;
    }

    public Usuario buscarPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }

        return null;
    }

    public boolean remover(String cpf) {
        Usuario usuario = buscarPorCpf(cpf);

        if (usuario != null) {
            usuarios.remove(usuario);
            return true;
        }

        return false;
    }

    public void editar(String cpf, String novoNome, String novoEmail) {
        Usuario usuario = buscarPorCpf(cpf);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        Usuario donoEmail = buscarPorEmail(novoEmail);

        if (donoEmail != null && !donoEmail.getCpf().equals(cpf)) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }

        usuario.setNome(novoNome);
        usuario.setEmail(novoEmail);
    }

    public List<Usuario> listarPorTipo(Class<?> tipo) {
        List<Usuario> resultado = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            if (tipo.isInstance(usuario)) {
                resultado.add(usuario);
            }
        }

        return resultado;
    }

    public int contarPorTipo(Class<?> tipo) {
        int contador = 0;

        for (Usuario usuario : usuarios) {
            if (tipo.isInstance(usuario)) {
                contador++;
            }
        }

        return contador;
    }
}