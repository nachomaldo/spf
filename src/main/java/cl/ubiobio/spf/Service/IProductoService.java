package cl.ubiobio.spf.Service;

import cl.ubiobio.spf.Entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.text.ParseException;
import java.util.List;

public interface IProductoService {

    // Guardar un producto
    Producto saveProducto(Producto producto) throws ParseException;

    // Obtener un producto por ID
    Producto getProducto(Long codigo);

    // Obtener todos los productos para una categoria determinada, paginados
    Page<Producto> getProductosPorCategoria(String categoria, Pageable pageable);

    // Actualizar un producto
    Producto updateProducto (Producto producto, Long codigo);

    //Eliminar un producto
    void deleteProducto (Long codigo);
}
