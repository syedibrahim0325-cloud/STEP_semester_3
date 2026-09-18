public class Main {

    static String classifyAccess(String fieldModifier, String accessorContext) {

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        return "DENIED";
    }

    static String describeContext(String accessorContext) {

        String[] words = accessorContext.split("_");
        String result = "";

        for (String word : words) {
            result += word.substring(0, 1).toUpperCase()
                    + word.substring(1).toLowerCase()
                    + " ";
        }

        return result.trim();
    }

    public static void main(String[] args) {

        System.out.println(
            classifyAccess(
                "protected",
                "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"
            )
        );

        System.out.println(
            classifyAccess(
                "protected",
                "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
            )
        );

        System.out.println(
            describeContext(
                "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
            )
        );
    }
}