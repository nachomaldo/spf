package cl.ubiobio.spf.Controller;

import cl.ubiobio.spf.Entity.Producto;
import cl.ubiobio.spf.Exception.InvalidParameterException;
import cl.ubiobio.spf.Service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    // Crear un nuevo producto
    @PostMapping("")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) throws ParseException {
        if (producto != null) {
            Producto productoCreado = productoService.saveProducto(producto);
                return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener un producto específico
    @GetMapping("/{codigo}")
    public ResponseEntity<Producto> getProducto (@PathVariable(value = "codigo") Long codigo){
        Producto productoEncontrado = productoService.getProducto(codigo);

        if (productoEncontrado != null) {
            return new ResponseEntity<>(productoEncontrado, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Obtener producto por categoria, paginados
    @GetMapping("/get/{categoria}/{pageNumber}")
    public ResponseEntity<Page<Producto>> getProductoPorCategoria(@PathVariable(value = "categoria") String categoria,
                                                                  @PathVariable(value = "pageNumber") Integer nroPagina) {
        Page pageOfProductos =  productoService.getProductosPorCategoria(categoria, PageRequest.of(nroPagina,5));

        if(!pageOfProductos.isEmpty()) return new ResponseEntity<>(pageOfProductos, HttpStatus.OK);
        else
            throw new InvalidParameterException("El número de página proporcionado no es válido.");
    }

    // Actualizar un producto
    @PutMapping("/update/{codigo}")
    public ResponseEntity<Producto> updateProducto (@RequestBody Producto producto,
                                                @PathVariable(value = "codigo") Long codigo) {
        Producto productoActualizado = productoService.updateProducto(producto, codigo);

        if (productoActualizado == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(productoActualizado, HttpStatus.CREATED);
    }

    // Eliminación física de un producto
    @DeleteMapping("/delete/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProducto (@PathVariable(value = "codigo") Long codigo) {
        productoService.deleteProducto(codigo);
    }

}
