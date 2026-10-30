/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UniFlow;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class InputRules {
    // Student ID format: 2 digits - 4 digits - any number of digits (at least 1)
    private static final int G1 = 2;
    private static final int G2 = 4;
    private static final int MAX_LAST = 10;   // safety limit for the last group
    public static final String ID_HINT = "00-0000-00000";

    private static final int MAX_DIGITS = G1 + G2 + MAX_LAST;
    private static final String ID_PATTERN =
            "\\d{" + G1 + "}-\\d{" + G2 + "}-\\d{1," + MAX_LAST + "}";

    public static boolean isValidStudentId(String id) {
        return id != null && id.matches(ID_PATTERN);
    }

    // Turns any text into digits only, with dashes in the right places
    private static String formatId(String raw) {
        String digits = raw.replaceAll("\\D", "");
        if (digits.length() > MAX_DIGITS) {
            digits = digits.substring(0, MAX_DIGITS);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            if (i == G1 || i == G1 + G2) sb.append('-');
            sb.append(digits.charAt(i));
        }
        return sb.toString();
    }

    // Student ID box: numbers only, dashes added automatically
    public static void applyStudentId(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                Document doc = fb.getDocument();
                String current = doc.getText(0, doc.getLength());
                String next = current.substring(0, offset)
                            + (text == null ? "" : text)
                            + current.substring(offset + length);
                fb.replace(0, doc.getLength(), formatId(next), attrs);
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs)
                    throws BadLocationException {
                replace(fb, offset, 0, text, attrs);
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                replace(fb, offset, length, "", null);
            }
        });
    }

    // Any text box: everything typed becomes CAPITAL LETTERS
    public static void applyUpperCase(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                super.replace(fb, offset, length, text == null ? null : text.toUpperCase(), attrs);
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs)
                    throws BadLocationException {
                super.insertString(fb, offset, text == null ? null : text.toUpperCase(), attrs);
            }
        });
    }
        // ---------- Year & Section: XXX-XXX-LETTERS + 2 digits (e.g. G11-FC1-STEM01) ----------
    private static final int SEC_MAX_LETTERS = 6;   // max letters in the last group
    public static final String SECTION_HINT = "G11-FC1-STEM01";

    private static final String SECTION_PATTERN =
            "[A-Z0-9]{3}-[A-Z0-9]{3}-[A-Z]{1," + SEC_MAX_LETTERS + "}\\d{2}";
    private static final String SECTION_NO_NUMBER =
            "[A-Z0-9]{3}-[A-Z0-9]{3}-[A-Z]{1," + SEC_MAX_LETTERS + "}";
    private static final String SECTION_ONE_DIGIT =
            "[A-Z0-9]{3}-[A-Z0-9]{3}-[A-Z]{1," + SEC_MAX_LETTERS + "}\\d";

    public static boolean isValidSection(String s) {
        return s != null && s.matches(SECTION_PATTERN);
    }

    // STEM -> STEM00, STEM1 -> STEM01, STEM01 stays as is
    public static String withDefaultNumber(String s) {
        if (s == null) return "";
        s = s.trim().toUpperCase();
        if (s.matches(SECTION_NO_NUMBER)) {
            return s + "00";
        }
        if (s.matches(SECTION_ONE_DIGIT)) {
            return s.substring(0, s.length() - 1) + "0" + s.charAt(s.length() - 1);
        }
        return s;
    }

    private static String formatSection(String raw) {
        String clean = raw.toUpperCase().replaceAll("[^A-Z0-9]", "");
        int n = clean.length();
        StringBuilder sb = new StringBuilder();

        sb.append(clean, 0, Math.min(3, n));
        if (n > 3) {
            sb.append('-').append(clean, 3, Math.min(6, n));
        }
        if (n > 6) {
            StringBuilder letters = new StringBuilder();
            StringBuilder nums = new StringBuilder();
            for (char c : clean.substring(6).toCharArray()) {
                if (Character.isLetter(c)) {
                    if (nums.length() == 0 && letters.length() < SEC_MAX_LETTERS) letters.append(c);
                } else {
                    if (letters.length() > 0 && nums.length() < 2) nums.append(c);
                }
            }
            if (letters.length() > 0) {
                sb.append('-').append(letters).append(nums);
            }
        }
        return sb.toString();
    }

    // Year & Section box: capitals only, dashes added automatically
    public static void applySection(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                Document doc = fb.getDocument();
                String current = doc.getText(0, doc.getLength());
                String next = current.substring(0, offset)
                            + (text == null ? "" : text)
                            + current.substring(offset + length);
                fb.replace(0, doc.getLength(), formatSection(next), attrs);
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs)
                    throws BadLocationException {
                replace(fb, offset, 0, text, attrs);
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                replace(fb, offset, length, "", null);
            }
        });
    }
}