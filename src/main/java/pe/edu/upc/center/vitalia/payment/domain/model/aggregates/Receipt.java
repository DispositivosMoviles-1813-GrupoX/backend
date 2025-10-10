package pe.edu.upc.center.agecare.payment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.center.agecare.payment.domain.model.commands.CreateReceiptCommand;
import pe.edu.upc.center.agecare.payment.domain.model.valueobjects.ResidentId;
import pe.edu.upc.center.agecare.shared.domain.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "receipt")
//@NoArgsConstructor
public class Receipt extends AuditableAbstractAggregateRoot<Receipt> {
    //@NotNull
    //@Column(nullable = false)
    //private Long receiptId;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "issueDate", nullable = false)
    @Getter
    private Date issueDate;

    @Getter
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "dueDate", nullable = false)
    private Date dueDate;

    @Getter
    @NotNull
    @Column(nullable = false)
    private Float totalAmount;

    @Getter
    @NotNull
    @Column(nullable = false)
    private Boolean status;

    @Getter
    @Embedded
    @NotNull
    private ResidentId residentId;

    @Getter
    @Column(name = "paymentId", nullable = true)
    private Long paymentId;

    @Getter
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "paymentDate", nullable = false)
    private Date paymentDate;

    @Getter
    @NotNull
    @Column(nullable = false)
    private Float amountPaid;

    @Getter
    @NotNull
    @Column(nullable = false)
    private Long paymentMethod;

    @Getter
    @NotNull
    @Column(nullable = false)
    private String type;

    //Constructor Completo

    public Receipt(Date issueDate, Date dueDate, Float totalAmount, Boolean status, ResidentId residentId, Long paymentId,
                   Date paymentDate, Float amountPaid, Long paymentMethod, String type){
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.residentId = residentId;
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.type = type;
    }

    //Constructor desde CreatePaymentCommand


    public Receipt(){}
    public Receipt(CreateReceiptCommand command) {
        this(
                command.issueDate(),
                command.dueDate(),
                command.totalAmount(),
                command.status(),
                command.residentId(),       // ✅ Ya es ResidentId
                command.paymentId(),                 // ✅ ahora correcto
                command.paymentDate(),
                command.amountPaid(),
                command.paymentMethod(),
                command.type()
        );
    }


    //Metodo para actualizar info basica
    public void updateInformation(Date issueDate, Date dueDate, Float totalAmount, Boolean status, ResidentId residentId,
                                  Long paymentId,
                                  Date paymentDate, Float amountPaid, Long paymentMethod, String type){
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.residentId = residentId;
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.type = type;
    }

    //public Long getReceiptId() {
    //return receiptId;
    //}
/*
    public Date getDueDate() {
        return dueDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public Boolean getStatus() {
        return status;
    }

    public ResidentId getResidentId() {
        return residentId;
    }

    public Long getPaymentID(){
        return paymentID;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public Float getAmountPaid() {
        return amountPaid;
    }

    public Long getPaymentMethod(){
        return paymentMethod;
    }

    public String getType() {
        return type;
    }*/
}
