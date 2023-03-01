import java.util.*;

public class ParserExpr {
    private Host host;
    private TokenizerExpr tkz;
    private Program program;

    /**
     * Program → Command+
     * Command → Group{Name} [Question+] | Question
     * Question → Question{Name , <identifier>}, Random [choice+]
     * Random → rand | norand
     * Choice → C{Name}=ans | C{Name}
     * Name → <number> | <identifier>
     *
     * @throws SyntaxError
     */

    public boolean isNumber(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public ParserExpr(String src,Host host) throws SyntaxError {
        this.host = host;
        this.tkz = new TokenizerExpr(src);
    }

    // Program → Command+

    public Program parseProgram() throws SyntaxError {
        Program program = new Program();
        while (!tkz.peek("")) {
            program.addStatement(parseCommand());
        }
        program.Iterator();
        return program;
    }

    // Command → Question | Group
    private Expr parseCommand() throws SyntaxError {
        String this_peek = tkz.peek();
        return switch (this_peek) {
            case "G" -> parseGroup();
            default -> parseQuestion();
        };
    }

    // BlockStatement → { Statement* }
    private BlockStatement parseBlockStatement() throws SyntaxError {
        tkz.consume("{");

        LinkedList<Expr> prossed = new LinkedList<>();
        while (!tkz.peek("}")) {
            prossed.add(parseStatement());
        }

        tkz.consume("}");
        return new BlockStatement(prossed);
    }

    // Group → Group{Name} [Question+]
    private Group parseGroup() throws SyntaxError {
        LinkedList<Expr> question = new LinkedList<>();
        tkz.consume("G");
        tkz.consume("{");
        Expr name = parseName();
        tkz.consume("}");
        tkz.consume("[");
        while (true) {
            String this_peek = tkz.peek();
            if (this_peek.equals("Q")) {
                question.add(parseQuestion());
            }else if (this_peek.equals("]")){
                tkz.consume();
                break;
            }
        }
        return new Group(question);
    }

    // Question → Question{Name , <identifier>}, Random [choice+]
    private Expr parseQuestion() throws SyntaxError {
        tkz.consume("Q");
        tkz.consume("{");
        Expr name = parseName();
        tkz.consume(",");
        String identifier = tkz.consume();
        tkz.consume("}");
        tkz.consume(",");
        String this_peek = tkz.consume();
        if(this_peek.equals("rand")){
            boolean random = true;
        }else if(this_peek.equals("norand")){
            boolean random = false;
        }else {
            throw new SyntaxError("Syntax Error");
        }
        Choice choice = parseChoice();

        return new NewQuestion(name, identifier, random, choice);
    }

    // Choice → C{Name}=ans | C{Name}
    private Choice parseChoice() throws SyntaxError {
        Choice choice = new Choice();
        tkz.consume("[");
        while (true) {
            tkz.consume("C");
            tkz.consume("{");
            Expr name = parseName();
            tkz.consume("}");
            String this_peek = tkz.peek();
            choice.add(name);
            if (this_peek.equals("=")) {
                tkz.consume("ans");
                choice.addAns(name);
            }else if (this_peek.equals("]")){
                tkz.consume();
                break;
            }
        }
        choice.Iterator();
        return choice;
    }

    // Name → <number> | <identifier>
    private Expr parseName() throws SyntaxError {
        String peek = tkz.peek();
        if (isNumber(peek)) {
            return new IntForm(Integer.parseInt(tkz.consume()));
        }
        else {
            return parseIdentifier();
        }
    }

    // identifier is valuable

    private Expr parseIdentifier() throws SyntaxError {
        String peek = tkz.peek();
        if(peek.chars().allMatch(Character::isLetterOrDigit)) {
            return new Identifier(peek);
        }
        throw new SyntaxError("Syntax Error");
    }

}
