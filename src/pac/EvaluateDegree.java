package pac;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//class for evaluating degree of a multivariate polynomial
public class EvaluateDegree {
	// scanner obj. for taking input
	static Scanner scannerObject = new Scanner(System.in);
	// list for storing degree of each term
	static List<List<Integer>> degreeList = new ArrayList<>();
	// list for storing valid operators
	static List<Character> operatorList = new ArrayList();

	/**
	 * will get input from the user
	 * 
	 * @return string polynomial
	 * @throws Exception in case polynomial string is not valid
	 */
	static public String getInputFromUser() throws Exception {
		System.out.println("Enter a multivariate polynomial without spaces without brackets");
		String polynomial = scannerObject.nextLine();
		polynomial = polynomial.toLowerCase();
		if (polynomial.contains(" ")) {
			throw new Exception("Spaces not allowed");
		}
		return polynomial;
	}

	/**
	 * will cal. degree of polynomial
	 * 
	 * @param polnomial whose degree we need to cal.
	 * @return list of degree of each term
	 */
	static public List<List<Integer>> evaluateDegree(String polnomial) {

		// will convert polynomail string to char array
		char polyArray[] = polnomial.toCharArray();
		// sublist for storing each term's degree
		List<Integer> sublist = new ArrayList<>();
		int degree = 0;

		// will iterate throughout the polynomial array
		for (int polyIndex = 0; polyIndex < polyArray.length; polyIndex++) {

			// if variable is encountered
			if (polyArray[polyIndex] >= 97 && polyArray[polyIndex] <= 122) {
				if (polyIndex < polyArray.length - 1 && polyArray[polyIndex + 1] == '^') {
				} else {
					degree += 1;
				}
			}

			// if power operator is encountered
			else if (polyArray[polyIndex] == '^' && polyArray[polyIndex + 1] >= '0'
					&& polyArray[polyIndex + 1] <= '9') {
				if (!sublist.isEmpty()) {
					int tempDegree = (int) (sublist.get(0));
					degree = polyArray[polyIndex + 1];
					int characterOfDegree = (int) degree;
					characterOfDegree -= 48;
					characterOfDegree += tempDegree;
					if (sublist.get(0) >= 0) {
						sublist.remove(0);
						sublist.add(characterOfDegree);
					}
					degree = 0;
					polyIndex++;
				} else {
					int tempDegree = polyArray[polyIndex + 1];
					int characterOfDegree = (int) tempDegree;
					characterOfDegree -= 48;
					characterOfDegree += degree;
					sublist.add(characterOfDegree);
					degree = 0;
					polyIndex++;
				}
			}

			// if add. or sub. op. is encountered
			else if (polyArray[polyIndex] == '+' || polyArray[polyIndex] == '-') {
				if (sublist.isEmpty()) {
					sublist.add(degree);
					degree = 0;
				} else if (degree > 0) {
					int temporaryDegree = degree + sublist.get(0);
					sublist.remove(0);
					sublist.add(temporaryDegree);
				}
				degreeList.add(sublist);
				sublist = new ArrayList<>();
				degree = 0;

			}

			// if zero is encountered
			else if (polyArray[polyIndex] == '0') {
				if (polyIndex > 0 && (polyArray[polyIndex - 1] == '*' || polyArray[polyIndex - 1] == '0')) {
					if (!sublist.isEmpty()) {
						sublist.remove(0);
					}
					sublist.add(-1);

				} else if (polyIndex > 0 && (polyArray[polyIndex - 1] >= 97 || polyArray[polyIndex - 1] <= 122)) {
					if (!sublist.isEmpty()) {
						sublist.remove(0);
					}
					sublist.add(-1);

				} else if (polyIndex == 0) {
					sublist.add(-1);
				}
			} else {
			}
		}

		if (sublist.isEmpty()) {
			sublist.add(degree);
		}
		degreeList.add(sublist);
		return degreeList;
	}

	public static void main(String[] args) {
		operatorList.add('+');
		operatorList.add('-');
		operatorList.add('*');
		operatorList.add('/');
		operatorList.add('^');
		try {
			// getting input polynomial from user
			String polynomial = getInputFromUser();
			boolean isValidPoly = isValidPolynomial(polynomial);
			// check whether polynomial is valid or not
			if (isValidPoly) {
				degreeList = evaluateDegree(polynomial);
				// seeting maxDegree's value to extract max degree
				int maxDegree = Integer.MIN_VALUE;
				for (int degreeIndex = 0; degreeIndex < degreeList.size(); degreeIndex++) {
					int degree = degreeList.get(degreeIndex).get(0);
					if (degree == -1) {
						degree = 0;
					}
					maxDegree = Math.max(maxDegree, degree);
				}
				System.out.println("Degree of multivariate polynomial is " + maxDegree);

			} else {
				System.out.println("Polynomial is not valid");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * checks whether a polynomial is valid or not
	 * 
	 * @param polynomial input provided by the user
	 * @return whether a polynomial is valid or not
	 * @throws Exception in case polynomial is invalid
	 */
	static boolean isValidPolynomial(String polynomial) throws Exception {

		// traversing throughout the polynomial string
		for (int polyIndx = 0; polyIndx < polynomial.length(); polyIndx++) {

			if ((polynomial.charAt(polyIndx) >= 97 && polynomial.charAt(polyIndx) <= 122)
					|| (polynomial.charAt(polyIndx) >= '0' && polynomial.charAt(polyIndx) <= '9')
					|| operatorList.contains(polynomial.charAt(polyIndx))) {

				if (operatorList.contains(polynomial.charAt(polyIndx))) {

					// if 2 operators are encountered
					if (polyIndx < polynomial.length() - 1 &&
							operatorList.contains(polynomial.charAt(polyIndx + 1))) {
						throw new Exception("Invalid polynomial");
					}
					// if an operator(/) and a variable is encountered
					else if (polynomial.charAt(polyIndx) == '/') {
						if (polynomial.charAt(polyIndx + 1) >= 97 && polynomial.charAt(polyIndx + 1) <= 122) {
							throw new Exception("Invalid polynomial");
						}
					}
					// if an operator(*) and a variable is encountered
					else if (polynomial.charAt(polyIndx) == '*') {
						if (polynomial.charAt(polyIndx + 1) >= 97 && polynomial.charAt(polyIndx + 1) <= 122) {
							throw new Exception("Invalid polynomial");
						}
					}
				} else {
				}
			}
			// if there is no coefficient
			else if (polynomial.charAt(polyIndx) == '^'
					&& !(polynomial.charAt(polyIndx + 1) >= '0' && polynomial.charAt(polyIndx + 1) <= '9')) {
				throw new Exception("Invalid polynomail");
			}

			else {
				throw new Exception("Invalid polynomial");
			}
		}
		return true;
	}
}
