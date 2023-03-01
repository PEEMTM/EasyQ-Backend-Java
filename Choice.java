import java.util.Iterator;
import java.util.*;

public class Choice{
    LinkedList<Double<Expr,Boolean>> choicelist = new LinkedList<>();
    Iterator<Double<Expr,Boolean>> itr;

    public void add(Expr choice){
        choicelist.add(new Double<>(choice,false));
    }

    public void addAns(Expr choice){
        choicelist.add(new Double<>(choice,true));
    }

    public Double<Expr, Boolean> nextStatement(){
        return itr.next();
    }

    public boolean hasNext(){
        return itr.hasNext();
    }

    public void Iterator(){
        itr = choicelist.iterator();
    }

    public void resetIterator(){
        itr = null;
    }
}
