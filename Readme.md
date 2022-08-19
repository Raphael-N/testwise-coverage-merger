# Testwise Coverage Merger

You can merge two or more coverage files by simply calling the program with the paths 
you want to look for coverage files in. Folders and files can be mixed.
If a directory is used, it must not contain JSON files with contents other than testwise coverage.

To build the executable jar file, you can use mavan package:
```
mvn package
```


Example Call:
```
java -jar testwise-coverage-converter-0.1.jar /path/to/testwise/coverage
```

For big coverage files this can be quite memory hungry since it keeps all coverage
files in memory for some time so be sure to give java enough memory for your testwise coverage files.