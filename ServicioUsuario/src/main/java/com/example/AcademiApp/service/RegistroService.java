package com.example.AcademiApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.AcademiApp.model.Entities.Apoderado;
import com.example.AcademiApp.model.Entities.Directivo;
import com.example.AcademiApp.model.Entities.Docente;
import com.example.AcademiApp.model.Entities.Estudiante;
import com.example.AcademiApp.model.Entities.Inspector;
import com.example.AcademiApp.model.Entities.Usuario;
import com.example.AcademiApp.model.Entities.direccion.Ciudad;
import com.example.AcademiApp.model.Entities.direccion.Comuna;
import com.example.AcademiApp.model.Entities.direccion.Direccion;
import com.example.AcademiApp.model.Entities.direccion.Pais;
import com.example.AcademiApp.model.Entities.direccion.Region;
import com.example.AcademiApp.model.request.RegistroDirectivoRequest;
import com.example.AcademiApp.model.request.RegistroDocenteRequest;
import com.example.AcademiApp.model.request.RegistroEstudianteRequest;
import com.example.AcademiApp.model.request.RegistroInspectorRequest;
import com.example.AcademiApp.model.request.RegistroRequest;
import com.example.AcademiApp.model.request.direccion.DireccionRequest;
import com.example.AcademiApp.model.response.EstudianteResponse;
import com.example.AcademiApp.repository.ApoderadoRespository;
import com.example.AcademiApp.repository.DirectivoRespository;
import com.example.AcademiApp.repository.DocenteRepository;
import com.example.AcademiApp.repository.EstudianteRepository;
import com.example.AcademiApp.repository.FuncionarioRepository;
import com.example.AcademiApp.repository.InspectorRespository;
import com.example.AcademiApp.repository.direccion.CiudadRepository;
import com.example.AcademiApp.repository.direccion.ComunaRepository;
import com.example.AcademiApp.repository.direccion.PaisRepository;
import com.example.AcademiApp.repository.direccion.RegionRepository;

import jakarta.transaction.Transactional;

@Service
public class RegistroService {

   @Autowired
   private EstudianteRepository estudianteRepository;

   @Autowired
   private ApoderadoRespository apoderadoRespository;

   @Autowired
   private DocenteRepository docenteRepository;
   @Autowired
   private InspectorRespository inspectorRespository;
   @Autowired
   private DirectivoRespository directivoRespository;
   @Autowired
   private FuncionarioRepository funcionarioRepository;

   @Autowired
   private PaisRepository paisRepository;
   @Autowired
   private RegionRepository regionRepository;
   @Autowired
   private CiudadRepository ciudadRepository;
   @Autowired
   private ComunaRepository comunaRepository;

   // Crea usuarios y apoderados
   @Transactional
   public String registrarAlumno(RegistroRequest nuevoRegistro) {

      // No se puede duplicar un estudiante
      if (estudianteRepository.existsByNumrun(nuevoRegistro.alumno().numrun())) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este alumno ya está registrado.");
      }

      // Transformar el request a entidad

      // 1. Extraemos los datos del apoderado del Request
      RegistroEstudianteRequest datosApo = nuevoRegistro.apoderado();

      // BUSCAMOS si el apoderado ya existe por su numrun
      Apoderado apoderado = apoderadoRespository.findByNumrun(datosApo.numrun()).orElseGet(() -> {
         // Si NO existe, creamos uno nuevo
         Apoderado nuevoApo = new Apoderado();
         nuevoApo.setUsu_email(datosApo.email());
         nuevoApo.setUsu_pass(datosApo.password());
         nuevoApo.setNumrun(datosApo.numrun());
         nuevoApo.setUsu_dvrun(datosApo.dvRun());

         nuevoApo.setUsu_nombre(datosApo.nombre());
         nuevoApo.setUsu_snombre(datosApo.segundoNombre());
         nuevoApo.setUsu_appaterno(datosApo.apellidoPaterno());
         nuevoApo.setUsu_apmaterno(datosApo.apellidoMaterno());

         nuevoApo.setApode_parentesco(datosApo.parentesco());

         if (datosApo.direcciones() != null) {
            procesarDirecciones(nuevoApo, datosApo.direcciones());
         }

         return apoderadoRespository.save(nuevoApo);
      });

      // Creacion alumno
      RegistroEstudianteRequest alu = nuevoRegistro.alumno();
      Estudiante estudiante = new Estudiante();
      estudiante.setUsu_email(alu.email());
      estudiante.setUsu_pass(alu.password());
      estudiante.setNumrun(alu.numrun());
      estudiante.setUsu_dvrun(alu.dvRun());

      estudiante.setUsu_nombre(alu.nombre());
      estudiante.setUsu_snombre(alu.segundoNombre());
      estudiante.setUsu_appaterno(alu.apellidoPaterno());
      estudiante.setUsu_apmaterno(alu.apellidoMaterno());

