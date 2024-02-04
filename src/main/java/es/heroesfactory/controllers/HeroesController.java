package es.heroesfactory.controllers;

import es.heroesfactory.anotations.CalculateDuration;
import es.heroesfactory.dtos.AllHeroesResponse;
import es.heroesfactory.dtos.ErrorDTO;
import es.heroesfactory.dtos.HeroeDTO;
import es.heroesfactory.dtos.HeroesPageableResponse;
import es.heroesfactory.services.HeroesService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/heroes")
@OpenAPIDefinition(info = @Info(title = "Heroes Complete API", version = "v1"))
@Tag(name = "Heroes", description = "Heroes Complete API")
@AllArgsConstructor
public class HeroesController {

    @Resource
    private HeroesService heroesService;

    @Operation(summary = "All heroes", description = "list all heroes", tags = { "Heroes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AllHeroesResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized with invalid user API-KEY",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @CalculateDuration
    @GetMapping("/all")
    public ResponseEntity<AllHeroesResponse> getAllHeroes(
            @Parameter(name = "API-KEY", description = "user secret api key", example = "8e366910", required = false)
            @RequestParam(name = "API-KEY", required = false) String apiKey
    ) {
        AllHeroesResponse response = this.heroesService.getAllHeroes();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "All heroes page", description = "list all heroes with pagination", tags = { "Heroes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HeroesPageableResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized with invalid user API-KEY",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "No exist required page",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @CalculateDuration
    @GetMapping("")
    public ResponseEntity<HeroesPageableResponse> getAllHeroesPage(
            @Parameter(name = "API-KEY", description = "user secret api key", example = "8e366910", required = false)
            @RequestParam(name = "API-KEY", required = false) String apiKey,

            @Parameter(name = "page", description = "page number to display", example = "1", required = true)
            @RequestParam(name = "page") Integer page,

            @Parameter(name = "elements", description = "number of elements each page", example = "10", required = true)
            @RequestParam(name = "elements") Integer elements) {
        HeroesPageableResponse response = this.heroesService.getAllHeroesPage(page, elements);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Heroe detail", description = "Search a heroe detail by ID", tags = { "Heroes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HeroeDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized with invalid user API-KEY",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Heroe not found with Incorrect heroe ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @CalculateDuration
    @GetMapping("/{id}")
    public ResponseEntity<HeroeDTO> getHeroeById(
            @Parameter(name = "API-KEY", description = "user secret api key", example = "8e366910", required = false)
            @RequestParam(name = "API-KEY", required = false) String apiKey,

            @Parameter(name = "id", description = "heroe ID", example = "332", required = true)
            @PathVariable("id") Integer id) {
        HeroeDTO dto = this.heroesService.getHeroeById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Search heroes", description = "Search heroes by name", tags = { "Heroes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AllHeroesResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized with invalid user API-KEY",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @CalculateDuration
    @GetMapping("/search")
    public ResponseEntity<AllHeroesResponse> getHeroesByName(
            @Parameter(name = "API-KEY", description = "user secret api key", example = "8e366910", required = false)
            @RequestParam(name = "API-KEY", required = false) String apiKey,

            @Parameter(name = "name", description = "name containing the heroe name", example = "man", required = true)
            @RequestParam(name = "name") String name) {
        AllHeroesResponse list = this.heroesService.searchHeroesByName(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(summary = "New heroe", description = "Insert new heroe", tags = { "Heroes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized with invalid user API-KEY",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @CalculateDuration
    @PostMapping("")
    public ResponseEntity<Void> insertHeroe(
            @Parameter(name = "API-KEY", description = "user secret api key", example = "8e366910", required = false)
            @RequestParam(name = "API-KEY", required = false) String apiKey,

            @RequestBody HeroeDTO heroe) {
        this.heroesService.saveHeroe(heroe);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Update heroe", description = "Update heroe data", tags = { "Heroes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized with invalid user API-KEY",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Heroe not found with Incorrect heroe ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @CalculateDuration
    @PutMapping("")
    public ResponseEntity<Void> updateHeroe(
            @Parameter(name = "API-KEY", description = "user secret api key", example = "8e366910", required = false)
            @RequestParam(name = "API-KEY", required = false) String apiKey,

            @RequestBody HeroeDTO heroe) {
        this.heroesService.updateHeroe(heroe);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete heroe", description = "Delete heroe by ID", tags = { "Heroes" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized with invalid user API-KEY",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Heroe not found with Incorrect heroe ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @CalculateDuration
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHeroe(
            @Parameter(name = "API-KEY", description = "user secret api key", example = "8e366910", required = false)
            @RequestParam(name = "API-KEY", required = false) String apiKey,

            @Parameter(name = "id", description = "heroe ID", example = "9", required = true)
            @PathVariable("id") Integer id) {
        this.heroesService.deleteHeroe(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
