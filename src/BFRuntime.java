import java.util.Arrays;
import java.util.Scanner;

public class BFRuntime {
	
	public static void main(String[] args) {
		byte tape[] = new byte[40000];
		Arrays.fill(tape, (byte)-128);
		int ptr = 0;
		Scanner in = new Scanner(System.in);

		++ptr;
		--ptr;
		++tape[ptr];
		--tape[ptr];
		System.out.println(tape[ptr] + 128);
		tape[ptr] = (byte)(in.nextInt() - 127);
		
		while (tape[ptr] + 128 != 0) {
			++ptr;
		}
		
		in.close();
	}
}
