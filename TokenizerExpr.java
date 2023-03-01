import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class TokenizerExpr implements Tokenizer{
    private String src;
    private String next;
    private int pos;
    public TokenizerExpr(String src) throws SyntaxError {
        if (src.replace(" ", "").isEmpty()) // Empty String
            throw new NoSuchElementException();
        this.src = src;
        pos = 0;
        computeNext();
    }

    private void computeNext() throws SyntaxError {
        StringBuilder s = new StringBuilder();
        while (pos < src.length()) {
            char c = src.charAt(pos);
            if (Character.isDigit(c)) {  // start of number
                s.append(c);
                for (pos++; pos < src.length() && Character.isDigit(src.charAt(pos)); pos++)
                    s.append(src.charAt(pos));
                break;
            }else if (c == '[' || c == ']' || c == '=' || c == ',' || c == '{' || c == '}') {
                s.append(c);
                pos++;
                break;
            }else if (isCharacter(c)){ 
                s.append(c);
                for (pos++; pos < src.length() && isCharacter(src.charAt(pos)); pos++)
                    s.append(src.charAt(pos));
                break;
            }else if (isSpace(c))
                pos++;
            else throw new SyntaxError("Unknown character: " + c);
        }
        next = s.toString();

    }

    private boolean isCharacter(char ch) {
        return Pattern.matches("[a-zA-z]", new StringBuilder(1).append(ch));
    }

    public boolean peek(String s){
        return peek().equals(s);
    }

    public void consume(String s) throws SyntaxError {
        if(peek(s)) {
            consume();
        }else
            throw new SyntaxError(peek());
    }

    private boolean isSpace(char c) {
        return c == ' ';
    }

    @Override
    public String peek() { return next; }
    @Override
    public String consume() throws SyntaxError {
        String result = next;
        if (pos < src.length())
            computeNext();
        else
            next = "";
        return result;
    }
}
