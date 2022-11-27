package cl.ubiobio.spf.Repository;

import cl.ubiobio.spf.Entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

    // Retorna clientes de acuerdo a su estado
    // SELECT * FROM cliente WHERE estado = ? (Retornar solo los clientes activos)
    Page<Cliente> findAllByEstado(String estado, Pageable pageable);

    // Obtiene los clientes activos por nombre, sin paginar
    // SELECT * FROM cliente WHERE nombre LIKE = %?% (Clientes sin paginar)
    List<Cliente> findAllByEstadoAndNombreContaining(String estado, String nombre);

    // Obtiene todos los clientes activos, por apellido, sin paginar
    // SELECT * FROM cliente WHERE estado = ? AND apellido LIKE = %?%
    List<Cliente> findAllByEstadoAndApellidoContaining(String estado, String apellido);

    // Obtiene todos los clientes activos, por nombre y apellido, sin paginar
    // SELECT * FROM cliente WHERE estado = ? AND nombre LIKE = %?% AND apellido LIKE =%?%
    List<Cliente> findAllByEstadoAndNombreContainingAndApellidoContaining(String estado, String nombre, String apellido);
}
