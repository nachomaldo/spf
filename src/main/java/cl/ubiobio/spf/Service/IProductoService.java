package cl.ubiobio.spf.Service;

import cl.ubiobio.spf.Entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IProductoService {

    // Guardar un producto
    Producto saveProducto(Producto producto) throws ParseException;

    // Obtener un producto por ID
    Producto getProducto(Long codigo);

    // Obtener todos los productos para una categoria determinada, paginados
    Page<Producto> getProductosPorCategoria(String categoria, Pageable pageable);

    //List<Producto> getProductosPorCategoria(String categoria);

    // Actualizar un producto
    Producto updateProducto (Producto producto, Long codigo);

    //Eliminar un producto
    void deleteProducto (Long codigo);

/*    Producto imagenReferencia(MultipartFile file, Long id) throws IOException;*/

    Page<Producto> getProductos(Pageable pageable);

    Producto imagenReferencia(MultipartFile file, Long codigo) throws IOException;
}