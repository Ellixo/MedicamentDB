package com.ellixo.healthcare.api;

import com.ellixo.healthcare.domain.Medicament;
import com.ellixo.healthcare.exception.UnknownObjectException;
import com.ellixo.healthcare.repository.MedicamentRepository;
import com.ellixo.healthcare.services.MedicamentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/medicaments")
@Api(value = "medicaments", description = "Medicaments", produces = "application/json", consumes = "application/json")
public class MedicamentController {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private MedicamentService service;
    @Autowired
    private MedicamentRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "lecture medicament", httpMethod = "GET", response = Medicament.class)
    public Medicament get(@PathVariable(value = "id") String id) {
        Medicament medicament = repository.findOne(id);
        if (medicament == null) {
            throw new UnknownObjectException("medicament", id);
        }
        return medicament;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "requête medicaments", httpMethod = "GET", response = ResponseEntity.class)
    public ResponseEntity<List<Medicament>> list(@ApiParam(value = "requête", required = false) @RequestParam(required = false) String query, Pageable pageable) {
        Page<Medicament> medicaments = repository.searchAll(query, pageable);

        HttpHeaders headers = new HttpHeaders();
        StringBuilder linkBuilder = new StringBuilder();

        linkBuilder.append(linkTo(methodOn(getClass()).list(query, new PageRequest(medicaments.getTotalPages(), pageable.getPageSize()))).withRel("last").toString());
        if (medicaments.hasNext()) {
            linkBuilder.append(", " + linkTo(methodOn(getClass()).list(query, new PageRequest(pageable.getPageNumber() + 1, pageable.getPageSize()))).withRel("next").toString());
        }
        headers.add("Link", linkBuilder.toString());

        return new ResponseEntity<>(medicaments.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "info base de données", httpMethod = "GET", response = ResponseEntity.class)
    public String getInfo() {
        return "{\"dateMiseAJour\":\"" + DATE_FORMATTER.format(service.getUpdateDate()) + "\"}";
    }

}