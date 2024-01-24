package gamelog;

import java.util.Arrays;

public class GameStatusLog {
	static boolean printtrace = true;
	String blacklist[] = {"seed","fileLoader"};
	public void Log(String type, String log) {
			if (!(Arrays.stream(blacklist).anyMatch("s"::equals))) {
				System.out.println(log);
			}
	}
}
