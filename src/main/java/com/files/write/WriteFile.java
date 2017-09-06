package com.files.write;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.files.parser.ParseFile;

public class WriteFile {

    private FileWriter writer;
    private ParseFile firstFile = null;
    private boolean isHeaderSet = false;
    private boolean _ignoreHeader;

    public WriteFile(String outputFolder, boolean ignoreHeader) throws IOException {
        _ignoreHeader = ignoreHeader;
        writer = new FileWriter(outputFolder);
    }

    public void addOutput(ParseFile outputFile) {
        List<String> output = outputFile.parse();
        if (_ignoreHeader || isHeaderTheSame(outputFile)) {
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
        } else {
            throw new IllegalStateException(
                    "Header are different, please make sure they are the same.\n Expected header: \""
                            + firstFile.getOriginalHeader() + "\"\n from file: " + firstFile.getFileName()
                            + "\n But this header: \"" + outputFile.getOriginalHeader() + "\"\n was fround in "
                            + outputFile.getFileName()
                            + "\n Check your files to have the same header line or use the ignoreHeader flag wisely.");
        }
    }

    private boolean isHeaderTheSame(ParseFile otherFile) {
        if (firstFile == null) {
            firstFile = otherFile;
            return true;
        }
        return firstFile.getOriginalHeader()
                .equals(otherFile.getOriginalHeader());
    }

    public void close() throws IOException {
        writer.close();
    }
}
