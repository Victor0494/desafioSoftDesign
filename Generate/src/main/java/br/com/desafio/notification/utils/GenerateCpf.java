package br.com.desafio.notification.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;
import java.util.stream.IntStream;

public class GenerateCpf {

    private static final Random RANDOM = new Random();

    public static String generateCpf(boolean isValid) {
        int[] cpf = IntStream.range(0, 9)
                .map(i -> RANDOM.nextInt(10))
                .toArray();

        if (isValid) {
            cpf = appendValidDigits(cpf);
        } else {
            cpf = appendInvalidDigits(cpf);
        }

        return formatCpf(cpf);
    }

    // Gera os dígitos verificadores corretos
    private static int[] appendValidDigits(int[] cpf) {
        int[] newCpf = new int[11];
        System.arraycopy(cpf, 0, newCpf, 0, 9);
        newCpf[9] = calculateDigit(newCpf, 10);
        newCpf[10] = calculateDigit(newCpf, 11);
        return newCpf;
    }

    // Gera os dígitos verificadores incorretos para um CPF inválido
    private static int[] appendInvalidDigits(int[] cpf) {
        int[] newCpf = new int[11];
        System.arraycopy(cpf, 0, newCpf, 0, 9);
        newCpf[9] = RANDOM.nextInt(10);
        newCpf[10] = RANDOM.nextInt(10);
        return newCpf;
    }

    // Calcula um dígito verificador do CPF
    private static int calculateDigit(int[] cpf, int peso) {
        int soma = IntStream.range(0, peso - 1)
                .map(i -> cpf[i] * (peso - i))
                .sum();
        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    // Formata o CPF no padrão XXX.XXX.XXX-XX
    private static String formatCpf(int[] cpf) {
        return String.format("%d%d%d.%d%d%d.%d%d%d-%d%d",
                cpf[0], cpf[1], cpf[2], cpf[3], cpf[4], cpf[5],
                cpf[6], cpf[7], cpf[8], cpf[9], cpf[10]);
    }

    public static boolean validateCPF(String cpf) {
        if (cpf == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CPF inválido");
        }

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // CPF deve ter exatamente 11 dígitos
        if (!cpf.matches("\\d{11}")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CPF inválido");
        }

        // Verifica se todos os dígitos são iguais (ex: "111.111.111-11" é inválido)
        if (cpf.matches("(\\d)\\1{10}")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CPF inválido");
        }

        // Cálculo do primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstCheckDigit = 11 - (sum % 11);
        firstCheckDigit = (firstCheckDigit >= 10) ? 0 : firstCheckDigit;

        // Cálculo do segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondCheckDigit = 11 - (sum % 11);
        secondCheckDigit = (secondCheckDigit >= 10) ? 0 : secondCheckDigit;

        // Verifica se os dígitos calculados correspondem aos informados
        if (!(firstCheckDigit == Character.getNumericValue(cpf.charAt(9)) &&
                secondCheckDigit == Character.getNumericValue(cpf.charAt(10)))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CPF inválido");
        }

        return true;
    }

}
