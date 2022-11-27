package cl.ubiobio.spf.Controller;

import cl.ubiobio.spf.Entity.Pedido;
import cl.ubiobio.spf.Service.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/{idPedido}")
    public ResponseEntity<Pedido> getPedido (@PathVariable(value = "idPedido") Long idPedido){
        Pedido pedidoEncontrado = pedidoService.getPedido(idPedido);

        if (pedidoEncontrado != null) {
            return new ResponseEntity<>(pedidoEncontrado, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Obtener Pedidos por fecha
    @GetMapping("/get/{fecha}")
    public ResponseEntity<List<Pedido>> getPedidoPorFecha(@PathVariable(value = "fecha") String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        List<Pedido> pedidos = pedidoService.getPedidosPorFecha(localDate);

        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Actualizar un pedido
    @PutMapping("/update/{idPedido}")
    public ResponseEntity<Pedido> updatePedido (@RequestBody Pedido pedido,
                                                @PathVariable(value = "idPedido") Long idPedido) {
        Pedido pedidoActualizado = pedidoService.updatePedido(pedido, idPedido);

        if (pedidoActualizado == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(pedidoActualizado, HttpStatus.CREATED);
    }

    // Eliminación física de un pedido
    @DeleteMapping("/delete/{idPedido}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePedido (@PathVariable(value = "idPedido") Long idPedido) {
        pedidoService.deletePedido(idPedido);
    }
}

