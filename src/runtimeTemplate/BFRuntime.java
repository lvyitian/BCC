package runtimeTemplate;
import java.util.ArrayList;
import java.util.Scanner;

public class BFRuntime {
	public static void ensureCapacity(int index, ArrayList<Byte> arr) {
		for (int i = arr.size(); i <= index; ++i) {
			arr.add((byte)0);
		}
	}
	public static byte get(int index, ArrayList<Byte> arr) {
		ensureCapacity(index, arr);
		return arr.get(index);
	}
	public static void set(int index, ArrayList<Byte> arr, byte item) {
		ensureCapacity(index, arr);
		arr.set(index, item);
	}
	public static void incr(int index, ArrayList<Byte> arr) {
		byte current = get(index, arr);
		arr.set(index, (++current));
	}
	public static void decr(int index, ArrayList<Byte> arr) {
		byte current = get(index, arr);
		arr.set(index, (--current));
	}
	public static void print(int index, ArrayList<Byte> arr) {
		byte current = get(index, arr);
		System.out.print((char)current);
	}
	
	public static void main(String[] args) {
		ArrayList<Byte> tape = new ArrayList<Byte>();
		int ptr = 0;
		Scanner in = new Scanner(System.in);

		++ptr;
		--ptr;
		incr(ptr, tape);
		incr(ptr, tape);
		set(ptr, tape, (byte)in.next().charAt(0));
		incr(ptr, tape);
		print(ptr, tape);
		
		while (get(ptr, tape) != 0) {
			++ptr;
		}
		
		in.close();
	}
}
