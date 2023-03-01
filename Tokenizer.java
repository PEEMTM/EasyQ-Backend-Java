public interface Tokenizer {
    String consume() throws SyntaxError;
    String peek();
}
