import java.util.Scanner;

public class BFRuntime {
	public static int wrap(int i) {
		return (i + 256) % 256;
	}
	
	public static void main(String[] args) {
		int tape[] = new int[256];
		int ptr = 0;
		Scanner in = new Scanner(System.in);

		ptr = wrap(++ptr);
		ptr = wrap(++ptr);
		ptr = wrap(++ptr);
		System.out.println(tape[ptr]);
		tape[ptr] = in.nextInt();
		
		while(tape[ptr] != 0) {
			ptr = wrap(--ptr);
		}
		
		in.close();
	}
}
