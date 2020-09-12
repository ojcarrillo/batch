package com.conexia.batch.reporteador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conexia.batch.reporteador.data.entity.Modulo;
import com.conexia.batch.reporteador.data.repository.ModuloRepository;

@Service
@Transactional("postgresTransactionManager")
public class SeriviciosServices  {

	@Autowired
	ModuloRepository moduloRepository;
	
	public List<Modulo> obtenerModulos(){
		return moduloRepository.findAll();
	}
}
