package cl.ubiobio.spf.Service.jpa;

import cl.ubiobio.spf.Entity.Pedido;
import cl.ubiobio.spf.Entity.Producto;
import cl.ubiobio.spf.Repository.IPedidoRepository;
import cl.ubiobio.spf.Repository.IProductoRepository;
import cl.ubiobio.spf.Service.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService implements IPedidoService {

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private IProductoRepository productoRepository;

    // Guardar un nuevo pedido
    @Override
    @Transactional
    public Pedido savePedido(Pedido pedido) {
        if (pedido != null) {

            List<Pedido> totalPedidosAgendados = getPedidosPorFecha(pedido.getFecha());

            if (totalPedidosAgendados.size()>=5) {
                return null;
            }

            return pedidoRepository.save(pedido);
        } else
            return null;
    }

    // Obtener un pedido específico
    @Override
    @Transactional
    public Pedido getPedido(Long idPedido) {
        return pedidoRepository.findById(idPedido).orElse(null);
    }

    // Obtener Pedidos por una fecha determinada
    @Override
    @Transactional(readOnly = true)
    public List<Pedido> getPedidosPorFecha(LocalDate fecha) {
        return pedidoRepository.findByFechaOrderByFechaAsc(fecha);
    }

    // Actualizar un Pedido
    @Override
    @Transactional
    public Pedido updatePedido (Pedido pedido, Long idPedido) {
        Pedido pedidoToUpdate = getPedido(idPedido);

        try {
            // Actualiza el nombre del receptor
            if (pedidoToUpdate.getNombreDelReceptor() != pedido.getNombreDelReceptor()) pedidoToUpdate.setNombreDelReceptor(
                    pedido.getNombreDelReceptor()
            );

            // Actualiza la fecha
            if (pedidoToUpdate.getFecha() != pedido.getFecha()) pedidoToUpdate.setFecha(
                    pedido.getFecha()
            );

            // Actualiza la direccion de reparto
            if (pedidoToUpdate.getDireccionDeReparto() != pedido.getDireccionDeReparto()) pedidoToUpdate.setDireccionDeReparto(
                    pedido.getDireccionDeReparto()
            );

            // Actualiza el total
            if (pedidoToUpdate.getTotal() != pedido.getTotal()) pedidoToUpdate.setTotal(
                    pedido.getTotal()
            );

            // Actualiza el estado pediente
            if (pedidoToUpdate.isPendiente() != pedido.isPendiente()) pedidoToUpdate.setPendiente(
                    pedido.isPendiente()
            );

            pedidoRepository.save(pedidoToUpdate);
            return pedidoToUpdate;
        }
        catch (NullPointerException e) { return null; }
    }

    // Eliminar un pedido (ELIMINACIÓN FÍSICA)
    @Override
    @Transactional
    public void deletePedido(Long idPedido) {
        pedidoRepository.deleteById(idPedido);
    }

    // Restar el stock de los productos venta rapida
    @Override
    public List<Producto> rapida(List<Producto> productos) {

        for (int i=0;i<productos.size();i++){

            Producto productoNuevoStock = productos.get(i);
            Producto productoViejoStock = productoRepository.findById(productoNuevoStock.getCodigo()).orElse(null);
            productoViejoStock.setStock(productoNuevoStock.getStock());
            productoRepository.save(productoNuevoStock);

        }
        return productos;
    }

}
