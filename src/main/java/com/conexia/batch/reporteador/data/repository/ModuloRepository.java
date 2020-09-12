package com.conexia.batch.reporteador.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.conexia.batch.reporteador.data.entity.Modulo;

@Repository
public interface ModuloRepository extends CrudRepository<Modulo, Integer> {
	
	@Override
    List<Modulo> findAll();
}