package cl.ubiobio.spf.Service.jpa;

import cl.ubiobio.spf.Entity.Cliente;
import cl.ubiobio.spf.Repository.IClienteRepository;
import cl.ubiobio.spf.Service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    // Guardar un nuevo cliente
    @Override
    @Transactional
    public Cliente saveCliente(Cliente cliente) {
        if (cliente != null) return clienteRepository.save(cliente);
        else
            return null;
    }

    // Obtener todos los clientes activos (que no han sido eliminados), paginados
    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> getClientesActivos(Pageable pageable) {
        return clienteRepository.findAllByEstado("Activo", pageable);
    }

    // Obtener todos los clientes inactivos, paginados
    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> getClientesInactivos(Pageable pageable) {
        return clienteRepository.findAllByEstado("Inactivo", pageable);
    }

    // Obtener uno o más clientes por nombre y estado, sin paginar
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getClientesPorEstadoPorNombreSinPaginar(String estado, String name) {
        if (name != null) return clienteRepository.findAllByEstadoAndNombreContaining(estado, name);
        else
            return null;
    }

    // Obtener uno o más clientes por apellido y estado, sin paginar
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getClientesPorEstadoPorApellidoSinPaginar(String estado, String apellido) {
        if (apellido != null) return clienteRepository.findAllByEstadoAndApellidoContaining(estado, apellido);
        else
            return null;
    }

    // Obtener uno o más clientes por su nombre, apellido y estado, sin paginar
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getClientesPorEstadoPorNombreApellidoSinPaginar(String estado, String nombre, String apellido) {
        if (nombre != null && apellido != null) return clienteRepository.
                findAllByEstadoAndNombreContainingAndApellidoContaining(estado, nombre, apellido);
        else
            return null;
    }

    // Obtener un cliente específico por su ID
    @Override
    @Transactional(readOnly = true)
    public Cliente getClienteById(Long idCliente) {
        return clienteRepository.findById(idCliente).orElse(null);
    }

    // Actualizar un cliente
    @Override
    @Transactional
    public Cliente updateCliente(Cliente cliente, Long idCliente) {
        Cliente clienteToUpdate = getClienteById(idCliente);

        if (clienteToUpdate != null) {
            // Actualiza el nombre del cliente
            if (!clienteToUpdate.getNombre().equalsIgnoreCase(cliente.getNombre()))
                clienteToUpdate.setNombre(cliente.getNombre());

            // Actualiza el apellido del cliente
            if (!clienteToUpdate.getApellido().equalsIgnoreCase(cliente.getApellido()))
                clienteToUpdate.setApellido(cliente.getApellido());

            // Actualiza número de teléfono
            if (!clienteToUpdate.getTelefono().equals(cliente.getTelefono()))
                clienteToUpdate.setTelefono("+569" + cliente.getTelefono());

            // Actualiza la direccion del cliente
            if (!clienteToUpdate.getDireccion().equalsIgnoreCase(cliente.getDireccion()))
                clienteToUpdate.setDireccion(cliente.getDireccion());

            // Actualiza el e-mail
            if (clienteToUpdate.getEmail() == null && cliente.getEmail() != null) {
                clienteToUpdate.setEmail(cliente.getEmail());
            }
            else if (clienteToUpdate.getEmail() != null) {
                if (!clienteToUpdate.getEmail().equals(cliente.getEmail())) {
                    clienteToUpdate.setEmail(cliente.getEmail());
                }
            }

            clienteRepository.save(clienteToUpdate);
            return clienteToUpdate;
        } else
            return null;
    }

    // Eliminar un cliente (lógica)
    @Override
    @Transactional
    public Cliente deleteCliente(Long idCliente) {
        Cliente clienteToDelete = getClienteById(idCliente);

        if (clienteToDelete.getEstado().equalsIgnoreCase("Inactivo")) {
            return null;
        }

/* Pendiente tratar los pedidos relacionados al cliente
        if (!clienteToDelete.getPedidos().isEmpty()) {
            for (Pedido pedido : clienteToDelete.getPedidos()) {
                if (pedido.pendiente() == null && pedido.getFecha().isBefore(LocalDate.now()) && pedido.getAsistencia() == 1) {
                    throw new ResourceDeletionNotPossibleException("El paciente no puede ser eliminado. Presenta atenciones impagadas");
                }
            }
        }
*/

        if (clienteToDelete != null) {

            // Se setea como 'Inactivo' al cliente
            clienteToDelete.setEstado("Inactivo");

            /*
            // Eliminación lógica de su anamnesis
            if (patientToDelete.getAnamnesis() != null) patientToDelete.getAnamnesis().setEstado("Inactivo");

            // Eliminación lógica de su ficha de tratamiento
            if (patientToDelete.getFichaTratamiento() != null) {
                patientToDelete.getFichaTratamiento().setEstado("Inactivo");

                // Se setean como inactivas las sesiones de terapia que tenga que el paciente
                if (patientToDelete.getFichaTratamiento().getSesionesDeTerapia().size() > 0 || patientToDelete.getFichaTratamiento().getSesionesDeTerapia() != null) {
                    for (SesionTerapia sesion : patientToDelete.getFichaTratamiento().getSesionesDeTerapia()) {
                        sesion.setEstado("Inactivo");
                    }
                }
            }
            */

            // Se guardan los cambios y se retorna el paciente eliminado
            return clienteRepository.save(clienteToDelete);
        }
        else
            return null;
    }
    // Reintegrar un cliente a la consulta (de INACTIVO a ACTIVO)
    @Override
    @Transactional
    public Cliente reintegrarCliente(Long idCliente) {
        Cliente clienteToIntegrate = getClienteById(idCliente);

        if (clienteToIntegrate != null) {

            // Se reintegra el cliente
            clienteToIntegrate.setEstado("Activo");
/*
            // Si existe una anamnesis registrada, esta es reintegrada
            if (pacienteToIntegrate.getAnamnesis() != null) pacienteToIntegrate.getAnamnesis().setEstado("Activo");

            // Si existe una ficha de tratamiento registrada, esta es reintegrada
            if (pacienteToIntegrate.getFichaTratamiento() != null) {
                pacienteToIntegrate.getFichaTratamiento().setEstado("Activo");

                // Si existen sesiones de terapia registradas, estas son reintegradas
                if (pacienteToIntegrate.getFichaTratamiento().getSesionesDeTerapia() != null || pacienteToIntegrate.getFichaTratamiento().getSesionesDeTerapia().size() > 0) {
                    for (SesionTerapia sesion : pacienteToIntegrate.getFichaTratamiento().getSesionesDeTerapia()) {
                        sesion.setEstado("Activo");
                    }
                }
            }
*/
            // Se guardan los cambios
            return clienteRepository.save(clienteToIntegrate);
        }
        else
            return null;
    }
}
