package com.MedApp.historico2.Service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class S_Generico {
    public static boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean textoEstaVazio(String texto) {
        return texto == null || texto.trim().equals("");
    }

    public static String limparNumero(String numero) {
        return numero.replaceAll("[^0-9]", "");
    }

    public static boolean validarCPF(String cpf) {
        return cpf.matches("^\\d{11}$");
    }

    public static boolean validarSenha(String senha) {
        return senha.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@*#_$%^&+=!]).{10,}$");
    }

}
