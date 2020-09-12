package com.conexia.batch.reporteador.configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

@Configuration
@EnableBatchProcessing
public class RealizarConsultasBatchConfiguration {



	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	//pendiente de probar cuando se despliega en forma de war
	//@PersistenceContext(unitName = "liquidacionPrestacionDS")
    //private EntityManager em;
	
	@Resource
	private DataSource datasource;
	
	@Autowired
	@Qualifier("postgresqDataSource")
    private DataSource postgresqDS;	
	
	@Bean
    public ItemStreamReader<String> objectDBReader() {
		//EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) em.getEntityManagerFactory();   
		JdbcCursorItemReader<String> reader = new JdbcCursorItemReader<String>();
		reader.setDataSource(postgresqDS);
		reader.setSql("select * from liquidacion.cuenta");
		reader.setRowMapper(new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				StringBuilder data = new StringBuilder("");
				for(int columnIndex = 1; columnIndex <= rs.getMetaData().getColumnCount(); columnIndex++) {
					data.append(Objects.nonNull(rs.getObject(columnIndex)) ? rs.getObject(columnIndex).toString() : "").append(",");
				}
				return data.toString();
			}
		});
		return reader;
	}
	
	@Bean
    public ItemWriter<String> objectFileWriter() throws Exception {
        FlatFileItemWriter<String> writer = new FlatFileItemWriter<String>();
        writer.setResource(new FileSystemResource("c:\\temp\\output_db\\salida.csv"));
        writer.setAppendAllowed(true);
		DelimitedLineAggregator<String> lineAggregator = new DelimitedLineAggregator<String>();
		writer.setLineAggregator(lineAggregator);
        writer.setShouldDeleteIfExists(true);
        return writer;
    }
	
	@Bean
	public Step step1() throws Throwable {
		return stepBuilderFactory.get("step1")
				.<String, String>chunk(5000)
				.reader(objectDBReader())
				.writer(objectFileWriter())
				.build();
	}

	@Bean
	public Job myJob() throws Throwable {
		return jobBuilderFactory.get("myJob")
				.incrementer(new RunIdIncrementer())
				.flow(step1())
				.end()
				.build();
	}
}
