# CSVFileParser
Helper to convert experiment dataset of cells into one csv format for further investigations.

## Files to parse
An experiment file is seperated in __metaData block, header and data rows__.
There can be __multiple metaData and data rows__, but __only one header__.

The __metaData block__ can look like this:
    
    metainfo1, metadata1
    metainfo2, metadata2
    ...
    metainfoZ, metadataZ

The header can look like this:

    header_name1,header_name2...header_nameX

and the data rows look like this, while dataY_1 is the name of the data row.

    data1_1,data1_2...data1_X
    data2_1,data2_2...data2_X
    ...
    dataY_1,dataY_2...dataY_X

The column number of header and data should be the same.

__Example input files__

File 1:

    Name, Peter
    Last Name, Pan
    Date, "Mar 18, 2014 1:20:39 PM"
    Data Name, Event A, Event B
    AAA,123,987
    BBB,123,987

File 2:
    
    Name, Peter
    Last Name, Pan
    Date, "Mar 19, 2014 1:20:39 PM"
    Data Name, Event A, Event B
    AAA,456,654
    BBB,456,654

## Exported csv File
The programm will parse files like above into one csv line each and combine them together as rows. the first line will be a header for the csv, 
containing the metadata names, and combinated names from the first column of the datarows and the header rows.
The second and following rows will be the data values matching to the combinated header name. You can use a Filter to select only headers that are needed.

__Example export file:__

    Name,Last Name, Date, AAA Event A, AAA Event B,BBB Event A, BBB Event B 
    Peter,Pan,"Mar 18, 2014 1:20:39 PM",123,987,123,987
    Peter,Pan,"Mar 19, 2014 1:20:39 PM",456,654,456,654
    
## Usage
### Use in Eclipse
Get a copy of the master. Import that project as an existing project to your eclipse workspace.
Go to your terminal and to the project path than run 

on Linux:

    gradle cleanEclipse eclipse

or on Windows

    ./gradle.bat cleanEclipse eclipse
    
Refresh your eclipse project, have fun changing the code.

### Generate jar file

Get a copy of the master. Go to your terminal and to the project path than run 

on Linux:

    gradle fatjar

or on Windows

    ./gradle.bat fatjar

This will automaticly download dependencies and build a new jar file that you can find in {ProjectPath}/build/libs/.

### Use the jar
Use the generated jar like this on your terminal:

     java -jar [Path/]FileParser-all-[version].jar [options]
     
Options are:

     -filter (-f) WERT    : Sets a column filter, can be used multiple times
     -headerRow (-h) N    : Sets the row of the table header, 0 based
     -help                : Shows the help manual you are seeing right now.
     -ignoreHeader        : If there are different header in your files, use this
                            flag after adding all variants of the needed column to
                            the filter option. There will be no further check, you
                            have to be sure what you are doing.
     -inputPath (-i) WERT : Sets a path to the root folder you want to parse
     -ouputFile (-o) WERT : Sets the file path to output in
     -seperator (-s) WERT : Sets a specific seperator. Default is ','
     
__Example usage__

File:

    Name, Peter
    Last Name, Pan
    Date, "Mar 18, 2014 1:20:39 PM"
    Data Name, Event A, Event B
    AAA,123,987
    BBB,123,987

Run with option 

    -i {inputFolder} -o {exportPath}/export.csv -h 3 -f "Event A"
    
Will generate a file named export.csv in {exportPath} like so:

    Name,Last Name, Date, AAA Event A, BBB Event A 
    Peter,Pan,"Mar 18, 2014 1:20:39 PM",123,123
