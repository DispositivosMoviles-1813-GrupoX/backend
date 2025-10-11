package pe.edu.upc.center.vitalia.users.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.vitalia.users.domain.model.aggregates.FamilyMember;
import pe.edu.upc.center.vitalia.users.domain.services.FamilyMemberCommandService;
import pe.edu.upc.center.vitalia.users.domain.services.FamilyMemberQueryService;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.CreateFamilyMemberResource;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.FamilyMemberResource;
import pe.edu.upc.center.vitalia.users.interfaces.rest.resources.UpdateFamilyMemberResource;
import pe.edu.upc.center.vitalia.users.interfaces.rest.transform.FamilyMemberResourceAssembler;

import java.util.List;

@RestController
@RequestMapping("/api/v1/family-members")
@Tag(name = "FamilyMembers", description = "Operations related to Family Members")
public class FamilyMemberController {

    private final FamilyMemberCommandService familyMemberCommandService;
    private final FamilyMemberQueryService familyMemberQueryService;

    public FamilyMemberController(FamilyMemberCommandService familyMemberCommandService,
                                  FamilyMemberQueryService familyMemberQueryService) {
        this.familyMemberCommandService = familyMemberCommandService;
        this.familyMemberQueryService = familyMemberQueryService;
    }

    @Operation(
            summary = "Retrieve all family members",
            description = "Get a list of all family members in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Family members retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FamilyMemberResource.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<FamilyMember>> getAllFamilyMembers() {
        return ResponseEntity.ok(familyMemberQueryService.getAllFamilyMembers());
    }

    @Operation(
            summary = "Retrieve family member by ID",
            description = "Get details of a specific family member by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Family member found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FamilyMemberResource.class))),
                    @ApiResponse(responseCode = "404", description = "Family member not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<FamilyMember> getFamilyMemberById(@PathVariable Long id) {
        return familyMemberQueryService.getFamilyMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new family member",
            description = "Register a new family member in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Family member created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FamilyMemberResource.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping
    public ResponseEntity<FamilyMemberResource> createFamilyMember(@RequestBody CreateFamilyMemberResource resource) {
        var familyMember = familyMemberCommandService.createFamilyMember(
                FamilyMemberResourceAssembler.toEntity(resource)
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FamilyMemberResourceAssembler.toResource(familyMember));
    }

    @Operation(
            summary = "Update an existing family member",
            description = "Update the information of an existing family member",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Family member updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FamilyMemberResource.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Family member not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<FamilyMemberResource> updateFamilyMember(
            @PathVariable Long id,
            @RequestBody UpdateFamilyMemberResource resource) {

        FamilyMember updatedFamilyMember = familyMemberCommandService.updateFamilyMember(
                id, FamilyMemberResourceAssembler.toEntity(resource));

        return ResponseEntity.ok(FamilyMemberResourceAssembler.toResource(updatedFamilyMember));
    }

    @Operation(
            summary = "Delete a family member",
            description = "Remove a family member from the system by their ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Family member deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Family member not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFamilyMember(@PathVariable Long id) {
        familyMemberCommandService.deleteFamilyMember(id);
        return ResponseEntity.noContent().build();
    }
}
