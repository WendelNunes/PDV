/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.util;

import br.com.tiaorockeiro.modelo.Usuario;
import br.com.tiaorockeiro.negocio.UsuarioNegocio;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Wendel
 */
public class SessaoUtil {

    private static Usuario usuario;

    public static boolean logar(String descricao, String senha) throws Exception {
        if (usuario == null) {
            Usuario u = new UsuarioNegocio().procurarPorDescricao(descricao);
            if (u != null && u.getDescricao().equals(descricao) && u.getSenha().equals(geraMD5Senha(senha))) {
                usuario = u;
                return true;
            }
        }
        return false;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    private static String geraMD5Senha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(senha.getBytes(Charset.forName("UTF8")));
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}
