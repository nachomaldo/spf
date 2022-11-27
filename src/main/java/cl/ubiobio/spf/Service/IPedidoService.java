package cl.ubiobio.spf.Service;

import cl.ubiobio.spf.Entity.Pedido;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface IPedidoService {

    // Guardar un pedido
    Pedido savePedido(Pedido pedido) throws ParseException;

    // Obtener un pedido por ID
    Pedido getPedido(Long idPedido);

    // Obtener todos los horarios par auna fecha determinada
    List<Pedido> getPedidosPorFecha(LocalDate fecha);

    // Actualizar un pedido
    Pedido updatePedido (Pedido pedido, Long idPedido);

    //Eliminar un pedido
    void deletePedido (Long idPedido);
}
