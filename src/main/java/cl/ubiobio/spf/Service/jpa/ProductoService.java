package cl.ubiobio.spf.Service.jpa;

import cl.ubiobio.spf.Entity.Producto;
import cl.ubiobio.spf.Repository.IProductoRepository;
import cl.ubiobio.spf.Service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


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

    // Eliminar un pedido (ELIMINACIÓN FÍSICA)
    @Override
    @Transactional
    public void deleteProducto(Long codigo) {
        productoRepository.deleteById(codigo);
    }
}
