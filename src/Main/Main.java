package Main;

import Tratamentos.TratamentoEmail;
import org.apache.commons.mail.EmailException;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/*
 * Responsavel pela execução do programa de acordo com a DATA agendada 
 * na variavel String DATA que segue o padrão crontab linux.
 */
public class Main {

    //Segundos, Minutos, Horas, Dia do Mês, Mês, Dia da Semana e Ano (Opcional)
    private static final String DATA = "0 23 * * * ?";
    private static final String GRUPO = "group01";
    private static final String EMAIL = "carlos.goiani@moinhoarapongas.com.br";
    private static Scheduler scheduler;
    private static JobDetail job;
    private static Trigger trigger;

    public static void main(String[] args) {

        SchedulerFactory shedFact = new StdSchedulerFactory();

        try {
            scheduler = shedFact.getScheduler();
            scheduler.start();
            job = JobBuilder.newJob(ValidadorJob.class)
                    .withIdentity("validadorJOB", GRUPO)
                    .build();
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("validadorTRIGGER", GRUPO)
                    .withSchedule(CronScheduleBuilder.cronSchedule(DATA))
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            enviarEmail("Erro ao executar tarefa agendada (DATAENV)",
                    e.getMessage(),
                    EMAIL);
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

}
