package Logic1;

public class Pair implements Comparable <Pair> {
	public int first, second;
	public Pair(int first, int second) {
		this.first = first;
		this.second = second;
		
	}
	public int compareTo(Pair other){
		return other.second - this.second;
	}
}
