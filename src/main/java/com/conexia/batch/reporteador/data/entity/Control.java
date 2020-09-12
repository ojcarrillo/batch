package com.conexia.batch.reporteador.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Control implements Serializable{

	@Column
	public Boolean habilitado;
	
	@Column
	public Boolean deleted;

	public Control() {
		super();
	}

	public Control(Boolean habilitado, Boolean deleted) {
		super();
		this.habilitado = habilitado;
		this.deleted = deleted;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
