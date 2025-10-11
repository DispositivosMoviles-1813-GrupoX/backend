package pe.edu.upc.center.vitalia.payment.application.Internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.vitalia.payment.domain.model.aggregates.Receipt;
import pe.edu.upc.center.vitalia.payment.domain.model.commands.CreateReceiptCommand;
import pe.edu.upc.center.vitalia.payment.domain.model.commands.DeleteReceiptCommand;
import pe.edu.upc.center.vitalia.payment.domain.model.commands.UpdateReceiptCommand;
import pe.edu.upc.center.vitalia.payment.domain.services.ReceiptCommandService;
import pe.edu.upc.center.vitalia.payment.infrastructure.persistence.jpa.repositories.ReceiptRepository;

import java.util.Optional;

@Service
public class ReceiptCommandServiceImpl implements ReceiptCommandService {
    private final ReceiptRepository receiptRepository;

    public ReceiptCommandServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public Long handle(CreateReceiptCommand command) {
        if (this.receiptRepository.existsByResidentId(command.residentId())) {
            throw new IllegalArgumentException("Receipt with ID Num." + command.residentId() + " already exists");
        }

        Receipt receipt = new Receipt(command);
        try {
            this.receiptRepository.save(receipt);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving receipt Num." + e.getMessage());
        }
        return receipt.getId();
    }

    @Override
    public Optional<Receipt> handle(UpdateReceiptCommand command) {
        var receiptId = command.receiptId();

        if (!this.receiptRepository.existsById(receiptId)) {
            throw new IllegalArgumentException("Receipt Num." + command.receiptId() + "already exists");
        }

        if (this.receiptRepository.existsByIssueDate(command.issueDate())) {
            throw new IllegalArgumentException("Receipt with Num." + command.receiptId() + " already exists");
        }

        var receiptToUpdate = this.receiptRepository.findById(receiptId).get();
        receiptToUpdate.updateInformation(
                command.issueDate(),
                command.dueDate(),
                command.totalAmount(),
                command.status(),
                command.residentId(),
                command.receiptId(),
                command.paymentDate(),
                command.amountPaid(),
                command.paymentMethod(),
                command.type()
        );

        try {
            var updatedReceipt = this.receiptRepository.save(receiptToUpdate);
            return Optional.of(updatedReceipt);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating receipt: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteReceiptCommand command){
        if (!this.receiptRepository.existsById(command.receiptId())){
            throw new IllegalArgumentException("Resident with id " + command.receiptId() + "does not exist");
        }

        try {
            this.receiptRepository.deleteById(command.receiptId());
        }catch (Exception e){
            throw new IllegalArgumentException("Error while deleting receipt: "+ e.getMessage());
        }
    }
}

