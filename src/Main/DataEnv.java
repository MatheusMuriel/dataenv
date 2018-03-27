/*
 * Copia os arquivos da origem obtida do txt pathenv que deve estar dentro 
 * da mesma pasta para o destino que também é obtido através deste mesmo arquivo
 * qualquer resultado com erro ou com sucesso é enviado através de email
 * para o destino especificado no codigo na variavel estatica EMAIL,
 * para obtenção da data do arquivo de acordo com o nome é usado REGEX
 * o qual está contido dentro da classe TratamentoData.
 */
package Main;

import Tratamentos.TratamentoData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import Tratamentos.TratamentoEmail;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author carlos.goiani
 * @version 1.0
 * @see https://github.com/carlosgit2016
 */
public class DataEnv {

    private static String sourceDataenv;
    private static String destinoDataenv;
    private static final String CAMINHO = "pathenv.txt";
    private static final String EMAIL = "carlos.goiani@moinhoarapongas.com.br";

    public static void moverDATAENV() {
        sourceDataenv = lerCaminho().get(0);
        destinoDataenv = lerCaminho().get(1);

        File source = new File(sourceDataenv);
        File caminhoDataenv = new File(destinoDataenv);

        try {

            List<File> listaDataenv = Arrays.asList(source.listFiles());
            List<String> copiados = new ArrayList<>();
            String nomeArq;
            for (File file : listaDataenv) {
                nomeArq = file.getName();
                if (TratamentoData.processaNome(nomeArq)) {
                    FileUtils.moveFileToDirectory(file, caminhoDataenv, false);
                    copiados.add(nomeArq);
                }
            }

            if (!copiados.isEmpty()) {
                enviarEmail("Copia DATAENV", geraMensagem(copiados), EMAIL);
                System.out.println("\n\r\n\r\n\r Arquivos copiados com sucesso \r\n" + getDataAtual());
            } else {
                enviarEmail("Copia DATAENV", "Nenhum arquivo copiado, Checar.", EMAIL);
                System.out.println("\n\r\n\r\n\r Nenhum arquivo copiado \r\n" + getDataAtual());
            }

        } catch (IOException | NullPointerException e) {
            enviarEmail("Erro ao mover arquivos do DATAENV: "
                    + CAMINHO, e.getMessage(), EMAIL);
        }
    }

    /**
     * Faz leitura do arquivo TXT que está contido no mesmo diretorio e retorna
     *
     * @return retorna uma String contendo o caminho e null se não existir
     */
    private static List<String> lerCaminho() {
        List<String> listaLinhas;
        Exception erro = null;
        File caminho;
        FileReader lerCaminho;
        BufferedReader bf;

        caminho = new File(CAMINHO).getAbsoluteFile();

        try {
            lerCaminho = new FileReader(caminho);
            String line;
            bf = new BufferedReader(lerCaminho);
            listaLinhas = new ArrayList<>();

            while ((line = bf.readLine()) != null) {
                listaLinhas.add(line.substring(line.indexOf(";") + 1));
            }

            return listaLinhas;
        } catch (FileNotFoundException fileEx) {
            erro = fileEx;
            return null;
        } catch (IOException ioEx) {
            erro = ioEx;
            return null;
        } finally {
            if (erro != null) {
                enviarEmail("Erro ao ler arquivo"
                        + CAMINHO, erro.getMessage(), EMAIL);
            }
        }

    }

    /**
     * Usa a classe TratamentoEmail para enviar um email simples
     *
     * @param assunto - assunto do email
     * @param mensagem - corpo do email
     * @param destino - endereço de destino
     */
    private static void enviarEmail(String assunto, String mensagem, String destino) {
        TratamentoEmail email = new TratamentoEmail();
        try {
            email.enviarEmailSimples(assunto, mensagem, destino);
        } catch (EmailException e) {
            System.out.println("Erro ao enviar Email: " + e.getMessage());
        }

    }

    /**
     * Gera mensagem com relatorio de copias + data
     *
     * @param copiados - Lista de arquivos copiados
     * @return retorna uma String contendo as informal
     */
    private static String geraMensagem(List<String> copiados) {
        String mensagem = "Arquivos copiados com sucesso na da data de : "
                + getDataAtual() + "\r\n\r\n";

        for (String copiado : copiados) {
            mensagem += copiado + "\r\n";
        }

        return mensagem;
    }

    /**
     * Traz a data atual de acordo com o padrão brasileiro
     *
     * @return String com data atual
     */
    private static String getDataAtual() {
        return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(new Locale("pt", "br")));

    }
}
