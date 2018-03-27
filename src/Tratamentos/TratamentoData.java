/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tratamentos;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author carlos.goiani
 */
public class TratamentoData {

    private static final String REGEX = "\\d{4}_\\d{2}_\\d{2}";

    /**
     * Recebe o nome do arquivo do DATAENV, faz o corte para obter somente a
     * data e compara se é diferente de hoje - 1 dia.
     *
     * @param nomeArquivo nome do arquivo a ser comparado
     * @return retorna true se diferente da data atual - 1 dia false se não
     */
    public static boolean processaNome(String nomeArquivo) {

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(nomeArquivo);

        String dataArquivo = new String();
        while (matcher.find()) {
            dataArquivo = dataArquivo + matcher.group();
        }
        if (!dataArquivo.matches(REGEX)) {
            return false;
        }
        String[] dataFragmentada = dataArquivo.split("_");
        LocalDate teste = LocalDate.of(new Integer(dataFragmentada[0]),
                new Integer(dataFragmentada[1]),
                new Integer(dataFragmentada[2]));
        return teste.equals(LocalDate.now().minusDays(1));// quantidade de dias subtraidos do dia atual
    }

}
