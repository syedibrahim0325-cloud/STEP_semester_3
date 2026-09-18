class LibraryMember {

    private String membershipId;
    String branchCode;
    protected double finesOwed;
    public String displayName;

    public LibraryMember(String membershipId, String branchCode,
                         double finesOwed, String displayName) {

        String id = membershipId.trim();

        if (id.isEmpty() || id.length() < 4) {
            throw new IllegalArgumentException("Invalid membership ID");
        }

        this.membershipId = id;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }
}


class AccessChecker {

    static String classifyAccess(String fieldModifier,
                                 String accessorContext) {

        if (fieldModifier.equals("private")) {

            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        if (fieldModifier.equals("default")) {

            if (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }

            return "DENIED";
        }

        if (fieldModifier.equals("protected")) {

            if (accessorContext.equals("DIFFERENT_PACKAGE")) {
                return "DENIED";
            }

            return "ALLOWED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    static String summarizeByModifier(String[][] attempts) {

        String[] modifiers = {
            "private",
            "default",
            "protected",
            "public"
        };

        String result = "";

        for (int i = 0; i < modifiers.length; i++) {

            int allowed = 0;
            int denied = 0;

            for (int j = 0; j < attempts.length; j++) {

                if (attempts[j][0].equals(modifiers[i])) {

                    String answer = classifyAccess(
                        attempts[j][0],
                        attempts[j][1]
                    );

                    if (answer.equals("ALLOWED")) {
                        allowed++;
                    } else {
                        denied++;
                    }
                }
            }

            if (i > 0) {
                result += " | ";
            }

            result += modifiers[i] + ": "
                    + allowed + " allowed / "
                    + denied + " denied";
        }

        return result;
    }
}