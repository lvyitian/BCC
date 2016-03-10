import java.util.ArrayList;
import java.util.Scanner;

public class BFRuntime {
	public static void ensureCapacity(int index, ArrayList<Byte> arr) {
		for (int i = arr.size(); i <= index; ++i) {
			arr.add((byte)0);
		}
	}
	public static void incr(int index, ArrayList<Byte> arr) {
		ensureCapacity(index, arr);
		byte current = arr.get(index);
		arr.set(index, (++current));
	}
	public static void decr(int index, ArrayList<Byte> arr) {
		ensureCapacity(index, arr);
		byte current = arr.get(index);
		arr.set(index, (--current));
	}
	public static void print(int index, ArrayList<Byte> arr) {
		ensureCapacity(index, arr);
		byte current = arr.get(index);
		System.out.print((int)(current & 0xff) + " ");
	}
	public static void set(int index, ArrayList<Byte> arr, int item) {
		ensureCapacity(index, arr);
		arr.set(index, (byte)item);
	}
	
	public static void main(String[] args) {
		ArrayList<Byte> tape = new ArrayList<Byte>();
		int ptr = 0;
		Scanner in = new Scanner(System.in);

		++ptr;
		--ptr;
		incr(ptr, tape);
		decr(ptr, tape);
		set(ptr, tape, in.nextInt());
		print(ptr, tape);
		
		while (tape.get(ptr) != 0) {
			++ptr;
		}
		
		in.close();
	}
}
