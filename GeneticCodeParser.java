import java.util.HashMap;
import java.util.Map;

import Model.Host;

public class GeneticCodeParser {
    Host host;
    int loopcounter;
    Map<String,Integer> data = new HashMap<>();

    public int evalStatement(Expr statement){
        StringBuilder strBuild = new StringBuilder();
        statement.prettyPrint(strBuild);
        System.out.println(strBuild);
        return statement.eval(data);
    }

    public String evalProgram(Program program,Host host){
        while(program.hasNext()){
            evalStatement(program.nextStatement());
        }
        System.out.println(data.get("t"));
        return "finish";
    }
}
