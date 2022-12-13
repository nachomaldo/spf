package cl.ubiobio.spf.Controller;

import cl.ubiobio.spf.Entity.Pedido;
import cl.ubiobio.spf.Entity.Producto;
import cl.ubiobio.spf.Service.IPedidoService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import java.text.ParseException;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;

    // Crear un nuevo pedido
    @Secured("ROLE_OPERADOR")
    @PostMapping("")
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) throws ParseException {
        if (pedido != null) {
            Pedido pedidoCreado = pedidoService.savePedido(pedido);

            // Si la cantidad de pedidos supera el maximo diario
            if (pedidoCreado == null) return new ResponseEntity<>(HttpStatus.CONFLICT);
            else
                return new ResponseEntity<>(pedidoCreado, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener un pedido específico
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/{idPedido}")
    public ResponseEntity<Pedido> getPedido (@PathVariable(value = "idPedido") Long idPedido){
        Pedido pedidoEncontrado = pedidoService.getPedido(idPedido);

        if (pedidoEncontrado != null) {
            return new ResponseEntity<>(pedidoEncontrado, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Obtener Pedidos por fecha
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/get/{fecha}")
    public ResponseEntity<List<Pedido>> getPedidoPorFecha(@PathVariable(value = "fecha") String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        List<Pedido> pedidos = pedidoService.getPedidosPorFecha(localDate);

        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Actualizar un pedido
    @Secured("ROLE_OPERADOR")
    @PutMapping("/update/{idPedido}")
    public ResponseEntity<Pedido> updatePedido (@RequestBody Pedido pedido,
                                                @PathVariable(value = "idPedido") Long idPedido) {
        Pedido pedidoActualizado = pedidoService.updatePedido(pedido, idPedido);

        if (pedidoActualizado == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(pedidoActualizado, HttpStatus.CREATED);
    }

    // compra rapida
    @Secured("ROLE_OPERADOR")
    @PutMapping("/rapida")
    public ResponseEntity<?> compraRapida (@RequestBody Pedido pedido) {
        List<Producto> productosRapida = pedidoService.rapida(pedido.getProductos());

        if (productosRapida.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Secured("ROLE_OPERADOR")
    // Eliminación física de un pedido
    @DeleteMapping("/delete/{idPedido}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePedido (@PathVariable(value = "idPedido") Long idPedido) {
        pedidoService.deletePedido(idPedido);
    }
}

