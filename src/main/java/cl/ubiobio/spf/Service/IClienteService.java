package cl.ubiobio.spf.Service;

import cl.ubiobio.spf.Entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IClienteService {

    // Obtener todos los clientes activos, paginados
    Page<Cliente> getClientesActivos(Pageable pageable);

    // Obtener todos los clientes inactivos, paginados
    Page<Cliente> getClientesInactivos(Pageable pageable);

    // Obtener un cliente por su ID
    Cliente getClienteById(Long idCliente);

    // Crear un cliente
    Cliente saveCliente(Cliente cliente);

    // Obtener lista de clientes activos por su nombre (sin paginar)
    List<Cliente> getClientesPorEstadoPorNombreSinPaginar(String estado, String name);

    // Obtener todos los clientes activos por su apellido, sin paginar
    List<Cliente> getClientesPorEstadoPorApellidoSinPaginar(String estado, String apellido);

    // Obtener todos los clientes activos por nombre y apellido, sin paginar
    List<Cliente> getClientesPorEstadoPorNombreApellidoSinPaginar(String estado, String nombre, String apellido);

    // Actualizar clientes
    Cliente updateCliente(Cliente cliente, Long idCliente);

    // Eliminar un cliente (eliminación lógica)
    Cliente deleteCliente(Long idCliente);

    // Reintegrar un cliente (estado de "Inactivo" a "Activo")
    Cliente reintegrarCliente(Long idCliente);
}
