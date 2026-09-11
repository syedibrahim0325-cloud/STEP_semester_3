
public class Library {

    static String normalizeCode(String raw) {

        // Remove leading and trailing spaces
        raw = raw.trim();

        // Get first 3 characters and make them uppercase
        String publisher = raw.substring(0, 3).toUpperCase();

        // Keep the remaining characters unchanged
        String rest = raw.substring(3);

        return publisher + rest;
    }

    static String validateAndFormat(String code) {

        // Check length
        if (code.length() != 13) {
            return "Invalid: wrong length";
        }

        // Check first 3 characters are letters
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: non-letter publisher code";
            }
        }

        // Check remaining 10 characters are digits
        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: non-digit body";
            }
        }

        // Extract year and catalog number
        String year = code.substring(3, 7);
        String catalog = code.substring(7, 13);

        // Build formatted output
        StringBuilder result = new StringBuilder();

        result.append("[")
              .append(code.substring(0, 3))
              .append("] YEAR: ")
              .append(year)
              .append(" | CATALOG: ")
              .append(catalog);

        return result.toString();
    }

    public static void main(String[] args) {

        String raw = " pen2026004251 ";

        String normalized = normalizeCode(raw);

        System.out.println(validateAndFormat(normalized));
    }
}
```
