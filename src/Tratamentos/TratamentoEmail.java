/**
 * Classe responsavel por emails enviados, como API usamos
 * o CommonEmail que contém maior variedade de uso.
 */
package Tratamentos;

import java.io.File;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author carlos.goiani
 */
public class TratamentoEmail {

    public TratamentoEmail(String hostName, String endereco, String user, String senha, boolean isSSL) {
        this.hostName = hostName;
        this.endereco = endereco;
        this.user = user;
        this.senha = senha;
        this.isSSL = isSSL;
    }

    public TratamentoEmail() {
        this("webmail.moinhoarapongas.com.br", "suporte@moinhoarapongas.com.br", "suporte", "suporte@123", true);
    }

    private String hostName;
    private String endereco;
    private String user;
    private String senha;
    private boolean isSSL;

    private SimpleEmail emailSimples;
    private MultiPartEmail emailAnexo;

    public String gethostName() {
        return hostName;
    }

    public void sethostName(String hostName) {
        this.hostName = hostName;
    }

    public String getuser() {
        return user;
    }

    public void setuser(String user) {
        this.user = user;
    }

    public String getsenha() {
        return senha;
    }

    public void setsenha(String senha) {
        this.senha = senha;
    }

    public boolean isIsSSL() {
        return isSSL;
    }

    public void setIsSSL(boolean isSSL) {
        this.isSSL = isSSL;
    }

    public SimpleEmail getEmailSimples() {
        return emailSimples;
    }

    public void setEmailSimples(SimpleEmail emailSimples) {
        this.emailSimples = emailSimples;
    }

    public MultiPartEmail getEmailAnexo() {
        return emailAnexo;
    }

    public void setEmailAnexo(MultiPartEmail emailAnexo) {
        this.emailAnexo = emailAnexo;
    }

    /**
     * Envia um email Simples através da API Common Email
     * @param assunto - Assunto do email
     * @param mensagem - Corpo do email
     * @param destino - Para quem será enviado
     * @throws EmailException
     */
    public void enviarEmailSimples(String assunto, String mensagem, String destino) throws EmailException {

        emailSimples = new SimpleEmail();
        emailSimples.setDebug(true);
        emailSimples.setHostName(hostName);
        emailSimples.setAuthentication(user, senha);
        emailSimples.setSSLCheckServerIdentity(isSSL);
        emailSimples.addTo(destino); //Para quem vai
        emailSimples.setFrom(endereco); //Quem está enviando
        emailSimples.setSubject(assunto);
        emailSimples.setMsg(mensagem);
        emailSimples.send();
        
    }

    /**
     * Envia um email com anexo através da API Common Email
     * @param assunto - Assunto do email
     * @param mensagem - Corpo do email
     * @param destino - Para quem será enviado
     * @param anexo - Anexo para o email
     * @throws EmailException 
     */
    public void enviarEmailAnexo(String assunto, String mensagem, String destino, File anexo) throws EmailException {

        emailAnexo = new MultiPartEmail();
        emailAnexo.setDebug(true);
        emailAnexo.setHostName(hostName);
        emailAnexo.setAuthentication(user,senha);
        emailAnexo.setSSL(isSSL);
        emailAnexo.addTo(destino); //Para quem vai
        emailAnexo.setFrom(endereco); //Quem está enviando
        emailAnexo.setSubject(assunto);
        emailAnexo.setMsg(mensagem);
        emailAnexo.attach(anexo);
        emailAnexo.send();
        
    }

}
