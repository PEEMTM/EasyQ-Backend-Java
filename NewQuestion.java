import java.util.Map;

//Question{Name , <identifier>}, Random [choice+]

public class NewQuestion implements Expr{
    private final Expr name;
    private final String identifier;
    private final Expr choice;
    NewQuestion(Expr name,String identifier,Expr choice){
        this.name = name;
        this.identifier = identifier;
        this.choice = choice;
    }

    @Override
    public int eval(Map<String,Integer> data) throws EvalError {
        if(!data.containsKey(identifier)){ // set default value
            data.put(identifier,0);
        }
        switch (op) {
            case "=" ->{
                data.put(identifier,expression.eval(data));
                return 0;
            }
        }
        throw new EvalError("Unknown op: " + op);
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append(identifier);
        s.append(op);
        expression.prettyPrint(s);
    }
}
