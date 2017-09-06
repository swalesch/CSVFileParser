package com.files.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

public class ReadFile {

    public List<String> _fileAsArrayList;
    public String _fileName;

    public ReadFile(File file) {
        Preconditions.checkNotNull(file);
        _fileName = file.getAbsolutePath();
        _fileAsArrayList = getFileAsList(file);
    }

    private List<String> getFileAsList(File file) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}