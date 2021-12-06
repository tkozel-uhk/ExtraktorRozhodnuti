package cz.fim.pdf;

import org.apache.pdfbox.multipdf.PageExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Converter {

    public static void convert(String filename, int pages, JProgressBar progressBar) throws IOException {
        File file = new File(filename);
        PDDocument doc = PDDocument.load(file);
        int pgNum = doc.getNumberOfPages();
        progressBar.setMaximum(pgNum);
        PageExtractor pageExtractor = new PageExtractor(doc);
        PDDocument result = new PDDocument();
        for (int i = 1; i <= doc.getNumberOfPages(); i += pages) {
            pageExtractor.setStartPage(i);
            pageExtractor.setEndPage(i);
            PDDocument out = pageExtractor.extract();
            result.importPage(out.getPage(0));
            out.close();
            progressBar.setValue(progressBar.getValue() + pages);
        }
        String newFileName = file.getParentFile().getAbsolutePath()+File.separator+"OUT_" + file.getName();
        result.save(newFileName);
        result.close();
        doc.close();

    }
}
