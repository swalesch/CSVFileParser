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
import com.google.common.collect.Lists;

public class Starter {
    @Option(name = "-inputPath", aliases = {
            "-i" }, required = true, usage = "Sets a path to the root folder you want to parse")
    private String _inputPath;

    @Option(name = "-ouputFile", aliases = { "-o" }, required = true, usage = "Sets the file path to output in")
    private String _outputFilePath;

    @Option(name = "-headerRow", aliases = {
            "-h" }, required = true, usage = "Sets the row of the table header, 0 based")
    private int _headerRow;

    @Option(name = "-seperator", aliases = {
            "-s" }, required = false, usage = "Sets a specific seperator. Default is ','")
    private String _seperator = ",";

    @Option(name = "-filter", aliases = {
            "-f" }, required = false, usage = "Sets a column filter, can be used multiple times")
    private List<String> _columnFilter = Lists.newArrayList();

    @Option(name = "-help", required = false, usage = "Shows the help manual you are seeing right now.")
    private boolean _help;

    @Option(name = "-ignoreHeader", required = false, usage = "If there are different header in your files, use this flag after adding all variants of the needed column to the filter option. There will be no further check, you have to be sure what you are doing.")
    private boolean _ignoreHeader;

    @Argument
    private List<String> _arguments = new ArrayList<>();

    public static void main(String[] args) {
        new Starter().doMain(args);
    }

    private void doMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (!isNotNull(_inputPath, _outputFilePath)) {
                throw new CmdLineException(parser, "-inputPath and -ouputFile has to be set",
                        new IllegalStateException());
            }

            if (_headerRow < 1) {
                throw new CmdLineException(parser, "-headerRow has to be greater than 0", new IllegalStateException());
            }

            File inputFile = new File(_inputPath);
            System.out.println(inputFile);
            WriteFile writeFile = new WriteFile(_outputFilePath, _ignoreHeader);

            new DirExplorer((level, path, file) -> path.endsWith(".csv"), (level, path, file) -> {
                System.out.println("Parsing " + file.getAbsolutePath());
                writeFile.addOutput(new ParseFile(new ReadFile(file), _seperator, _columnFilter, _headerRow));
            }).explore(inputFile);

            System.out.println("Written to " + _outputFilePath);

            writeFile.close();

        } catch (CmdLineException e) {
            if (_help) {
                System.out.println("Options: ");
                parser.printUsage(System.out);
                return;
            }
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Error: " + e.getMessage());
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
