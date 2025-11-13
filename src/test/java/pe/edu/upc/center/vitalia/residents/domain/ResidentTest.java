package pe.edu.upc.center.vitalia.residents.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.vitalia.residents.domain.model.aggregates.Resident;
import pe.edu.upc.center.vitalia.residents.domain.model.entities.MedicalHistory;
import pe.edu.upc.center.vitalia.residents.domain.model.entities.Medication;
import pe.edu.upc.center.vitalia.residents.domain.model.entities.MentalHealthRecord;
import pe.edu.upc.center.vitalia.residents.domain.model.valueobjects.Address;
import pe.edu.upc.center.vitalia.residents.domain.model.valueobjects.FullName;
import pe.edu.upc.center.vitalia.residents.domain.model.valueobjects.ReceiptId;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ResidentTest {

    private Resident resident;

    @BeforeEach
    void setUp() {
        resident = new Resident(
                "12345678",
                new FullName("Carlos", "Perez"),
                new Address("Av. Siempre Viva", "Lima", "Lima", "15001", "123 Main St"),
                new Date(),
                "Male",
                new ReceiptId(1L),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Test
    void shouldAddMedicationSuccessfully() {
        Medication med = new Medication("Ibuprofen", "200mg");
        resident.addMedication(med);
        assertEquals(1, resident.getMedication().size());
        assertEquals("Ibuprofen", resident.getMedication().get(0).getName());
    }



    @Test
    void shouldAddMentalHealthRecordSuccessfully() {
        MentalHealthRecord record = new MentalHealthRecord(new Date(),"Happy", "Good mood");
        resident.addMentalHealthRecord(record);

        assertEquals(1, resident.getMentalHealthRecords().size());
        assertEquals("Happy", resident.getMentalHealthRecords().get(0).getDiagnosis());
    }

    @Test
    void shouldAddMedicalHistorySuccessfully() {
        MedicalHistory history = new MedicalHistory(new Date(),"Flu", "Rest and hydration");
        resident.addMedicalHistory(history);

        assertEquals(1, resident.getMedicalHistories().size());
        assertEquals("Flu", resident.getMedicalHistories().get(0).getDiagnosis());
    }
}
