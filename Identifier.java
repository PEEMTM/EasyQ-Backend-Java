import java.util.Map;
import java.util.Random;

public class Identifier implements Expr{
    private String name;

    public Identifier(String name) {
        this.name = name;
    }

    public int eval(Map<String,Integer> data) {
        if (data.containsKey(name))
            return data.get(name);
        else if(name == "random"){
            int MAX_RAND_BOUND = 100;
            Random rand = new Random();
            return rand.nextInt(MAX_RAND_BOUND);
        }
        throw new EvalError("undefined Identifier: " + name);
    }
    public void prettyPrint(StringBuilder s) {
        s.append("(");
        s.append(name);
        s.append(")");
  }
}
