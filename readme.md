# About the repo

This is a programming kata/dojo. The developed class ConfigurationSearcher 
can be used to solve chess puzzles (i.e. N-Queens problem and its generalized 
version of placing arbitrary chess pieces at arbitrary board).
The developed solution might be a subject of further optimizations.
I have written it with a goal just to practice TDD approach.

## Usage

For example, the following code will solve famous 8-Queens problem:

    ConfigurationSearcher searcher = new ConfigurationSearcher();
    int numberOfConfigurations = 
        searcher.numberOfUniqueConfigurations(
            8       /* number of board rows */, 
            8       /* number of columns */,
            "8Q"    /* chess picess to place specification */); 
    System.out.println("Results: " + numberOfConfigurations);
    
The program will print:
    
    Results: 92
    
To find out how many configurations exist to place 2 kings, 1 queen, 1 bishop, 
1 rook, 1 knight at the board with 6 rows and 9 columns the following code might be used:
             
    ConfigurationSearcher searcher = new ConfigurationSearcher();
    int numberOfConfigurations = 
        searcher.numberOfUniqueConfigurations(
            6                   /* number of board rows */, 
            9                   /* number of columns */,
            "2K 1Q 1B 1R 1N"    /* chess picess to place specification */); 
    System.out.println("Results: " + numberOfConfigurations);

The program will print:

    Results: 20136752

## Note

You might need to adjust java heap memory to solve some puzzles.

For example:

    -Xmx4g -Xmn1200m -XX:SurvivorRatio=50 -XX:+UseConcMarkSweepGC