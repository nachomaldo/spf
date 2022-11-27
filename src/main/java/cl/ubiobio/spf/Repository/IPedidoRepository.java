package cl.ubiobio.spf.Repository;

import cl.ubiobio.spf.Entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long> {
    // Select * FROM pedido WHERE fecha = ?
    List<Pedido> findByFechaOrderByFechaAsc(LocalDate fecha);
}
