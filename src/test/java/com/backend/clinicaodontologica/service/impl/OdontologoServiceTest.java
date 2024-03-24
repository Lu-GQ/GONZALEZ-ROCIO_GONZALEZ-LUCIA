package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.OdontologoSalidaDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;


    @Test
    @Order(1)
    void deberiaRegistrarseUnOdontologoDeNombreMaria_yRetornarSuId() {
        //arrange
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("AB11", "Maria", "Lopez");

        //act
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        //assert
        assertNotNull(odontologoSalidaDto);
        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("Maria", odontologoSalidaDto.getNombre());

    }

    @Test
    @Order(2)
    void deberiaEliminarseElOdontologoConId1() {
        assertDoesNotThrow(() -> odontologoService.eliminarOdontologo(1L));
        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();
        assertTrue(odontologos.isEmpty());
    }


    @Test
    @Order(3)
    void deberiaRetornarUnaListaConUnElementoSolo() {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("AB11", "Maria", "Lopez");
        odontologoService.registrarOdontologo(odontologoEntradaDto);
        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();
        assertEquals(1, odontologos.size());
    }




}


