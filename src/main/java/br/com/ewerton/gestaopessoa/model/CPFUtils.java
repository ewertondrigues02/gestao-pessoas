package br.com.ewerton.gestaopessoa.model;

public class CPFUtils {

    // Método para validar o CPF
    public static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", ""); // Remove formatação
        if (cpf.length() != 11) {
            return false; // CPF deve ter 11 dígitos
        }

        // Verifica se todos os dígitos são iguais (CPF inválido)
        if (cpf.chars().distinct().count() == 1) {
            return false; // CPF com todos os dígitos iguais é inválido
        }

        // Definindo os pesos para o cálculo dos dígitos verificadores
        int[] pesos1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        // Calcula o primeiro dígito verificador
        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += Character.getNumericValue(cpf.charAt(i)) * pesos1[i];
        }
        int digito1 = (soma1 * 10) % 11;
        if (digito1 == 10) digito1 = 0; // Ajusta para 0 se o dígito for 10

        // Calcula o segundo dígito verificador
        int soma2 = 0;
        for (int i = 0; i < 9; i++) {
            soma2 += Character.getNumericValue(cpf.charAt(i)) * pesos2[i + 1];
        }
        soma2 += digito1 * pesos2[0];
        int digito2 = (soma2 * 10) % 11;
        if (digito2 == 10) digito2 = 0; // Ajusta para 0 se o dígito for 10

        // Verifica se os dígitos verificadores calculados são iguais aos fornecidos
        return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
                digito2 == Character.getNumericValue(cpf.charAt(10));
    }

    // Método para formatar o CPF
    public static String formatarCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", ""); // Remove formatação existente
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos.");
        }
        return String.format("%s.%s.%s-%s",
                cpf.substring(0, 3),
                cpf.substring(3, 6),
                cpf.substring(6, 9),
                cpf.substring(9));
    }

    // Método para remover formatação do CPF
    public static String removerFormatacao(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    // Método para validar e formatar o CPF em um único passo
    public static String validarEFormatarCPF(String cpf) {
        if (isValidCPF(cpf)) {
            return formatarCPF(removerFormatacao(cpf));
        } else {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }
}
