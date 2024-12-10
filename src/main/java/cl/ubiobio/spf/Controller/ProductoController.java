package cl.ubiobio.spf.Controller;

import cl.ubiobio.spf.Entity.Producto;
import cl.ubiobio.spf.Exception.InvalidIdException;
import cl.ubiobio.spf.Exception.InvalidParameterException;
import cl.ubiobio.spf.Exception.ResourceNotFoundException;
import cl.ubiobio.spf.Service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    // Crear un nuevo producto
    @Secured("ROLE_OPERADOR")
    @PostMapping("")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) throws ParseException {
        if (producto != null) {
            Producto productoCreado = productoService.saveProducto(producto);
            return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Obtener un producto específico
    @Secured("ROLE_OPERADOR")
    @GetMapping("/{codigo}")
    public ResponseEntity<Producto> getProducto (@PathVariable(value = "codigo") Long codigo){
        if (codigo == null || codigo == 0) throw new InvalidIdException("El ID del producto ingresado no es valido");

        Producto productoEncontrado = productoService.getProducto(codigo);

        if (productoEncontrado != null) {
            return new ResponseEntity<>(productoEncontrado, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Obtener todos los productos
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/get/{pageNumber}")
    public ResponseEntity<Page<Producto>> getProductos (@PathVariable(value = "pageNumber") Integer nroPagina){
        Page pageOfProductos =  productoService.getProductos(PageRequest.of(nroPagina, 5));

        if(!pageOfProductos.isEmpty()) return new ResponseEntity<>(pageOfProductos, HttpStatus.OK);
        else
            throw new InvalidParameterException("El número de página proporcionado no es válido.");
    }

    // Obtener todos los productos activos, paginados
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/get/page/{pageNumber}")
    public ResponseEntity<Page<Producto>> getProductosActivosPaginados (@PathVariable(value = "pageNumber") Integer nroPagina) {
        Page pageOfProducts = productoService.getProductosActivosPaginados("Activo", PageRequest.of(nroPagina, 5));

        if (!pageOfProducts.isEmpty()) return new ResponseEntity<>(pageOfProducts, HttpStatus.OK);
        else
            throw new InvalidParameterException("El número de página proporcionado no es válido.");
    }

    // Obtener todos los productos inactivos, paginados
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/get/inactivo/{pageNumber}")
    public ResponseEntity<Page<Producto>> getProductosInactivosPaginados (@PathVariable(value = "pageNumber") Integer nroPagina) {
        Page pageOfProducts = productoService.getProductosInactivosPaginados("Inactivo", PageRequest.of(nroPagina, 5));

        if (!pageOfProducts.isEmpty()) return new ResponseEntity<>(pageOfProducts, HttpStatus.OK);
        else
            throw new InvalidParameterException("El número de página proporcionado no es válido.");
    }

    // Obtener productos activos por categoria, paginados
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/get/{categoria}/{pageNumber}")
    public ResponseEntity<Page<Producto>> getProductoPorCategoria(@PathVariable(value = "categoria") String categoria,
                                                                  @PathVariable(value = "pageNumber") Integer nroPagina) {
        Page pageOfProductos =  productoService.getProductosPorCategoria("Activo", categoria, PageRequest.of(nroPagina,8));

        if(!pageOfProductos.isEmpty()) return new ResponseEntity<>(pageOfProductos, HttpStatus.OK);
        else
            throw new InvalidParameterException("El número de página proporcionado no es válido.");
    }

    // Obtener uno o más productos activos, por nombre, sin paginar
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/get/by-name")
    public ResponseEntity<List<Producto>> getProductosPorNombreSinPaginar (@RequestParam String nombre) {
        if (nombre == null) throw new InvalidParameterException("Se debe ingresar el nombre del producto.");

        List productosNamed = productoService.getProductosPorEstadoPorNombreSinPaginar("Activo", nombre);

        if (!productosNamed.isEmpty()) return new ResponseEntity<>(productosNamed, HttpStatus.OK);
        else
            throw new ResourceNotFoundException("No se ha encontrado un producto con el nombre ingresado.");
    }

    // Obtener uno o más productos inactivos, por nombre, sin paginar
    @Secured({"ROLE_OPERADOR"})
    @GetMapping("/get/inactive/by-name")
    public ResponseEntity<List<Producto>> getProductosInactivosPorNombreSinPaginar (@RequestParam String nombre) {
        if (nombre == null) throw new InvalidParameterException("Se debe ingresar el nombre del producto.");

        List productosNamed = productoService.getProductosPorEstadoPorNombreSinPaginar("Inactivo", nombre);

        if (!productosNamed.isEmpty()) return new ResponseEntity<>(productosNamed, HttpStatus.OK);
        else
            throw new ResourceNotFoundException("No se ha encontrado un producto con el nombre ingresado.");
    }

    /*@GetMapping("/get/{categoria}")
    public ResponseEntity<List<Producto>> getProductoPorCategoria(@PathVariable(value = "categoria") String categoria) {
        List pageOfProductos =  productoService.getProductosPorCategoria(categoria);

        if(!pageOfProductos.isEmpty()) return new ResponseEntity<>(pageOfProductos, HttpStatus.OK);
        else
            throw new InvalidParameterException("El número de página proporcionado no es válido.");
    }*/

    // Actualizar un producto
    @Secured("ROLE_OPERADOR")
    @PutMapping("/update/{codigo}")
    public ResponseEntity<Producto> updateProducto (@RequestBody Producto producto,
                                                    @PathVariable(value = "codigo") Long codigo) {
        Producto productoActualizado = productoService.updateProducto(producto, codigo);

        if (productoActualizado == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(productoActualizado, HttpStatus.CREATED);
    }
/*
    // Eliminación física de un producto
    @Secured("ROLE_OPERADOR")
    @DeleteMapping("/delete/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProducto (@PathVariable(value = "codigo") Long codigo) {
        productoService.deleteProducto(codigo);
    }
    */

    //Servicio web que recibe una imagen y un codigo, y la cual guarda como imagen de referencia para el producto que corresponde el codigo
    @Secured("ROLE_OPERADOR")
    @RequestMapping(value = "/imagen/{codigo}", method = RequestMethod.POST)
    public ResponseEntity<Producto> uploadImagenReferencia (@RequestParam("file") MultipartFile file, @PathVariable Long codigo){
        ResponseEntity<Producto> response;
        try {
            Producto productoImagen = productoService.imagenReferencia(file,codigo);
            response = new ResponseEntity<Producto>(productoImagen, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            response = new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    //@Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/imagenes/image/{nombreImagen:.+}")
    public ResponseEntity<Resource> verImagen(@PathVariable String nombreImagen) throws MalformedURLException {

        Path rutaArchivo = Paths.get("imagenes").resolve(nombreImagen).toAbsolutePath();

        Resource recurso = null;

        recurso = new UrlResource(rutaArchivo.toUri());

        if (!recurso.exists() && !recurso.isReadable()) {
            throw new RuntimeException("Error no se pudo cargar la imagen: " + nombreImagen);
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }

    // Eliminación lógica de un producto
    @Secured("ROLE_OPERADOR")
    @PutMapping("/delete/{codigo}")
    public ResponseEntity<?> logicDeleteProducto(@PathVariable(value = "codigo") Long codigo) {

        try {
            if (codigo == null || codigo == 0) {
                throw new InvalidIdException("Se debe ingresar un identificador válido.");
            }

            Producto productoToDelete = productoService.logicDelete(codigo);

            if (productoToDelete != null) return new ResponseEntity<>(productoToDelete, HttpStatus.OK);
            else
                throw new ResourceNotFoundException("El identificador del producto ingresado no se encuentra registrado en el sistema.");
        }
        catch (InvalidIdException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Reintegrar un producto (de INACTIVO a ACTIVO)
    @Secured("ROLE_OPERADOR")
    @PutMapping("integrate/{codigo}")
    public ResponseEntity<Producto> reintegrarProducto(@PathVariable(value = "codigo") Long codigo) {
        if (codigo == null || codigo == 0) throw new InvalidIdException("Se debe ingresar un identificador válido.");

        Producto productoToIntegrate = productoService.reintegrarProducto(codigo);

        if (productoToIntegrate == null) throw new ResourceNotFoundException("El identificador del producto ingresado no se encuentra registrado en el sistema.");

        return new ResponseEntity<>(productoToIntegrate, HttpStatus.OK);
    }

    /*    //Servicio web que recibe una imagen y un codigo, y la cual guarda como imagen de referencia para el producto que corresponde el codigo
    @RequestMapping(value = "/imagen/{codigo}", method = RequestMethod.POST)
    public ResponseEntity<Producto> uploadImagenReferencia (@RequestParam("file") MultipartFile file, @PathVariable Long codigo){
        ResponseEntity<Producto> response;
        try {
            Producto productoImagen = productoService.imagenReferencia(file,codigo);
            response = new ResponseEntity<Producto>(productoImagen, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            response = new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }*/
}