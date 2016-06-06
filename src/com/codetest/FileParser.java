package com.codetest;

import java.io.File;
import java.io.FileNotFoundException;

/**
* This interface is for parse file, can be used by factory to create any kind of file parser.
 */
public interface FileParser {

    public void parserFile(File file) throws FileNotFoundException;
}
