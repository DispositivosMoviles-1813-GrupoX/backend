package pe.edu.upc.center.vitalia.notification.domain.model.valueobjects;

public class FamilyMemberId {
    private final long familyMemberId;

    public FamilyMemberId(long familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public long getFamilyMemberId() {
        return familyMemberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyMemberId that = (FamilyMemberId) o;
        return familyMemberId == that.familyMemberId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(familyMemberId);
    }
}