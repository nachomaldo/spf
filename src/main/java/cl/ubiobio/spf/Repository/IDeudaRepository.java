package cl.ubiobio.spf.Repository;

import cl.ubiobio.spf.Entity.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDeudaRepository extends JpaRepository<Deuda, Long> {
}
