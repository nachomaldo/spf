package cl.ubiobio.spf.Service.jpa;

import cl.ubiobio.spf.Entity.Cliente;
import cl.ubiobio.spf.Entity.Deuda;
import cl.ubiobio.spf.Entity.Producto;
import cl.ubiobio.spf.Exception.ResourceDeletionNotPossibleException;
import cl.ubiobio.spf.Repository.IProductoRepository;
import cl.ubiobio.spf.Service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository productoRepository;

    // Guardar un nuevo producto
    @Override
    @Transactional
    public Producto saveProducto(Producto producto) {
        if(producto != null) {
            return productoRepository.save(producto);
        } else
            return null;
    }

    // Obtener un produccto especifico
    @Override
    @Transactional
    public Producto getProducto(Long codigo) { return productoRepository.findById(codigo).orElse(null); }

    // Obtener productos por una categoria determinada
    @Override
    @Transactional(readOnly = true)
    public Page<Producto> getProductosPorCategoria(String estado, String categoria, Pageable pageable) {
        return productoRepository.findByEstadoAndCategoriaOrderByPrecioAsc(estado, categoria, pageable);
    }

    // Obtener uno o más productos por nombre y estado, sin paginar
    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductosPorEstadoPorNombreSinPaginar(String estado, String name) {
        if (name != null) return productoRepository.findAllByEstadoAndNombreContaining(estado, name);
        else
            return null;
    }

    @Override
    public Producto logicDelete(Long idProducto) {
        Producto productToDelete = getProducto(idProducto);

        if (productToDelete.getEstado().equalsIgnoreCase("Inactivo")) {
            return null;
        }

        if (productToDelete != null) {

            // Se setea como 'Inactivo' el producto
            productToDelete.setEstado("Inactivo");
            // Se guardan los cambios y se retorna el paciente eliminado
            return productoRepository.save(productToDelete);
        }
        else
            return null;
    }

    // Reintegrar un producto (estado de "Inactivo" a "Activo")
    @Override
    public Producto reintegrarProducto(Long codigo) {
        Producto productoToIntegrate = getProducto(codigo);

        if (productoToIntegrate != null) {
            // Se reintegra el producto
            productoToIntegrate.setEstado("Activo");
            // Se guardan los cambios
            return productoRepository.save(productoToIntegrate);
        }
        else
            return null;
    }

    /*@Override
    @Transactional(readOnly = true)
    public List<Producto> getProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoriaOrderByPrecioAsc(categoria);
    }*/

    // Actualizar un producto
    @Override
    @Transactional
    public Producto updateProducto (Producto producto, Long codigo) {
        Producto productoToUpdate = getProducto(codigo);

        try {
            // Actualiza el nombre
            if (productoToUpdate.getNombre() != producto.getNombre()) productoToUpdate.setNombre(
                    producto.getNombre()
            );

            // Actualiza la precio
            if (productoToUpdate.getPrecio() != producto.getPrecio()) productoToUpdate.setPrecio(
                    producto.getPrecio()
            );

            // Actualiza el stock
            if (productoToUpdate.getStock() != producto.getStock()) productoToUpdate.setStock(
                    producto.getStock()
            );

            // Actualiza la descripcion
            if (productoToUpdate.getDescripcion() != producto.getDescripcion()) productoToUpdate.setDescripcion(
                    producto.getDescripcion()
            );

            // Actualiza la categoria
            if (productoToUpdate.getCategoria() != producto.getCategoria()) productoToUpdate.setCategoria(
                    producto.getCategoria()
            );

            productoRepository.save(productoToUpdate);
            return productoToUpdate;
        }
        catch (NullPointerException e) { return null; }
    }

    // Eliminar un producto (lógica)
    @Override
    @Transactional
    public Producto deleteProducto(Long codigo) {
        Producto productoToDelete = getProducto(codigo);

        if (productoToDelete.getEstado().equalsIgnoreCase("Inactivo")) {
            return null;
        }
        if (codigo != null) {

            // Se setea como 'Inactivo' al cliente
            productoToDelete.setEstado("Inactivo");
            // Se guardan los cambios y se retorna el paciente eliminado
            return productoRepository.save(productoToDelete);
        }
        else
            return null;
    }

    // Eliminar un pedido (ELIMINACIÓN FÍSICA)
/*    @Override
    public Producto imagenReferencia(MultipartFile file, Long id) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Producto producto = productoRepository.findById(id).orElse(null);
        Producto productoModificado;
        if (fileName.contains("..")) {
            throw new FacilitoException("Nombre de archivo contiene carácteres inválidos");
        }
        String type = file.getContentType();
        if (type.equalsIgnoreCase("image/png") || type.equalsIgnoreCase("image/gif")
                || type.equalsIgnoreCase("image/jpeg")) {
            producto.setImageName(fileName);
            producto.setImageBytes(file.getBytes());
            productoModificado = productoRepository.save(producto);
            return productoModificado;
        }else{
            throw new FacilitoException("Error guardando archivo, tipo de archivo no soportado");
        }
    }*/

    @Override
    public Producto imagenReferencia(MultipartFile file, Long codigo) throws IOException {
        Producto producto = productoRepository.findById(codigo).orElse(null);

        if (!file.isEmpty()) {
            String nombreArchivo = file.getOriginalFilename();
            Path pathFile = Paths.get("imagenes").resolve(nombreArchivo).toAbsolutePath();

            Files.copy(file.getInputStream(), pathFile);

            String nombreImagenAnterior = producto.getImageName();

            if (nombreImagenAnterior != null && nombreImagenAnterior.length() > 0) {
                Path rutaImagenAnterior = Paths.get("imagenes").resolve(nombreImagenAnterior).toAbsolutePath();
                File archivoImagenAnterior = rutaImagenAnterior.toFile();
                if (archivoImagenAnterior.exists() && archivoImagenAnterior.canRead()) {
                    archivoImagenAnterior.delete();
                }
            }

            producto.setImageName(nombreArchivo);
            return productoRepository.save(producto);
        }

        return null;
    }

    @Override
    public Page<Producto> getProductos(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public Page<Producto> getProductosActivosPaginados(String estado, Pageable pageable) {
        return productoRepository.findAllByEstado(estado, pageable);
    }

    @Override
    public Page<Producto> getProductosInactivosPaginados(String estado, Pageable pageable) {
        return productoRepository.findAllByEstado(estado, pageable);
    }

}
