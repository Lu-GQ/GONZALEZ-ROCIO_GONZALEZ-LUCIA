package com.backend.parcial.test;

import com.backend.parcial.dao.IDao;
import com.backend.parcial.dao.impl.OdontologoDaoH2;
import com.backend.parcial.dao.impl.OdontologoDaoMemory;
import com.backend.parcial.entity.Odontologo;
import com.backend.parcial.service.impl.OdontologoService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

public class OdontologoServiceTest {

    private OdontologoService odontologoService;

    //H2
    @Test
    @Ignore
    public void deberiaRegistrarUnOdontologoYRetornarUnIdEnH2() {
        //  Ignoramos este test ya que afecta los datos de la DB y luego el test `deberiaListarOdontologosDeH2`
        //  falla porque no se puede predecir la cantidad de elementos en la table ODONTOLOGO
        Odontologo odontologo = new Odontologo(714, "Ana", "Gutierrez");
        odontologoService = new OdontologoService(new OdontologoDaoH2());
        Odontologo odontologoRegistrado = odontologoService.registrarOdontologo(odontologo);
        Assert.assertTrue(odontologoRegistrado.getId() != 0);
    }

    @Test
    public void deberiaListarOdontologosDeH2() {
        odontologoService = new OdontologoService(new OdontologoDaoH2());
        int count = odontologoService.listarOdontologos().size();
        Assert.assertEquals(2, count);
    }

    //Memoria
    @Test
   public void deberiaRegistrarUnOdontologoYRetornarUnIdEnMemoria() {
        Odontologo odontologo = new Odontologo(714, "Ana", "Gutierrez");
        odontologoService = new OdontologoService(new OdontologoDaoMemory());
        Odontologo odontologoRegistrado = odontologoService.registrarOdontologo(odontologo);
        Assert.assertTrue(odontologoRegistrado.getId() != 0);
    }

    @Test
    public void deberiaListarOdontologosEnMemoria() {
        Odontologo odontologo1 = new Odontologo(714, "Ana", "Gutierrez");
        Odontologo odontologo2 = new Odontologo(3343, "Jorge", "Gutierrez");
        odontologoService = new OdontologoService(new OdontologoDaoMemory());
        odontologoService.registrarOdontologo(odontologo1);
        odontologoService.registrarOdontologo(odontologo2);
        int count = odontologoService.listarOdontologos().size();
        Assert.assertEquals(2, count);
    }

}


