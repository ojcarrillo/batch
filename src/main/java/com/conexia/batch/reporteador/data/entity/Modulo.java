package com.conexia.batch.reporteador.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(schema = "public", name = "modulo")
@Where(clause = " habilitado = 'true' and deleted='false' ")
public class Modulo extends Control{

	@Id
	private long id;

	@Column
	private String codigo;

	@Column
	private String nombre;

	public Modulo() {
		super();
	}

	public Modulo(long id, String codigo, String nombre) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
