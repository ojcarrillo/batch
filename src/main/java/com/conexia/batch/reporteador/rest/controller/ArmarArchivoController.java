package com.conexia.batch.reporteador.rest.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/armararchivo")
public class ArmarArchivoController {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	Job myJobArmarArchivo;
	
	@GetMapping
	public BatchStatus armarArchivo() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
		/* ruta de los archivos de entrada */
		String archivos = "file://c:/temp/output/archivo_completo*.csv";
		/* nombre del archivo de salida */
		String fileName = "c:\\temp\\output_2\\archivo_completo_" + String.valueOf(System.currentTimeMillis()) + ".csv";
		/* parametros para el job */
		JobParametersBuilder paramsBuilder = new JobParametersBuilder();
		paramsBuilder.addString("fileName", fileName);
		paramsBuilder.addString("archivos", archivos);
		paramsBuilder.addDate("time", new Date());
		/* ejecucion del job */
		JobExecution jobExecution = jobLauncher.run(myJobArmarArchivo, paramsBuilder.toJobParameters());
		return jobExecution.getStatus();		
	}

}
