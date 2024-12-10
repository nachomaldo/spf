package cl.ubiobio.spf.Repository;

import cl.ubiobio.spf.Entity.Cliente;
import cl.ubiobio.spf.Entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {
    // Retorna productos de acuerdo a su categoria
    // Select * FROM producto WHERE categoria = ?
    Page<Producto> findByEstadoAndCategoriaOrderByPrecioAsc(String estado, String categoria, Pageable pageable);

    // Obtiene los clientes activos por nombre, sin paginar
    // SELECT * FROM cliente WHERE nombre LIKE = %?% (Clientes sin paginar)
    List<Producto> findAllByEstadoAndNombreContaining(String estado, String nombre);

    // Obtiene todos los productos activos, paginados
    // SELECT * FROM producto WHERE estado = ?
    Page<Producto> findAllByEstado(String estado, Pageable pageable);

    //List<Producto> findByCategoriaOrderByPrecioAsc(String categoria);
}
