package Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Model.Host;

public class ReadGeneticCode {
    GeneticCodeParser evaluator = new GeneticCodeParser();
    ParserExpr parser;
    StringBuilder strBuild = new StringBuilder();
    static Scanner myReader;

    public static void main(String[] args) {
        Host host = new Host();
        ReadGeneticCode RGC = new ReadGeneticCode();
        String src = RGC.readfile("./src/Parser/testread.txt");
        try {
            RGC.evaluate(src,host);
        } catch (SyntaxError e) {
            e.printStackTrace();
        }
    }

    public String readfile(String filename){
        try {
            File myObj = new File(filename);
            myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              strBuild.append(" "+myReader.nextLine());
            }
            myReader.close();
            return strBuild.toString();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        return null;
    }

    public String evaluate(String src,Host host) throws SyntaxError{
        parser = new ParserExpr(src, host);
        Program program = parser.parseProgram();
        evaluator.evalProgram(program, host);
        //System.out.println(evaluator.evalProgram(program, host));
        return "true";
    }
}
