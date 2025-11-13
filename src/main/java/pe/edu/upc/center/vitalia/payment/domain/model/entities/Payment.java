package pe.edu.upc.center.vitalia.payment.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.edu.upc.center.vitalia.payment.domain.model.valueobjects.PaymentMethod;
import pe.edu.upc.center.vitalia.payment.domain.model.aggregates.Receipt;
import pe.edu.upc.center.vitalia.shared.domain.entities.AuditableModel;


import java.util.Date;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor
public class Payment extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @NotBlank
    @Column(nullable = false)
    private Float amountPaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receiptId;

    @NotBlank
    @Column(nullable = false)
    private PaymentMethod paymentMethod;


    public Payment(Date paymentDate, Float amountPaid, Receipt receiptId, PaymentMethod paymentMethod) {
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.receiptId = receiptId;
        this.paymentMethod = paymentMethod;
    }

    public void update(PaymentMethod paymentMethod, Float amountPaid) {
        this.paymentMethod = paymentMethod;
        this.amountPaid = amountPaid;
    }
}
