package stuff.stuff;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		ArrayList<Object> equation = new ArrayList<Object>();

		System.out.println("Enter expression (put spaces between all items entered):");

		Scanner scanner = new Scanner(System.in);
		String input = "";
		while (scanner.hasNextLine()) {
			input = scanner.nextLine();
			for (String s : input.split(" ")) {
				if (isInteger(s)) {
					equation.add(Integer.parseInt(s));
				} else {
					equation.add((String) s);
				}
			}
			break;
		}
		scanner.close();
		
		if(equation.contains(")")) {
			removeBrackets(equation);
		}
		if(equation.contains("^")) {
			exponentiate(equation);
		}
		if (equation.contains("*") || equation.contains("/")) {
			timesDivide(equation);
		}
		if (equation.contains("+") || equation.contains("-")) {
			plusMinus(equation);
		}
		System.out.println("Answer: " + equation.get(0));
	}
	
	public static ArrayList<Object> removeBrackets(ArrayList<Object> equation) {
		if(equation.contains(")") == false) return equation;
		int lowerIndex = equation.lastIndexOf("(");
		equation.remove(lowerIndex);
		int upperIndex = equation.indexOf(")");
		equation.remove(upperIndex);
		ArrayList<Object> subEquation = new ArrayList<Object>(equation.subList(lowerIndex, upperIndex));
		if (subEquation.contains("^")) {
			exponentiate(subEquation);
		}
		if (subEquation.contains("*") || subEquation.contains("/")) {
			timesDivide(subEquation);
		}
		if (subEquation.contains("+") || subEquation.contains("-")) {
			plusMinus(subEquation);
		}
		equation.set(lowerIndex, subEquation.get(0));
		equation.subList(lowerIndex + 1, upperIndex).clear();
		return removeBrackets(equation);
	}
	
	public static ArrayList<Object> exponentiate(ArrayList<Object> equation) {
		if(equation.contains("^") == false) return equation;
		int temp = equation.indexOf("^");
		Integer square = (int) Math.pow((Integer) equation.get(temp - 1), (Integer) equation.get(temp + 1));
		equation.set(temp, square);
		equation.remove(temp + 1);
		equation.remove(temp - 1);
		return exponentiate(equation);
	}
	
	public static ArrayList<Object> timesDivide(ArrayList<Object> equation) {
		if (equation.contains("*") == false && equation.contains("/") == false) return equation;
		if(equation.indexOf("/") == -1 || (equation.contains("*") && equation.contains("/") && equation.indexOf("*") < equation.indexOf("/"))) {
			int temp = equation.indexOf("*");
			Integer product = (Integer) equation.get(temp - 1) * (Integer) equation.get(temp + 1);
			equation.set(temp, product);
			equation.remove(temp + 1);
			equation.remove(temp - 1);
			return timesDivide(equation);
		} else {
			int temp = equation.indexOf("/");
			Integer quotient = (Integer) equation.get(temp - 1) / (Integer) equation.get(temp + 1);
			equation.set(temp, quotient);
			equation.remove(temp + 1);
			equation.remove(temp - 1);
			return timesDivide(equation);
		}
	}
	
	public static ArrayList<Object> plusMinus(ArrayList<Object> equation) {
		if (equation.contains("+") == false && equation.contains("-") == false) return equation;
		if(equation.indexOf("-") == -1 || (equation.contains("+") && equation.contains("-") && equation.indexOf("+") < equation.indexOf("-"))) {
			int temp = equation.indexOf("+");
			Integer sum = (Integer) equation.get(temp - 1) + (Integer) equation.get(temp + 1);
			equation.set(temp, sum);
			equation.remove(temp + 1);
			equation.remove(temp - 1);
			return plusMinus(equation);
		} else {
			int temp = equation.indexOf("-");
			Integer difference = (Integer) equation.get(temp - 1) - (Integer) equation.get(temp + 1);
			equation.set(temp, difference);
			equation.remove(temp + 1);
			equation.remove(temp - 1);
			return plusMinus(equation);
		}
	}
	
	public static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
