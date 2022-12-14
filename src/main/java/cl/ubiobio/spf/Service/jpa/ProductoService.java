package cl.ubiobio.spf.Service.jpa;

import cl.ubiobio.spf.Entity.Producto;
import cl.ubiobio.spf.Exception.FacilitoException;
import cl.ubiobio.spf.Repository.IProductoRepository;
import cl.ubiobio.spf.Service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    public Page<Producto> getProductosPorCategoria(String categoria, Pageable pageable) {
        return productoRepository.findByCategoriaOrderByPrecioAsc(categoria, pageable);
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

    // Eliminar un pedido (ELIMINACI??N F??SICA)
    @Override
    @Transactional
    public void deleteProducto(Long codigo) {

        Producto producto = productoRepository.findById(codigo).orElse(null);
        if (producto != null) {
            String nombreImagenAnterior = producto.getImageName();

            if (nombreImagenAnterior != null && nombreImagenAnterior.length() > 0) {
                Path rutaImagenAnterior = Paths.get("imagenes").resolve(nombreImagenAnterior).toAbsolutePath();
                File archivoImagenAnterior = rutaImagenAnterior.toFile();
                if (archivoImagenAnterior.exists() && archivoImagenAnterior.canRead()) {
                    archivoImagenAnterior.delete();
                }
            }
        }

        productoRepository.deleteById(codigo);
    }



/*    @Override
    public Producto imagenReferencia(MultipartFile file, Long id) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Producto producto = productoRepository.findById(id).orElse(null);
        Producto productoModificado;
        if (fileName.contains("..")) {
            throw new FacilitoException("Nombre de archivo contiene car??cteres inv??lidos");
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




}
