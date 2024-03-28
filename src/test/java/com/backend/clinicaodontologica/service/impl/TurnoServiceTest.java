package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.TurnoSalidaDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TurnoServiceTest {

    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private TurnoService turnoService;

    @Test
    @Order(1)
    void registrarTurno() {
        //Crear odontólogo
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("AB11", "Maria", "Lopez");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        //Crear paciente
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456, LocalDate.of(2024, 3, 22), new DomicilioEntradaDto("Calle", 1234, "Localidad", "Provincia"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        TurnoEntradaDto turno = new TurnoEntradaDto(pacienteSalidaDto.getId(), odontologoSalidaDto.getId(), LocalDateTime.now());
        TurnoSalidaDto turnoSalida = assertDoesNotThrow(() -> turnoService.registrarTurno(turno));

        assertNotNull(turnoSalida);
        assertEquals(odontologoSalidaDto.getId(), turnoSalida.getOdontologo().getId());
        assertEquals(pacienteSalidaDto.getId(), turnoSalida.getPaciente().getId());
    }

    @Test
    @Order(2)
    void eliminarTurno() {
        assertDoesNotThrow(() -> turnoService.eliminarTurno(1L));
        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();
        assertTrue(turnos.isEmpty());
    }

    @Test
    @Order(3)
    void listarTurnos() {
        //Crear odontólogo
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("AB11", "Maria", "Lopez");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        //Crear paciente
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456, LocalDate.of(2024, 3, 22), new DomicilioEntradaDto("Calle", 1234, "Localidad", "Provincia"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        TurnoEntradaDto turno = new TurnoEntradaDto(pacienteSalidaDto.getId(), odontologoSalidaDto.getId(), LocalDateTime.now());
        TurnoSalidaDto turnoSalida = assertDoesNotThrow(() -> turnoService.registrarTurno(turno));

        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();
        assertEquals(1, turnos.size());
    }
}