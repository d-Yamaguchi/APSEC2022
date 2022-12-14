package jp.ntt.sic.automig.util;

public class Pair<T1, T2> {

    T1 first;
    
    T2 second;
    
    public static <T1, T2> Pair<T1, T2> of(T1 first, T2 second){
        return new Pair<>(first, second);
    }
    
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
    
    public T1 getFirst() {
        return first;
    }
    
    public T2 getSecond() {
        return second;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Pair<T1, T2> other = (Pair<T1, T2>) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        if (second == null) {
            return other.second == null;
        } else return second.equals(other.second);
    }
    
    
}
