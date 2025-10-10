package pe.edu.upc.center.agecare.payment.interfaces.rest.resources;

import java.util.Date;

public record CreateReceiptResource(
        //Long receiptID,
        Date issueDate,
        Date dueDate,
        Float totalAmount,
        Boolean status,
        Long residentId,
        Long paymentId,
        Date paymentDate,
        Float amountPaid,
        Long paymentMethod,
        String type
) {}