      estudiante.setEstu_parentesco(nuevoRegistro.apoderado().parentesco());

      // PROCESAR DIRECCIONES DEL ALUMNO
      if (alu.direcciones() != null) {
         procesarDirecciones(estudiante, alu.direcciones());
      }

      // Join de tablas
      estudiante.setApoderado(apoderado);
      estudianteRepository.save(estudiante);

      return "Alumno y apoderado registrados correctamente";

   }

   // Buscar todos
   public List<Estudiante> obtenerTodosEstudiantes() {
      return estudianteRepository.findAll();
   }

   public List<Apoderado> obtenerTodosApoderados() {
      return apoderadoRespository.findAll();
   }

   // Buscar * id
   // Buscar * id
   public EstudianteResponse obtenerEstudiante(int id) {

      Estudiante est = estudianteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                  HttpStatus.NOT_FOUND,
                  "Estudiante no encontrado"));

      // Mapeamos manualmente la entidad al Record de respuesta
      return new EstudianteResponse(
            est.getUsu_id(),
            est.getUsu_nombre(),
            est.getUsu_appaterno(),
            est.getUsu_email(),
            est.getEstu_parentesco(),
            est.getApoderado() != null
                  ? est.getApoderado().getUsu_nombre()
                  : "Sin apoderado");
   }

   // Actualizar
   @Transactional
   public void actualizarAlumnoYApoderado(int estudianteId, RegistroRequest datosNuevos) {
      // 1. Buscamos el estudiante
      Estudiante est = estudianteRepository.findById(estudianteId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

      // 2. ACTUALIZAMOS EL ESTUDIANTE (Campos básicos)
      RegistroEstudianteRequest datosAlu = datosNuevos.alumno();
      est.setUsu_nombre(datosAlu.nombre());
      est.setUsu_appaterno(datosAlu.apellidoPaterno());
      est.setUsu_apmaterno(datosAlu.apellidoMaterno());
      est.setUsu_email(datosAlu.email());

      // Direcciones del Alumno
      if (datosAlu.direcciones() != null) {
         est.getDirecciones().clear();
         procesarDirecciones(est, datosAlu.direcciones());
      }

      // 3. LÓGICA DE CAMBIO O EDICIÓN DE APODERADO
      RegistroEstudianteRequest datosApo = datosNuevos.apoderado();

      // ¿El RUN que viene en el Request es distinto al que ya tiene el alumno?
      if (est.getApoderado().getNumrun() != datosApo.numrun()) {

         // ESCENARIO A: El "nuevo" apoderado ya existe en la BD
         Apoderado nuevoApo = apoderadoRespository.findByNumrun(datosApo.numrun())
               .orElseGet(() -> {
                  // ESCENARIO B: El nuevo apoderado no existe, lo creamos desde cero
                  Apoderado nuevo = new Apoderado();
                  nuevo.setNumrun(datosApo.numrun());
                  nuevo.setUsu_dvrun(datosApo.dvRun());
                  nuevo.setUsu_pass(datosApo.password()); // Password inicial
                  return nuevo;
               });

         // Actualizamos los datos del apoderado (sea nuevo o existente)
         nuevoApo.setUsu_nombre(datosApo.nombre());
         nuevoApo.setUsu_appaterno(datosApo.apellidoPaterno());
         nuevoApo.setUsu_apmaterno(datosApo.apellidoMaterno());
         nuevoApo.setUsu_email(datosApo.email());

         // Direcciones del nuevo Apoderado
         if (datosApo.direcciones() != null) {
            nuevoApo.getDirecciones().clear();
            procesarDirecciones(nuevoApo, datosApo.direcciones());
         }

         // Realizamos el cambio de vínculo
         est.setApoderado(nuevoApo);
         apoderadoRespository.save(nuevoApo);

      } else {
         // ESCENARIO C: Es el mismo apoderado, solo editamos sus datos
         Apoderado apoActual = est.getApoderado();
         apoActual.setUsu_nombre(datosApo.nombre());
         apoActual.setUsu_appaterno(datosApo.apellidoPaterno());
         apoActual.setUsu_apmaterno(datosApo.apellidoMaterno());
         apoActual.setUsu_email(datosApo.email());

         if (datosApo.direcciones() != null) {
            apoActual.getDirecciones().clear();
            procesarDirecciones(apoActual, datosApo.direcciones());
         }
      }

      estudianteRepository.save(est);
   }

   // ------------------- Funcionario

   // Registro
   @Transactional
   public void registrarDocente(RegistroDocenteRequest req) {
      validarDuplicadoFuncionario(req.numrun());

      Docente docente = new Docente();
      docente.setUsu_email(req.email());
      docente.setUsu_pass(req.password());
      docente.setNumrun(req.numrun());
      docente.setUsu_dvrun(req.dvRun());
      docente.setUsu_nombre(req.nombre());
      docente.setUsu_snombre(req.segundoNombre());
      docente.setUsu_appaterno(req.apellidoPaterno());
      docente.setUsu_apmaterno(req.apellidoMaterno());

      // Procesar Direcciones N a 1
      procesarDirecciones(docente, req.direcciones());

      docente.setFunci_titulo(req.titulo());
      docente.setDocen_espec(req.especialidad());

      if (req.direcciones() != null) {
         procesarDirecciones(docente, req.direcciones());
      }

      docenteRepository.save(docente);
   }

   @Transactional
   public void registrarDirectivo(RegistroDirectivoRequest req) {
      validarDuplicadoFuncionario(req.numrun());

      Directivo directivo = new Directivo();
      directivo.setUsu_email(req.email());
      directivo.setUsu_pass(req.password());
      directivo.setNumrun(req.numrun());
      directivo.setUsu_dvrun(req.dvRun());
      directivo.setUsu_nombre(req.nombre());
      directivo.setUsu_snombre(req.segundoNombre());
      directivo.setUsu_appaterno(req.apellidoPaterno());
      directivo.setUsu_apmaterno(req.apellidoMaterno());

      // Procesar Direcciones N a 1
      procesarDirecciones(directivo, req.direcciones());

      directivo.setFunci_titulo(req.titulo());
      directivo.setDirect_cargo(req.cargoDirectivo());
      if (req.direcciones() != null) {
         procesarDirecciones(directivo, req.direcciones());
      }

      directivoRespository.save(directivo);

   }

   @Transactional
   public void registrarInspector(RegistroInspectorRequest req) {
      validarDuplicadoFuncionario(req.numrun());

      Inspector inspector = new Inspector();

      inspector.setUsu_email(req.email());
      inspector.setUsu_pass(req.password());
      inspector.setNumrun(req.numrun());
      inspector.setUsu_dvrun(req.dvRun());
      inspector.setUsu_nombre(req.nombre());
      inspector.setUsu_snombre(req.segundoNombre());
      inspector.setUsu_apmaterno(req.apellidoPaterno());
      inspector.setUsu_apmaterno(req.apellidoMaterno());
      inspector.setFunci_titulo(req.titulo());

      // Procesar Direcciones N a 1
      procesarDirecciones(inspector, req.direcciones());

      inspector.setInspec_nivel(req.nivel());
      if (req.direcciones() != null) {
         procesarDirecciones(inspector, req.direcciones());
      }

      inspectorRespository.save(inspector);

   }

   // --- MÉTODOS DE APOYO (Privados) ---

   private void validarDuplicadoFuncionario(Integer numrun) {
      if (numrun == null) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El RUN es obligatorio.");
      }
      // Revisa en tu repo de funcionarios o usuario base
      if (funcionarioRepository.existsByNumrun(numrun)) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un funcionario con este RUN.");
      }
   }

   /**
    * Método privado para transformar DireccionDTO en entidades Direccion
    * y asociarlas al usuario correspondiente.
    */
   private void procesarDirecciones(Usuario usuario, List<DireccionRequest> dtos) {
      if (dtos == null)
         return;

      for (DireccionRequest dto : dtos) {
         // 1. Pais
         Pais pais = paisRepository.findByNombre(dto.nombrePais())
               .orElseGet(() -> {
                  Pais p = new Pais();
                  p.setPais_nombre(dto.nombrePais());
                  return paisRepository.save(p);
               });

         // 2. Region (pasamos el nombre y el ID del país encontrado/creado)
         Region region = regionRepository.findByNombreAndPais(dto.nombreRegion(), pais.getPais_id())
               .orElseGet(() -> {
                  Region r = new Region();
                  r.setRegi_nombre(dto.nombreRegion());
                  r.setPais(pais);
                  return regionRepository.save(r);
               });

         // 3. Ciudad (pasamos el nombre y el ID de la región)
         Ciudad ciudad = ciudadRepository.findByNombreAndRegion(dto.nombreCiudad(), region.getRegi_id())
               .orElseGet(() -> {
                  Ciudad c = new Ciudad();
                  c.setCiudad_nombre(dto.nombreCiudad());
                  c.setRegion(region);
                  return ciudadRepository.save(c);
               });

         // 4. Comuna (pasamos el nombre y el ID de la ciudad)
         Comuna comuna = comunaRepository.findByNombreAndCiudad(dto.nombreComuna(), ciudad.getCiudad_id())
               .orElseGet(() -> {
                  Comuna com = new Comuna();
                  com.setComu_nombre(dto.nombreComuna());
                  com.setCiudad(ciudad);
                  return comunaRepository.save(com);
               });

         // 5. Dirección final
         Direccion dir = new Direccion();
         dir.setAdd_calle(dto.calle());
         dir.setAdd_numero(dto.numero());
         dir.setAdd_letra(dto.letra());
         dir.setComuna(comuna);

         usuario.agregarDireccion(dir);
      }
   }

}
