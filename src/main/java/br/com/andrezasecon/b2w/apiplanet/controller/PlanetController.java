package br.com.andrezasecon.b2w.apiplanet.controller;

import br.com.andrezasecon.b2w.apiplanet.client.ClientFeignSwapi;
import br.com.andrezasecon.b2w.apiplanet.client.PlanetSwapiPaginationResponse;
import br.com.andrezasecon.b2w.apiplanet.domain.Planet;
import br.com.andrezasecon.b2w.apiplanet.exceptions.InvalidIdException;
import br.com.andrezasecon.b2w.apiplanet.exceptions.InvalidNameException;
import br.com.andrezasecon.b2w.apiplanet.exceptions.NotFoundNameException;
import br.com.andrezasecon.b2w.apiplanet.exceptions.PlanetNotFoundException;
import br.com.andrezasecon.b2w.apiplanet.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Validated
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @Autowired
    private ClientFeignSwapi clientFeignSwapi;

    // Busca todos os planetas na base e o número de filmes na Swapi
    @GetMapping
    public List<Planet> findAll() {
        List<Planet> planetList = planetService.findAllPlanets();
        planetList.stream().forEach(p -> {
            PlanetSwapiPaginationResponse planetsApi = clientFeignSwapi.getPlanetsByName(p.getName());
            planetsApi.getResults().stream().forEach(pa -> {
                p.setFilmsAppearances(pa.getFilms().size() + p.getFilmsAppearances());
            });
        });
        return planetList;
    }

    // Busca planeta na base de dados pelo id e o número de filmes na Swapi
    @GetMapping(value = "/{id}")
    ResponseEntity<Planet> findPlanetById(@PathVariable String id) {
        if (id.isBlank()) {
            throw new InvalidIdException();
        }
        return planetService.findPlanetById(id).map(record -> {
            PlanetSwapiPaginationResponse planetsApi = clientFeignSwapi.getPlanetsByName(record.getName());
            planetsApi.getResults().stream().forEach(pa -> {
                record.setFilmsAppearances(pa.getFilms().size() + record.getFilmsAppearances());
            });
            return ResponseEntity.ok().body(record);
        }).orElseThrow(() -> new PlanetNotFoundException(id));
    }


    // Busca planeta por nome na base dados o número de filmes na Swapi
    @GetMapping(value = "/find/{name}")
    public List<Planet> findPlanetByName(@PathVariable String name, HttpServletResponse response) {
        if (name.isBlank()) {
            throw new InvalidNameException();
        }
        List<Planet> planetList = planetService.findByNameIgnoreCase(name);
        planetList.stream().forEach(p -> {
            PlanetSwapiPaginationResponse planetsApi = clientFeignSwapi.getPlanetsByName(p.getName());
            planetsApi.getResults().stream().forEach(pa -> {
                p.setFilmsAppearances(pa.getFilms().size() + p.getFilmsAppearances());
            });

        });
        if (!planetList.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            throw new NotFoundNameException(name);
        }
        return planetList;
    }

    // Insere um novo planeta na base de dados
    @PostMapping
    public void insertPlanet(@RequestBody @Valid Planet objPlanet, HttpServletResponse response) {
        planetService.insertPlanet(objPlanet);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    // Deleta um planeta da base de dados pelo id
    @DeleteMapping(value = "/{id}")
    public void deletePlanet(@PathVariable String id, HttpServletResponse response) {
        if (!id.isBlank()) {
            planetService.deletePlanet(id);
            response.setStatus((HttpServletResponse.SC_OK));
        } else {
            throw new InvalidIdException();
        }
    }
}
