package com.backend.parcial.dao.impl;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.entity.Odontologo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoMemory implements IDao<Odontologo> {
    private final Logger LOGGER = Logger.getLogger(OdontologoDaoMemory.class);
    private List<Odontologo> odontologoRepository;
    private int currentId = 0;

    public OdontologoDaoMemory() {
        this.odontologoRepository = new ArrayList<>();
    }

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        currentId = currentId + 1;

        Odontologo odontologoGuardado = new Odontologo(currentId, odontologo.getNumeroMatricula(), odontologo.getNombre(), odontologo.getApellido());

        odontologoRepository.add(odontologo);
        LOGGER.info("Odontologo guardado: " + odontologoGuardado);
        return odontologoGuardado;
    }

    @Override
    public List<Odontologo> listarTodos() {

        return odontologoRepository;
    }
}
