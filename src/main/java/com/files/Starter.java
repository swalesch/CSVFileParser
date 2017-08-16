package com.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.files.parser.ParseFile;
import com.files.read.DirExplorer;
import com.files.read.ReadFile;
import com.files.write.WriteFile;

public class Starter {
	@Option(name = "-inputPath", aliases = {
			"-i"}, required = true, usage = "Sets a path to the root folder you want to parse")
	private String inputPath;

	@Option(name = "-ouputFile", aliases = {"-o"}, required = true, usage = "Sets the file path to output in")
	private String outputFilePath;

	@Argument
	private List<String> arguments = new ArrayList<>();

	public static void main(String[] args) {
		new Starter().doMain(args);
	}

	private void doMain(String[] args) {
		CmdLineParser parser = new CmdLineParser(this);
		try {
			parser.parseArgument(args);

			if (!isNotNull(inputPath, outputFilePath)) {
				throw new CmdLineException(parser, "-inputPath and -ouputFile has to be set",
						new IllegalStateException());
			}

			File inputFile = new File(inputPath);
			WriteFile writeFile = new WriteFile(outputFilePath);

			new DirExplorer((level, path, file) -> path.endsWith(".csv"), (level, path, file) -> {
				writeFile.addOutput(new ParseFile(new ReadFile(file)));
			}).explore(inputFile);

			writeFile.close();

		} catch (CmdLineException e) {
			System.err.println(e.getMessage());
			parser.printUsage(System.err);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isNotNull(String... args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				return false;
			}
		}
		return true;
	}
}
