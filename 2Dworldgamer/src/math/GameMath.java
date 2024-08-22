package math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GameMath {
	void print(Object o) {
		System.out.println(o);
	}
	public int countdecimalsplacesamount(double input) {
		int amount = 0;
		long tothemax = 1;
		long finalnum = (int)input;
		while(finalnum < Math.ceil(input * tothemax)) {
			tothemax *= 10;
			finalnum = (int)(input * tothemax);
			amount++;
		}
		return amount;
	}
	public double specificDouble(double base, double divider, int decimalplaces) {
		double result = Math.round(base) * divider;
        BigDecimal bdResult = new BigDecimal(result);
        String formattedResult = bdResult.setScale(decimalplaces, RoundingMode.HALF_UP).toPlainString();
        print(formattedResult);
        double finalResult = Double.parseDouble(formattedResult);
        print(Double.parseDouble(formattedResult));
        return finalResult;
	}
}
