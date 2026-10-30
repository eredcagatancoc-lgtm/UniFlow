package UniFlow;

public class Hint {

    public static void apply(javax.swing.JTextField field, String example) {
        final javax.swing.JLabel hint = new javax.swing.JLabel(example);
        hint.setForeground(new java.awt.Color(150, 150, 150));
        hint.setFont(field.getFont().deriveFont(java.awt.Font.ITALIC));
        hint.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 6, 0, 0));

        field.setLayout(new java.awt.BorderLayout());
        field.add(hint, java.awt.BorderLayout.CENTER);

        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void update() { hint.setVisible(field.getText().isEmpty()); }
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { update(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { update(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        });
    }
}