package br.com.challange.softDesign.domain.utils;

public class CpfUtils {

    public static String maskCpf(String cpf) {
        cpf = removeSpecialCharacters(cpf);

        if (cpf.length() != 11) {
            return "CPF inválido";
        }

        return cpf.substring(0, 3) + ".***.***-" + cpf.substring(9);
    }

    public static String removeSpecialCharacters(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }
}
