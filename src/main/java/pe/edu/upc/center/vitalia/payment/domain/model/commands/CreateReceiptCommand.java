package pe.edu.upc.center.vitalia.payment.domain.model.commands;

import pe.edu.upc.center.vitalia.payment.domain.model.valueobjects.ResidentId;
import java.util.Date;

public record CreateReceiptCommand(
        //Long receiptId,
        Date issueDate,
        Date dueDate,
        Float totalAmount,
        Boolean status,
        ResidentId residentId,
        Long paymentId,
        Date paymentDate,
        Float amountPaid,
        Long paymentMethod,
        String type
) {}
