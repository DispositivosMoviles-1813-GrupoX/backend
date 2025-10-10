package pe.edu.upc.center.agecare.payment.interfaces.rest.transform;

import pe.edu.upc.center.agecare.payment.domain.model.commands.UpdateReceiptCommand;
import pe.edu.upc.center.agecare.payment.domain.model.valueobjects.ResidentId;
import pe.edu.upc.center.agecare.payment.interfaces.rest.resources.ReceiptResource;

public class UpdateReceiptCommandFromResourceAssembler {
    public static UpdateReceiptCommand toCommandFromResource(Long receiptId, ReceiptResource resource) {
        ResidentId residentId = new ResidentId(resource.residentId());

        return new UpdateReceiptCommand(
                receiptId,
                resource.issueDate(),
                resource.dueDate(),
                resource.totalAmount(),
                resource.status(),
                residentId,
                resource.paymentId(),
                resource.paymentDate(),
                resource.amountPaid(),
                resource.paymentMethod(),
                resource.type()
        );
    }
}
