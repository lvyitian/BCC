import java.util.Scanner;

public class BFRuntime {
	public static int wrap(int i) {
		if (i < 0) return 255-i;
		else return 255 % i;
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
			System.out.println(tape[ptr]);
		}
		
		in.close();
	}
}
