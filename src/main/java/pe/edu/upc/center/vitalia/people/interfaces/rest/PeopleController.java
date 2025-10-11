package pe.edu.upc.center.vitalia.people.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.vitalia.people.domain.model.aggregates.People;
import pe.edu.upc.center.vitalia.people.domain.services.PeopleCommandService;
import pe.edu.upc.center.vitalia.people.domain.services.PeopleQueryService;
import pe.edu.upc.center.vitalia.people.interfaces.rest.resources.CreatePeopleResource;
import pe.edu.upc.center.vitalia.people.interfaces.rest.resources.PeopleResource;
import pe.edu.upc.center.vitalia.people.interfaces.rest.transform.PeopleResourceAssembler;

import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@Tag(name = "People", description = "Operations related to Users")
public class PeopleController {

    private final PeopleQueryService userQueryService;
    private final PeopleCommandService userCommandService;

    public PeopleController(PeopleQueryService userQueryService, PeopleCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @Operation(
            summary = "Retrieve all users",
            description = "Get a list of all users in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PeopleResource.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<PeopleResource>> getAllUsers() {
        var users = userQueryService.getAllPeople()
                .stream()
                .map(PeopleResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Retrieve user by ID",
            description = "Get details of a specific user by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PeopleResource.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PeopleResource> getUserById(@PathVariable Long id) {
        var user = userQueryService.getPeopleById(id);
        return user.map(PeopleResourceAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new user",
            description = "Register a new user in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PeopleResource.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping
    public ResponseEntity<PeopleResource> createUser(@RequestBody CreatePeopleResource createPeopleResource) {
        People user = PeopleResourceAssembler.toEntity(createPeopleResource);
        People savedUser = userCommandService.createPeople(user);
        PeopleResource peopleResource = PeopleResourceAssembler.toResource(savedUser);
        return ResponseEntity.status(201).body(peopleResource);
    }

    @Operation(
            summary = "Update an existing user",
            description = "Update the information of an existing user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PeopleResource.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<PeopleResource> updateUser(@PathVariable Long id, @RequestBody CreatePeopleResource resource) {
        People updatedUser = userCommandService.updatePeople(id, PeopleResourceAssembler.toEntity(resource));
        PeopleResource peopleResource = PeopleResourceAssembler.toResource(updatedUser);
        return ResponseEntity.ok(peopleResource);
    }

    @Operation(
            summary = "Delete a user",
            description = "Remove a user from the system by their ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userCommandService.deletePeople(id);
        return ResponseEntity.noContent().build();
    }
}
