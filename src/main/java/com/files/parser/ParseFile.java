package com.files.parser;

import java.util.List;
import java.util.regex.Pattern;

import com.files.read.ReadFile;
import com.google.common.collect.Lists;
import com.util.Util;

public class ParseFile {

    private ReadFile _inputFile;
    private String _seperator;
    private List<String> _columnFilter;
    private int _headerRow;

    public ParseFile(ReadFile inputFile, String seperator, List<String> columnFilter, int headerRow) {
        _inputFile = inputFile;
        _seperator = seperator;
        _columnFilter = columnFilter;
        _headerRow = headerRow;
    }

    public List<String> parse() {
        int numberMetaData = _headerRow - 1;
        int startLineData = _headerRow + 1;
        List<Integer> columnFilter = getColumnFilter(_headerRow);
        List<String> output = Lists.newArrayList();
        output.add(getHeader(numberMetaData, startLineData, _headerRow));

        List<Object> outputLine = Lists.newArrayList();
        outputLine.addAll(getMetaData(numberMetaData));

        for (int i = startLineData; i < _inputFile._fileAsArrayList.size(); i++) {
            if (isEmptyLine(i)) {
                continue;
            }

            List<String> line = Util.convertStringToStringList(_inputFile._fileAsArrayList.get(i), _seperator);

            for (int columnId : columnFilter) {
                outputLine.add(line.get(columnId));
            }
        }
        output.add(Util.convertListToString(outputLine, _seperator));
        return output;
    }

    private List<String> getMetaData(int numberMetaData) {
        List<String> metaDatas = Lists.newArrayList();
        for (int i = 0; i < numberMetaData; i++) {
            String metaData = Util.convertStringToStringList(_inputFile._fileAsArrayList.get(i), _seperator)
                    .get(1);
            metaDatas.add(metaData);
        }
        return metaDatas;
    }

    private List<Integer> getColumnFilter(int headerRow) {
        List<String> columNames = Util.convertStringToStringList(_inputFile._fileAsArrayList.get(headerRow),
                _seperator);
        List<Integer> columnFilter = Lists.newArrayList();
        for (int j = 1; j < columNames.size(); j++) {
            if (isInFilter(columNames.get(j))) {
                columnFilter.add(j);
            }
        }
        return columnFilter;
    }

    private String getHeader(int numberMetaData, int startLineData, int headerRow) {
        List<Object> header = Lists.newArrayList();
        List<String> columNames = Util.convertStringToStringList(_inputFile._fileAsArrayList.get(headerRow),
                _seperator);
        for (int i = 0; i < _inputFile._fileAsArrayList.size(); i++) {
            if (isEmptyLine(i)) {
                continue;
            }

            if (i >= numberMetaData && i < startLineData) {
                continue;
            }

            String name = Util.convertStringToStringList(_inputFile._fileAsArrayList.get(i), _seperator)
                    .get(0);

            if (i >= startLineData) {
                for (int j = 1; j < columNames.size(); j++) {
                    if (isInFilter(columNames.get(j))) {
                        String combinatedName = name + " " + columNames.get(j);
                        header.add(combinatedName);
                    }
                }
            } else {
                header.add(name);
            }
        }
        return Util.convertListToString(header, _seperator);

    }

    private boolean isInFilter(String columnName) {
        if (_columnFilter.isEmpty()) {
            return true;
        }
        for (String nameToFilter : _columnFilter) {
            if (Pattern.matches(nameToFilter, columnName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmptyLine(int i) {
        return _inputFile._fileAsArrayList.get(i)
                .trim()
                .equals("");
    }

}
