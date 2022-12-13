package cl.ubiobio.spf.Controller;

import cl.ubiobio.spf.Entity.Deuda;
import cl.ubiobio.spf.Service.IDeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/deudas")
public class DeudaController {

    @Autowired
    private IDeudaService deudaService;

    @Secured("ROLE_OPERADOR")
    @PostMapping("")
    public ResponseEntity<Deuda> CreateDeuda(@RequestBody Deuda deuda) throws ParseException {
        if (deuda != null) {
            Deuda deudaCreada = deudaService.saveDeuda(deuda);
            return new ResponseEntity<>(deudaCreada, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @GetMapping("/{idDeuda}")
    public ResponseEntity<Deuda> getDeuda (@PathVariable(value = "idDeuda") Long idDeuda){
        Deuda deudaEncontrada = deudaService.getDeuda(idDeuda);

        if (deudaEncontrada != null) {
            return new ResponseEntity<>(deudaEncontrada, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Secured("ROLE_OPERADOR")
    @PutMapping("/update/{idDeuda}")
    public ResponseEntity<Deuda> updateDeuda (@RequestBody Deuda deuda,
                                              @PathVariable(value = "idDeuda") Long idDeuda) {
        Deuda deudaActualizada = deudaService.updateDeuda(deuda, idDeuda);

        if (deudaActualizada == null ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(deudaActualizada, HttpStatus.CREATED);
    }

    @Secured({"ROLE_OPERADOR", "ROLE_REPARTIDOR"})
    @PutMapping("/vigente/{idDeuda}")
    public void vigente (@PathVariable(value = "idDeuda") Long idDeuda) { deudaService.vigenteDeuda(idDeuda);}
}
