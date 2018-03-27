package Main;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Implementa a classe Job e usa execute para executar em uma nova TREAD a tarefa
 * @author carlos.goiani
 */
public class ValidadorJob implements Job{

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        DataEnv.moverDATAENV();
    }
    
}
