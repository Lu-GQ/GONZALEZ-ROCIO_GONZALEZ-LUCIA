package com.backend.clinicaodontologica.dto.salida;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class TurnoSalidaDto {
    private Long id;
    private PacienteSalidaDto paciente;
    private OdontologoSalidaDto odontologo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaYHora;

    public TurnoSalidaDto() {
    }

    public TurnoSalidaDto(Long id, PacienteSalidaDto pacienteSalidaDto, OdontologoSalidaDto odontologoSalidaDto, LocalDateTime fechaYHora) {
        this.id = id;
        this.paciente = pacienteSalidaDto;
        this.odontologo = odontologoSalidaDto;
        this.fechaYHora = fechaYHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PacienteSalidaDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteSalidaDto pacienteSalidaDto) {
        this.paciente = pacienteSalidaDto;
    }

    public OdontologoSalidaDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoSalidaDto odontologoSalidaDto) {
        this.odontologo = odontologoSalidaDto;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }
}