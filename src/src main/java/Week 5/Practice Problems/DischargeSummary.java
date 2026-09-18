class DischargeSummary {

    private final String patientId;
    private final String[] medicationCodes;

    private static String systemName;

    static {
        systemName = "MediTrack";
    }

    public DischargeSummary(String patientId, String[] medicationCodes) {

        if (patientId == null || medicationCodes == null) {
            throw new IllegalArgumentException("Invalid data");
        }

        for (String code : medicationCodes) {
            if (code == null || !code.matches("MED-[A-Z]")) {
                throw new IllegalArgumentException("Invalid medication code");
            }
        }

        this.patientId = patientId;
        this.medicationCodes = medicationCodes.clone();
    }

    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {

        if (index < 0 || index >= medicationCodes.length) {
            throw new IllegalArgumentException("Invalid index");
        }

        if (newCode == null || !newCode.matches("MED-[A-Z]")) {
            throw new IllegalArgumentException("Invalid medication code");
        }

        String[] corrected = medicationCodes.clone();
        corrected[index] = newCode;

        return new DischargeSummary(patientId, corrected);
    }

    public static String processNightlyBatch(DischargeSummary[] summaries) {

        int processed = 0;
        int nullSkipped = 0;
        int critical = 0;
        int routine = 0;

        if (summaries == null) {
            return "0 processed | 0 null skipped | 0 critical-care | 0 routine";
        }

        for (DischargeSummary summary : summaries) {

            if (summary == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (summary instanceof CriticalCareDischargeSummary) {
                critical++;
            } else {
                routine++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + critical + " critical-care | "
                + routine + " routine";
    }
}


class CriticalCareDischargeSummary extends DischargeSummary {

    private final int icuDays;

    public CriticalCareDischargeSummary(
            String patientId,
            String[] medicationCodes,
            int icuDays) {

        super(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    public int getIcuDays() {
        return icuDays;
    }
}