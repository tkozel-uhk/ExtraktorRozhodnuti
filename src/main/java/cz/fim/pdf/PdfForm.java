package cz.fim.pdf;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class PdfForm extends JFrame {
    private JTextField tfFile;
    private JButton btVybrat;
    private JSpinner spPages;
    private JProgressBar progress;
    private JButton btConvert;
    private JButton btKonec;
    private JPanel pnlMain;

    JFileChooser fch = new JFileChooser(System.getProperty("user.home"));

    public PdfForm()  {
        super("Převod rozhodnutí");
        setContentPane(pnlMain);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480,320);

        fch.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".pdf");
            }

            @Override
            public String getDescription() {
                return "PDF soubory (*.pdf)";
            }
        });


        spPages.getModel().setValue(10);

        btVybrat.addActionListener((e)-> selectFile());
        btConvert.addActionListener((e)-> convertFile(tfFile.getText(), (int)spPages.getModel().getValue()));

        btKonec.addActionListener((e)->System.exit(0));
    }

    private void convertFile(String filename, int pages) {
        System.out.printf("Filename: %s, Pages: %d\n", filename, pages);
        progress.setValue(0);
        try {
            if (filename.length() == 0) throw new Exception("Nebyl zadan nazev souboru!");
            Converter.convert(filename, pages, progress);
            JOptionPane.showMessageDialog(this, "Hotovo!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selectFile() {
        if (fch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            tfFile.setText(fch.getSelectedFile().getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new PdfForm().setVisible(true));
    }
}
