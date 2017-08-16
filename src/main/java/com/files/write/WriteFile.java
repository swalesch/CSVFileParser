package com.files.write;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.files.parser.ParseFile;

public class WriteFile {

	private FileWriter writer;

	public WriteFile(String outputFolder) throws IOException {
		writer = new FileWriter(outputFolder);
	}

	public void addOutput(ParseFile outputFile) {
		List<String> output = outputFile.parse();
		try {
			for (String str : output) {
				writer.write(str + "\n");
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
