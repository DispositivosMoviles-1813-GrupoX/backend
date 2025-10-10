package pe.edu.upc.center.agecare.payment.interfaces.rest.transform;

import pe.edu.upc.center.agecare.payment.domain.model.aggregates.Receipt;
import pe.edu.upc.center.agecare.payment.interfaces.rest.resources.ReceiptResource;

public class ReceiptResourceFromEntityAssembler {
    public static ReceiptResource toResourceFromEntity(Receipt receipt) {
        return new ReceiptResource(
                receipt.getId(),
                receipt.getIssueDate(),
                receipt.getDueDate(),
                receipt.getTotalAmount(),
                receipt.getStatus(),
                receipt.getResidentId().getValue(),
                receipt.getPaymentId(),
                receipt.getPaymentDate(),
                receipt.getAmountPaid(),
                receipt.getPaymentMethod(),
                receipt.getType()
        );
    }
}