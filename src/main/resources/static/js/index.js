function inicializarform() {
    // Obtener el formulario por su ID
     const form = document.getElementById('registroForm');

     // Manejar el envío del formulario de registro de paciente
     form.addEventListener('submit', async function(event) {
         event.preventDefault(); // Evitar que el formulario se envíe normalmente

         // Obtener valores del formulario
         const nombre = document.getElementById('nombre').value;
         const apellido = document.getElementById('apellido').value;
         const dni = document.getElementById('dni').value;
         const fechaIngresoInput = document.getElementById('fechaIngreso');
         const calle = document.getElementById('calle').value;
         const numero = document.getElementById('numero').value;
         const localidad = document.getElementById('localidad').value;
         const provincia = document.getElementById('provincia').value;

         // Obtener fecha actual
         const fechaActual = new Date();
         const fechaIngreso = new Date(fechaIngresoInput.value);

         // Validar si la fecha de ingreso es anterior a la fecha actual
         if (fechaIngreso < fechaActual) {
             alert('La fecha de ingreso no puede ser anterior a la fecha actual.');
             return; // Detener el proceso de envío del formulario
         }

         // Crear objeto paciente
         const paciente = {
             nombre: nombre,
             apellido: apellido,
             dni: parseInt(dni),
             fechaIngreso: fechaIngresoInput.value,
             domicilioEntradaDto: {
                 calle: calle,
                 numero: parseInt(numero),
                 localidad: localidad,
                 provincia: provincia
             }
         };

         // Llamar a registrarPaciente
         await registrarPaciente(paciente);

         // Limpiar el formulario
         form.reset();

         // Volver a listar pacientes después de registrar uno nuevo
         await cargarDatosPacientes();
     });

}

 // Función para manejar errores de respuesta HTTP
 function handleErrors(response) {
     if (!response.ok) {
         throw Error(response.statusText);
     }
     return response;
 }

 // Función para consumir el servicio de registrar paciente
 async function registrarPaciente(paciente) {
     const response = await fetch('/pacientes/registrar', {
         method: 'POST',
         headers: {
             'Content-Type': 'application/json'
         },
         body: JSON.stringify(paciente)
     });
     handleErrors(response);
     return response.json();
 }

 // Función para consumir el servicio de listar pacientes
 async function listarPacientes() {
     const response = await fetch('/pacientes');
     handleErrors(response);
     return response.json();
 }

 // Función para consumir el servicio de buscar paciente por ID
 async function buscarPacientePorId(id) {
     const response = await fetch(`/buscarPacientePorId/${id}`);
     handleErrors(response);
     return response.json();
 }

 // Función para consumir el servicio de eliminar paciente por ID
 async function eliminarPaciente(id) {
     const response = await fetch(`/eliminarPaciente/${id}`, {
         method: 'DELETE'
     });
     handleErrors(response);
 }

 // Función para consumir el servicio de modificar paciente por ID
 async function modificarPaciente(paciente, id) {
     const response = await fetch(`/modificarPaciente/${id}`, {
         method: 'PUT',
         headers: {
             'Content-Type': 'application/json'
         },
         body: JSON.stringify(paciente)
     });
     handleErrors(response);
     return response.json();
 }

 // Función para mostrar la lista de pacientes en el HTML
 function mostrarListaPacientes(pacientes) {
     const listaPacientesElement = document.getElementById('listaPacientes');

     // Limpiar la lista antes de agregar nuevos elementos
     listaPacientesElement.innerHTML = '';
     console.log("mostrarListaPacientes")
     console.log(pacientes)
     // Iterar sobre la lista de pacientes y agregar cada uno como un elemento de lista
     pacientes.forEach(function(paciente) {
         const listItem = document.createElement('li');
         listItem.textContent = `Nombre: ${paciente.nombre}, Apellido: ${paciente.apellido}, DNI: ${paciente.dni}, Fecha de Ingreso: ${paciente.fechaIngreso}, Domicilio: ${paciente.domicilioSalidaDto.calle} ${paciente.domicilioSalidaDto.numero}, ${paciente.domicilioSalidaDto.localidad}, ${paciente.domicilioSalidaDto.provincia}`;
         listaPacientesElement.appendChild(listItem);
     });
 }

 // Llamar a listarPacientes y mostrar la lista en el HTML después de cargar la página
 document.addEventListener('DOMContentLoaded', async function() {
    inicializarform();
    cargarDatosPacientes();
 });

 async function cargarDatosPacientes() {
     const pacientes = await listarPacientes();
     mostrarListaPacientes(pacientes);
 }