package Files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import engine.*;

public class LoadSettings {
	public JSONObject settingsdata = new JSONObject();
	public static BufferedImage errorimage;
	
	static void print(Object s) {
		System.out.println(s);
	}
	
	@SuppressWarnings("resource")
	public LoadSettings() {
		String content = "";
		try {
			content = new Scanner(new File("player/settings.json")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		settingsdata = new JSONObject(content);
		try {
			errorimage = ImageIO.read(new File(settingsdata.getString("error image")));
		} catch (IOException e) {
			print("error loading error image file");
			System.exit(-1);
		}
		if(settingsdata.getInt("mouseclicktimeout") == -1) {
			settingsdata.put("mouseclicktimeout", detectOptimalTimeout(1000, 5, 0) + 5);
			try {
				FileWriter myWriter = new FileWriter("player/settings.json");
			    myWriter.write(settingsdata.toString());
			    myWriter.close();
	        } catch (IOException e) {
	            System.err.println("An error occurred while writing to file: " + e.getMessage());
	        }
		}
		MouseListerner.mouseclickstimeout = settingsdata.getInt("mouseclicktimeout");
	}
	
	private static int detectOptimalTimeout(int timeoutMillis, int step, int minTimeout) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        while (timeoutMillis >= minTimeout) {
            boolean allTasksSuccessful = true;
            for (int i = 0; i < 5; i++) { // Test current timeout with 5 iterations
                Callable<Void> task = () -> {
                	//TODO change 12 to check for TwoD system time out
                    Thread.sleep(12); // Simulated task
                    return null;
                };
                try {
                    Future<Void> future = executor.submit(task);
                    future.get(timeoutMillis, TimeUnit.MILLISECONDS);
                } catch (TimeoutException e) {
                    allTasksSuccessful = false;
                    break;
                } catch (Exception e) {
                    System.err.println("Task failed: " + e.getMessage());
                }
            }

            if (allTasksSuccessful) {
                timeoutMillis -= step; // Reduce timeout if all tasks succeeded
            } else {
                break; // Stop if tasks start failing
            }
        }

        executor.shutdown();
        return timeoutMillis + step; // Return last successful timeout
    }
}
