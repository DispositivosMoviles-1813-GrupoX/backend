package pe.edu.upc.center.agecare.payment.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.agecare.payment.domain.model.commands.DeleteReceiptCommand;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetAllReceiptsQuery;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetReceiptsByResidentIdQuery;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetReceiptByDateQuery;
import pe.edu.upc.center.agecare.payment.domain.model.queries.GetReceiptByReceiptIdQuery;
import pe.edu.upc.center.agecare.payment.domain.model.valueobjects.ResidentId;
import pe.edu.upc.center.agecare.payment.domain.services.ReceiptQueryService;
import pe.edu.upc.center.agecare.payment.domain.services.ReceiptCommandService;
import pe.edu.upc.center.agecare.payment.interfaces.rest.resources.CreateReceiptResource;
import pe.edu.upc.center.agecare.payment.interfaces.rest.resources.ReceiptResource;
import pe.edu.upc.center.agecare.payment.interfaces.rest.transform.CreateReceiptCommandFromResourceAssembler;
import pe.edu.upc.center.agecare.payment.interfaces.rest.transform.ReceiptResourceFromEntityAssembler;
import pe.edu.upc.center.agecare.payment.interfaces.rest.transform.UpdateReceiptCommandFromResourceAssembler;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Receipts", description = "Receipt Management Endpoints")
public class ReceiptController {
    private final ReceiptQueryService receiptQueryService;
    private final ReceiptCommandService receiptCommandService;

    public ReceiptController(ReceiptQueryService receiptQueryService, ReceiptCommandService receiptCommandService) {
        this.receiptQueryService = receiptQueryService;
        this.receiptCommandService = receiptCommandService;
    }

    @PostMapping
    public ResponseEntity<ReceiptResource> createReceipt(@RequestBody CreateReceiptResource resource) {
        var createCommand = CreateReceiptCommandFromResourceAssembler.toCommandFromResource(resource);
        var residentId = receiptCommandService.handle(createCommand);

        if (residentId == null || residentId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getReceiptByResidentIdQuery = new GetReceiptsByResidentIdQuery(residentId);
        var receipts = receiptQueryService.handle(getReceiptByResidentIdQuery); // ahora es una lista

        if (receipts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Si quieres devolver solo la boleta más reciente, puedes hacer:
        var latestReceipt = receipts.get(receipts.size() - 1);
        var receiptResource = ReceiptResourceFromEntityAssembler.toResourceFromEntity(latestReceipt);

        return new ResponseEntity<>(receiptResource, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<ReceiptResource>> getAllReceipts() {
        var query = new GetAllReceiptsQuery();
        var receipts = receiptQueryService.handle(query);
        var resources = receipts.stream()
                .map(ReceiptResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{residentId}")
    public ResponseEntity<List<ReceiptResource>> getReceiptsByResidentId(@PathVariable Long residentId) {
        var query = new GetReceiptsByResidentIdQuery(residentId); // nombre plural
        var receipts = receiptQueryService.handle(query);         // devuelve List<Receipt>

        if (receipts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var resources = receipts.stream()
                .map(ReceiptResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{receiptId}")
    public ResponseEntity<ReceiptResource> updateReceipt(@PathVariable Long receiptId, @RequestBody ReceiptResource resource) {
        var updateCommand = UpdateReceiptCommandFromResourceAssembler.toCommandFromResource(receiptId, resource);
        var optionalReceipt = receiptCommandService.handle(updateCommand);

        if (optionalReceipt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var updatedResource = ReceiptResourceFromEntityAssembler.toResourceFromEntity(optionalReceipt.get());
        return ResponseEntity.ok(updatedResource);
    }

    @DeleteMapping("/{receiptId}")
    public ResponseEntity<?> deleteReceipt(@PathVariable Long receiptId) {
        var deleteCommand = new DeleteReceiptCommand(receiptId);
        receiptCommandService.handle(deleteCommand);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/searchByReceiptId")
    public ResponseEntity<ReceiptResource> getReceiptByReceiptId(@RequestParam Long receiptId) {
        var query = new GetReceiptByReceiptIdQuery(receiptId);
        var optionalReceipt = receiptQueryService.handle(query);

        if (optionalReceipt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = ReceiptResourceFromEntityAssembler.toResourceFromEntity(optionalReceipt.get());
        return ResponseEntity.ok(resource);
    }
}
