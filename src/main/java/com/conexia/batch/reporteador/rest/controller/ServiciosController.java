package com.conexia.batch.reporteador.rest.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conexia.batch.reporteador.SeriviciosServices;
import com.conexia.batch.reporteador.data.entity.Modulo;
import com.google.gson.Gson;

@RestController
@RequestMapping("/reportes/servicios")
public class ServiciosController {

	@Autowired
	SeriviciosServices serviciosServices;
	
	private static final Gson gson = new Gson();
	
	@GET
	@RequestMapping(path = "/modulos", produces = { "application/JSON" })
	public Response consultarModulos() {
		List<Modulo> lista = serviciosServices.obtenerModulos();		
		return Response.status(Status.OK).entity(lista).build();
	}
}
