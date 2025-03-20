package constructora.constructorabackend.controller;

import constructora.constructorabackend.model.PersonaEncargadaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/PersonaEncargadas")
public class PersonaEncargadaController {
    @Autowired
    constructora.constructorabackend.service.PersonaEncargadaService PersonaEncargadaService;

    @PostMapping()
    public ResponseEntity<PersonaEncargadaModel> savePersonaEncargada(@RequestBody PersonaEncargadaModel PersonaEncargadaModel) {
        try{
            PersonaEncargadaModel savedPersonaEncargada = PersonaEncargadaService.savePersonaEncargada(PersonaEncargadaModel);
            return new ResponseEntity<>(savedPersonaEncargada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<PersonaEncargadaModel> updatePersonaEncargada(@RequestBody PersonaEncargadaModel PersonaEncargadaModel) {
        try{
            PersonaEncargadaModel savedPersonaEncargada = PersonaEncargadaService.updatePersonaEncargada(PersonaEncargadaModel);
            return new ResponseEntity<>(savedPersonaEncargada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<PersonaEncargadaModel>> getAllPersonaEncargadas() {
        return new ResponseEntity<>(PersonaEncargadaService.getPersonaEncargadas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaEncargadaModel> getPersonaEncargadaById(@PathVariable Integer id){
        Optional<PersonaEncargadaModel> PersonaEncargadaModel = PersonaEncargadaService.getPersonaEncargadaById(id);
        return PersonaEncargadaModel.map(model -> new ResponseEntity<>(model, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonaEncargada(@PathVariable Integer id){
        Optional<PersonaEncargadaModel> PersonaEncargadaModel = PersonaEncargadaService.getPersonaEncargadaById(id);
        if (PersonaEncargadaModel.isPresent()){
            PersonaEncargadaService.deletePersonaEncargada(PersonaEncargadaModel.get().getId_persona_encargada());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}