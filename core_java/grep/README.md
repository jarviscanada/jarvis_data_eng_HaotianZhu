# Introduction
This app allows user to research and find all text pattern recursively within a given folder and write all match text lines in an output text file.
# Usage

`[regex] [rootPath] [outFile]`

example:
` .*Romeo.*Juliet.*  ./data  ./out/grep.txt `

running from terminal
`java -classpath target/classes ca.jrvs.apps.grep.App .*Romeo.*Juliet.*  ./data /out/grep.txt`

# Pseudocode
```

lines <- store all matched lines 
outputFile<- generate file with name [outfile]

for file in allFileUnderFolder(rootPath)
    for line in file
        if file.contains(regex)
            lines.add(line)

outputFile.write(lines)
         
```



# Performance Issue
I think the memory costs too much. We could directly write line to file when we find such line contains the pattern. 
Currently, we find all lines and store them in memory and then write them to a file, which is inefficient and slow when we have a huge data input.

# Improvement

- modify Interface, so that each function return Stream<File> or Stream<String> instead of List<File> and List<String>

- modify the algorithm

```
outputFile = getOutputFile()

for file in allFiles:
    for line in file:
        if(line.contains(patter)):
            outputFile.write(line)

```

-  add more argument to tell App how deep to get files and what type of file need to be filter 

example

`haotian ./data ./out -d 3 -t txt`