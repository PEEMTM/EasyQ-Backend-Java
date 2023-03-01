public class Double<T1, T2>{
    protected T1 fst;
    protected T2 snd;

    public Double(T1 fst, T2 snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public T1 fst() { return fst;}
    public T2 snd() { return snd;}
}
