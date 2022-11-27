package cl.ubiobio.spf.Repository;

import cl.ubiobio.spf.Entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {
    // Retorna productos de acuerdo a su categoria
    // Select * FROM producto WHERE categoria = ?
    Page<Producto> findByCategoriaOrderByPrecioAsc(String categoria, Pageable pageable);
}
