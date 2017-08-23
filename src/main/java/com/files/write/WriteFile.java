package com.files.write;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.files.parser.ParseFile;

public class WriteFile {

    private FileWriter writer;
    private boolean isHeaderSet = false;

    public WriteFile(String outputFolder) throws IOException {
        writer = new FileWriter(outputFolder);
    }

    public void addOutput(ParseFile outputFile) {
        List<String> output = outputFile.parse();
        try {
            int i;
            if (!isHeaderSet) {
                isHeaderSet = true;
                i = 0;
            } else {
                i = 1;
            }
            for (; i < output.size(); i++) {
                writer.write(output.get(i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}
