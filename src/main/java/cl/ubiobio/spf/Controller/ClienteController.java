package cl.ubiobio.spf.Controller;

import cl.ubiobio.spf.Entity.Cliente;
import cl.ubiobio.spf.Exception.InvalidIdException;
import cl.ubiobio.spf.Exception.InvalidParameterException;
import cl.ubiobio.spf.Exception.ResourceNotFoundException;
import cl.ubiobio.spf.Service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    // Crear un nuevo cliente
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @PostMapping("")
    public ResponseEntity<Cliente> saveCliente (@Valid @RequestBody Cliente cliente) {
        if (cliente != null) {
            Cliente clienteToCreate = clienteService.saveCliente(cliente);
            return new ResponseEntity<>(clienteToCreate, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Obtener un cliente por su ID
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> getCliente (@PathVariable(value = "idCliente") Long idCliente) {
        if (idCliente == null || idCliente == 0) throw new InvalidIdException("El ID del cliente ingresado no es válido.");

        Cliente clienteToReturn  = clienteService.getClienteById(idCliente);

        if (clienteToReturn != null) {
            return new ResponseEntity<>(clienteToReturn, HttpStatus.OK);
        }
        else
            throw new ResourceNotFoundException("No se ha encontrado un cliente con el ID ingresado.");
    }

    // Obtener todos los clientes activos, paginados
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/get/page/{pageNumber}")
    public ResponseEntity<Page<Cliente>> getClientes (@PathVariable(value = "pageNumber") Integer nroPagina) {
        Page pageOfClientes = clienteService.getClientesActivos(PageRequest.of(nroPagina, 5));

        if (!pageOfClientes.isEmpty()) return new ResponseEntity<>(pageOfClientes, HttpStatus.OK);
        else
            throw new InvalidParameterException("El número de página proporcionado no es válido.");
    }

    // Obtener todos los clientes inactivos, paginados
    //@Secured("ROLE_OPERADOR")
    @GetMapping("/get/inactive/page/{pageNumber}")
    public ResponseEntity<Page<Cliente>> getClientesInactivos (@PathVariable(value = "pageNumber") Integer nroPagina) {
        Page inactiveClientes = clienteService.getClientesInactivos(PageRequest.of(nroPagina, 5));

        if (!inactiveClientes.isEmpty()) return new ResponseEntity<>(inactiveClientes, HttpStatus.OK);
        else
            throw new InvalidParameterException("El número de página proporcionado no es válido.");
    }

    // Obtener uno o más clientes activos, por nombre, sin paginar
    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/get/by-name")
    public ResponseEntity<List<Cliente>> getClientesPorNombreSinPaginar (@RequestParam String nombre) {
        if (nombre == null) throw new InvalidParameterException("Se debe ingresar el nombre del cliente.");

        List clientesNamed = clienteService.getClientesPorEstadoPorNombreSinPaginar("Activo", nombre);

        if (!clientesNamed.isEmpty()) return new ResponseEntity<>(clientesNamed, HttpStatus.OK);
        else
            throw new ResourceNotFoundException("No se ha encontrado un cliente con el nombre ingresado.");
    }

    // Obtener uno o más clientes inactivos, por nombre, sin paginar
   //@Secured("ROLE_OPERADOR")
    @GetMapping("/get/inactive/by-name")
    public ResponseEntity<List<Cliente>> getClientesInactivosPorNombre(@RequestParam String nombre) {

        if (nombre == null) throw new InvalidParameterException("Se debe ingresar el nombre del cliente.");

        List inactiveClientesNamed = clienteService.getClientesPorEstadoPorNombreSinPaginar("Inactivo", nombre);

        if (!inactiveClientesNamed.isEmpty()) return new ResponseEntity<>(inactiveClientesNamed, HttpStatus.OK);
        else
            throw new ResourceNotFoundException("No se ha encontrado un cliente con el nombre ingresado.");
    }

    // Obtener uno o más clientes inactivos, por apellido, sin paginar
    //@Secured("ROLE_OPERADOR", "ROLE_REPARTIDOR")
    @GetMapping("/get/inactive/by-lastname")
    public ResponseEntity<List<Cliente>> getClientesInactivosPorApellido(@RequestParam String apellido) {
        if (apellido == null) throw new InvalidParameterException("Se debe ingresar un apellido.");

        List inactiveClientes = clienteService.getClientesPorEstadoPorApellidoSinPaginar("Inactivo", apellido);

        if(!inactiveClientes.isEmpty()) return new ResponseEntity<>(inactiveClientes, HttpStatus.OK);
        else
            throw new ResourceNotFoundException("No se han encontrado clientes con el apellido ingresado.");
    }

    // Obtener uno o más clientes inactivos, por nombre y apellido, sin paginar
    //@Secured("ROLE_OPERADOR")
    @GetMapping("/get/inactive/by-name-lastname")
    public ResponseEntity<List<Cliente>> getClientesInactivosPorNombreApellido(@RequestParam String nombre,
                                                                                 @RequestParam String apellido) {
        if (nombre == null || apellido == null) throw new InvalidParameterException("Se debe ingresar el nombre y el apellido.");

        List inactiveClientes = clienteService.getClientesPorEstadoPorNombreApellidoSinPaginar("Inactivo", nombre, apellido);

        if (!inactiveClientes.isEmpty()) return new ResponseEntity<>(inactiveClientes, HttpStatus.OK);
        else
            throw new ResourceNotFoundException("No se han encontrado clientes con el nombre y apellido ingresado.");
    }

    // Obtener uno o más clientes activos, por apellido, sin paginar
    //@Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/get/by-lastname")
    public ResponseEntity<List<Cliente>> getClientesPorApellidoSinPaginar (@RequestParam String apellido) {
        if (apellido != null) {
            List clientesWhoseLastnameIs = clienteService.getClientesPorEstadoPorApellidoSinPaginar("Activo", apellido);

            if (!clientesWhoseLastnameIs.isEmpty()) return new ResponseEntity<>(clientesWhoseLastnameIs, HttpStatus.OK);
            else
                throw new ResourceNotFoundException("No se han encontrado clientes con el apellido ingresado.");
        }
        else
            throw new InvalidParameterException("Se debe ingresar un apellido.");
    }

    // Obtener uno o más clientes activos, por nombre y apellido, sin paginar
    //@Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/get/by-name-lastname")
    public ResponseEntity<List<Cliente>> getPacientesPorNombreApellidoSinPaginar(@RequestParam String nombre, @RequestParam String apellido) {
        if (nombre != null && apellido != null) {
            List<Cliente> clientesByNameAndLastname = clienteService.getClientesPorEstadoPorNombreApellidoSinPaginar("Activo", nombre, apellido);

            if (!clientesByNameAndLastname.isEmpty()) return new ResponseEntity<>(clientesByNameAndLastname, HttpStatus.OK);
            else
                throw new ResourceNotFoundException("No se han encontrado clientes con el nombre y apellido ingresado.");
        }
        else
            throw new InvalidParameterException("Se debe ingresar el nombre y el apellido.");
    }

    // Actualizar los datos de un cliente
    @Secured("ROLE_OPERADOR")
    @PutMapping("/update/{idCliente}")
    public ResponseEntity<Cliente> updateCliente (@Valid @RequestBody Cliente cliente, @PathVariable (value = "idCliente") Long idCliente) {
        if (idCliente == null || idCliente == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Cliente updatedCliente = clienteService.updateCliente(cliente, idCliente);

        if (updatedCliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(updatedCliente, HttpStatus.CREATED);
    }

    // Eliminación lógica de un cliente
    //@Secured("ROLE_OPERADOR",)
    @PutMapping("delete/{idCliente}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable(value = "idCliente") Long idCliente) {
        if (idCliente == null || idCliente == 0) {
            throw new InvalidIdException("Se debe ingresar un identificador válido.");
        }

        Cliente clienteToDelete = clienteService.deleteCliente(idCliente);

        if (clienteToDelete != null) return new ResponseEntity<>(clienteToDelete, HttpStatus.OK);
        else
            throw new ResourceNotFoundException("El identificador del cliente ingresado no se encuentra registrado en el sistema.");
    }

    // Reintegrar un cliente(de INACTIVO a ACTIVO)
    //@Secured("ROLE_OPERADOR")
    @PutMapping("integrate/{idCliente}")
    public ResponseEntity<Cliente> reintegrarPaciente(@PathVariable(value = "idCliente") Long idCliente) {
        if (idCliente == null || idCliente == 0) throw new InvalidIdException("Se debe ingresar un identificador válido.");

        Cliente clienteToIntegrate = clienteService.reintegrarCliente(idCliente);

        if (clienteToIntegrate == null) throw new ResourceNotFoundException("El identificador del cliente ingresado no se encuentra registrado en el sistema.");

        return new ResponseEntity<>(clienteToIntegrate, HttpStatus.OK);
    }
}