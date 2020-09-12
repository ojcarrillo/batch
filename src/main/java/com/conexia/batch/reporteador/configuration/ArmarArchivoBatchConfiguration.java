package com.conexia.batch.reporteador.configuration;

import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@EnableBatchProcessing
public class ArmarArchivoBatchConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public FlatFileItemReader<String> reader() {
		FlatFileItemReader<String> reader = new FlatFileItemReader<String>();
		reader.setLineMapper(new DefaultLineMapper<String>() {
			public String mapLine(String line, int lineNumber) throws Exception {
				return line;
			}
		});
		return reader;
	}

	@Bean
	@StepScope
	public MultiResourceItemReader<String> multiResourceItemReader(
			@Value("#{jobParameters['archivos']}") String archivos) throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		MultiResourceItemReader<String> multiResourceItemReader = new MultiResourceItemReader<String>();
		multiResourceItemReader.setResources(resolver.getResources(archivos));
		multiResourceItemReader.setDelegate(reader());
		return multiResourceItemReader;
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<String> writer(@Value("#{jobParameters['fileName']}") String fileName) {
		FlatFileItemWriter<String> writer = new FlatFileItemWriter<String>();
		writer.setResource(new FileSystemResource(fileName));
		writer.setAppendAllowed(true);
		DelimitedLineAggregator<String> lineAggregator = new DelimitedLineAggregator<String>();
		writer.setLineAggregator(lineAggregator);
		return writer;
	}

	@Bean
	public Step step1ArmarArchivo() throws IOException {
		return stepBuilderFactory.get("step1ArmarArchivo")
				.<String, String>chunk(100000)
				.reader(multiResourceItemReader(null))
				.writer(writer(null))
				.build();
	}

	@Bean
	public Job myJobArmarArchivo() throws IOException {
		return jobBuilderFactory.get("myJobArmarArchivo")
				.incrementer(new RunIdIncrementer())
				.flow(step1ArmarArchivo())
				.end()
				.build();
	}

}
