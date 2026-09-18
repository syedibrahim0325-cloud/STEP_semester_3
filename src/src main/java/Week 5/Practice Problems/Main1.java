
class AccessRuleEngine {

    public static String classifyAccess(
            String fieldModifier,
            String accessorContext) {

        boolean allowed = false;

        if (fieldModifier.equals("private")) {

            if (accessorContext.equals("SAME_CLASS")) {
                allowed = true;
            }

        } else if (fieldModifier.equals("default")) {

            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE")) {
                allowed = true;
            }

        } else if (fieldModifier.equals("protected")) {

            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE")) {
                allowed = true;
            }

        } else if (fieldModifier.equals("public")) {

            allowed = true;
        }

        return allowed ? "ALLOWED" : "DENIED";
    }


    public static String summarizeBatch(String[][] attempts) {

        int allowed = 0;
        int denied = 0;

        for (String[] attempt : attempts) {

            String result =
                classifyAccess(attempt[0], attempt[1]);

            if (result.equals("ALLOWED")) {
                allowed++;
            } else {
                denied++;
            }
        }

        return "Allowed: " + allowed +
               " | Denied: " + denied;
    }
}


class PatientRecord {

    // Choosing appropriate visibility for each field
    private String patientId;
    private String wardCode;
    private double vitalsScore;
    private String facilityName;


    // Parameterized constructor only
    public PatientRecord(
            String patientId,
            String wardCode,
            double vitalsScore,
            String facilityName) {

        String trimmedId =
            patientId == null ? "" : patientId.trim();

        // Blank, whitespace-only, or less than 4 characters
        if (trimmedId.isEmpty() || trimmedId.length() < 4) {
            throw new IllegalArgumentException(
                "Invalid patient ID"
            );
        }

        this.patientId = trimmedId;
        this.wardCode = wardCode;
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName;
    }
}


public class Main1 {

    public static void main(String[] args) {

        System.out.println(
            AccessRuleEngine.classifyAccess(
                "private",
                "SAME_CLASS"
            )
        );

        System.out.println(
            AccessRuleEngine.classifyAccess(
                "default",
                "DIFFERENT_PACKAGE"
            )
        );


        String[][] attempts = {
            {"protected", "SAME_PACKAGE"},
            {"protected", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
            AccessRuleEngine.summarizeBatch(attempts)
        );


        // This succeeds
        PatientRecord p1 =
            new PatientRecord(
                "MT94",
                "W3",
                98.2,
                "MediTrack Central"
            );

        // This would be rejected:
        // PatientRecord p2 =
        //     new PatientRecord(
        //         "MT9",
        //         "W3",
        //         98.2,
        //         "MediTrack Central"
        //     );
    }
}

