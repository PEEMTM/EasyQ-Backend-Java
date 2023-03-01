import java.util.Iterator;
import java.util.*;

public class Group implements Expr{
    LinkedList<Expr> prossed = new LinkedList<>();

    public Group(LinkedList<Expr> proseed){
        this.prossed.addAll(proseed);
    }

    @Override
    public int eval(Map<String,Integer> data) {
        Iterator<Expr> itr = prossed.iterator();
        while(itr.hasNext()){
            Expr statement = itr.next();
            statement.eval(data);
        }
        return 0;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
    }
}
