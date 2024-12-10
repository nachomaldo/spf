package cl.ubiobio.spf.Service;

import cl.ubiobio.spf.Entity.Cliente;
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
    Page<Producto> getProductosPorCategoria(String estado, String categoria, Pageable pageable);

    // Obtener lista de clientes activos por su nombre (sin paginar)
    //List<Producto> getProductosPorNombreSinPaginar(String name);

    //List<Producto> getProductosPorCategoria(String categoria);

    // Actualizar un producto
    Producto updateProducto (Producto producto, Long codigo);

    //Eliminar un producto
    Producto deleteProducto (Long codigo);

/*    Producto imagenReferencia(MultipartFile file, Long id) throws IOException;*/

    Page<Producto> getProductos(Pageable pageable);

    // Obtener productos activos, paginados
    Page<Producto> getProductosActivosPaginados(String estado, Pageable pageable);

    // Obtener productos inactivos, paginados
    Page<Producto> getProductosInactivosPaginados(String estado, Pageable pageable);

    Producto imagenReferencia(MultipartFile file, Long codigo) throws IOException;

    List getProductosPorEstadoPorNombreSinPaginar(String estado,String nombre);

    // Eliminación lógica de un producto
    Producto logicDelete(Long idProducto);

    // Reintegrar un producto (estado de "Inactivo" a "Activo")
    Producto reintegrarProducto(Long codigo);
}