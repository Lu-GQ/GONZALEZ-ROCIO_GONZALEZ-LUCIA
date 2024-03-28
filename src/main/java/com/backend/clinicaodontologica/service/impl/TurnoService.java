package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.OdontologoRepository;
import com.backend.clinicaodontologica.repository.PacienteRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final OdontologoRepository odontologoRepository;
    private final PacienteRepository pacienteRepository;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, OdontologoRepository odontologoRepository, PacienteRepository pacienteRepository) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.odontologoRepository = odontologoRepository;
        this.pacienteRepository = pacienteRepository;
    }


    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {
        TurnoSalidaDto turnoSalidaDto;
        Odontologo odontologo = odontologoRepository.getReferenceById(turnoEntradaDto.getOdontologoId());
        Paciente paciente = pacienteRepository.getReferenceById(turnoEntradaDto.getPacienteId());

        String pacienteNoEnBdd = "El paciente no se encuentra en nuestra base de datos";
        String odontologoNoEnBdd = "El odontologo no se encuentra en nuestra base de datos";
        String ambosNulos = "El paciente y el odontologo no se encuentran en nuestra base de datos";

        if (paciente == null || odontologo == null) {
            if (paciente == null && odontologo == null) {
                LOGGER.error(ambosNulos);
                throw new BadRequestException(ambosNulos);
            } else if (paciente == null) {
                LOGGER.error(pacienteNoEnBdd);
                throw new BadRequestException(pacienteNoEnBdd);
            } else {
                LOGGER.error(odontologoNoEnBdd);
                throw new BadRequestException(odontologoNoEnBdd);
            }

        } else {
            Turno turnoNuevo = turnoRepository.save(modelMapper.map(turnoEntradaDto, Turno.class));
            turnoSalidaDto = entidadADtoSalida(turnoNuevo, paciente, odontologo);
            LOGGER.info("Nuevo turno registrado con exito: {}", turnoSalidaDto);
        }


        return turnoSalidaDto;
    }


    @Override
    public List<TurnoSalidaDto> listarTurnos() {

        List<TurnoSalidaDto> turnosSalidaDto = turnoRepository.findAll()
                .stream()
                .map(turno -> {
                    TurnoSalidaDto turnoSalidaDto =  modelMapper.map(turno, TurnoSalidaDto.class);
                    turnoSalidaDto.setPacienteSalidaDto(modelMapper.map(turno.getPaciente(), PacienteSalidaDto.class));
                    turnoSalidaDto.setOdontologoSalidaDto(modelMapper.map(turno.getOdontologo(), OdontologoSalidaDto.class));
                    return turnoSalidaDto;
                })
                .toList();

        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnosSalidaDto));
        return turnosSalidaDto;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {

        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            turnoEncontrado.setPacienteSalidaDto(modelMapper.map(turnoBuscado.getPaciente(), PacienteSalidaDto.class));
            turnoEncontrado.setOdontologoSalidaDto(modelMapper.map(turnoBuscado.getOdontologo(), OdontologoSalidaDto.class));

            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));

        } else
            LOGGER.error("No se ha encontrado el turno con id {}", id);


        return turnoEncontrado;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {

        if (buscarTurnoPorId(id) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id {}", id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
            throw new ResourceNotFoundException("No existe registro de turno con id " + id);
        }
    }

    @Override
    public TurnoSalidaDto modificarTurno(TurnoEntradaDto turnoEntradaDto, Long id) {
        Turno turno = turnoRepository.findById(id).orElse(null);
        if (turno == null) {
            return null;
        }
        Odontologo odontologo = odontologoRepository.getReferenceById(turnoEntradaDto.getOdontologoId());
        Paciente paciente = pacienteRepository.getReferenceById(turnoEntradaDto.getPacienteId());
        turno.setOdontologo(odontologo);
        turno.setPaciente(paciente);
        turno.setFechaYHora(turnoEntradaDto.getFechaYHora());
        turnoRepository.save(turno);
        return null;
    }

    private TurnoSalidaDto entidadADtoSalida(Turno turno, Paciente pacienteSalidaDto, Odontologo odontologoSalidaDto) {
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turno, TurnoSalidaDto.class);
        turnoSalidaDto.setPacienteSalidaDto(modelMapper.map(pacienteSalidaDto, PacienteSalidaDto.class));
        turnoSalidaDto.setOdontologoSalidaDto(modelMapper.map(odontologoSalidaDto, OdontologoSalidaDto.class));
        return turnoSalidaDto;
    }


}