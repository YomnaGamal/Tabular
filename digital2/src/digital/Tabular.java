package digital;

import java.util.Scanner;
import java.util.Vector;

public class Tabular {
	static int nOfV;
	static int nOfME;
	static int nOfDC;
	static int nOfE;
	static int step = 0;
	static Scanner reader = new Scanner(System.in);
	static Vector<Vector<String>> BV = new Vector<Vector<String>>();
	static Vector<String> NBV = new Vector<String>();
	static Vector<Vector<Boolean>> BVC = new Vector<Vector<Boolean>>();
	static Vector<Boolean> NBVC = new Vector<Boolean>();
	static Vector<String> PI = new Vector<String>();

	public static void main(String[] args) {
		scan();
		loop();
		alph();
	}

	public static void intial() {
		for (int i = 0; i < nOfV + 1; i++) {
			BV.add(new Vector<String>());
			BVC.add(new Vector<Boolean>());
		}
	}

	public static void scan() {

		System.out.println("Enter number of variables: ");
		nOfV = reader.nextInt();
		System.out.println("Enter number of main terms: ");
		nOfME = reader.nextInt();
		System.out.println("Enter number of dont care terms: ");
		nOfDC = reader.nextInt();
		nOfE = nOfME + nOfDC;
		intial();
		System.out.println("Enter THe Main Elements: ");
		for (int i = 0; i < nOfME; i++) {
			int n = reader.nextInt();
			String s = Integer.toBinaryString(n);
			for (int j = s.length(); j < nOfV; j++) {
				s = new StringBuffer(s).insert(0, "0").toString();
			}
			BV.get(Integer.bitCount(n)).add(s);
			BVC.get(Integer.bitCount(n)).add(false);
		}
		System.out.println("Enter Don't care elements:");
		for (int i = 0; i < nOfDC; i++) {
			int n = reader.nextInt();
			String s = Integer.toBinaryString(n);
			for (int j = s.length(); j < nOfV; j++) {
				s = new StringBuffer(s).insert(0, "0").toString();
			}
			BV.get(Integer.bitCount(n)).add(s);
			BVC.get(Integer.bitCount(n)).add(false);
		}
		System.out.println("Answer:");
	}

	public static int diffOne(String A, String B) {
		int x = 0;
		int k = 0;
		for (int i = 0; i < nOfV; i++) {
			if (A.charAt(i) != B.charAt(i)) {
				x++;
				k = i;
			}
		}
		if (x == 1) {
			return k;
		}
		return -1;
	}

	public static String newE(String A, int k) {
		String Comp = A;
		Comp = Comp.substring(0, k) + "X" + Comp.substring(k + 1);
		return Comp;
	}

	public static void loop() {
		do {
			step++;
			for (int f = 0; f < BV.size(); f++) {
				for (int l = 0; l < BV.elementAt(f).size(); l++) {
					System.out.printf(" %s ", BV.elementAt(f).elementAt(l));
				}
				System.out.printf("\n");
			}
			for (int r = 0; r < BV.size() - 1; r++) {
				for (int c = 0; c < BV.elementAt(r).size(); c++) {
					for (int k = 0; k < BV.elementAt(r + 1).size(); k++) {
						if (diffOne(BV.elementAt(r).elementAt(c), BV.elementAt(r + 1).elementAt(k)) != -1) {
							String s = newE(BV.elementAt(r).elementAt(c),
									diffOne(BV.elementAt(r).elementAt(c), BV.elementAt(r + 1).elementAt(k)));
							if (!exist(s, NBV)) {
								NBV.addElement(s);
								NBVC.addElement(false);
							}
							BVC.get(r).setElementAt(true, c);
							BVC.get(r + 1).setElementAt(true, k);
						}
					}
					if (BVC.elementAt(r).elementAt(c) == false) {
						if (!exist(BV.elementAt(r).elementAt(c), PI)) {
							PI.add(BV.elementAt(r).elementAt(c));
						}
					}
				}
				BV.set(r, NBV);
				BVC.set(r, NBVC);
				NBV = new Vector<String>();
				NBVC = new Vector<Boolean>();
			}
			for (int x = 0; x < BV.get(BV.size() - 1).size(); x++) {
				if (BVC.elementAt(BV.size() - 1).elementAt(x) == false) {
					if (exist(BV.elementAt(BV.size() - 1).elementAt(x), PI) == false) {
						PI.add(BV.elementAt(BV.size() - 1).elementAt(x));
					}
				}
			}
			BV.removeElementAt(BV.size() - 1);
		} while (BV.size() != 0);
	}

	public static boolean exist(String A, Vector<String> V) {
		for (int i = 0; i < V.size(); i++) {
			if (A.equals(V.get(i))) {
				return true;
			}
		}
		return false;
	}

	public static void alph() {
		System.out.println("prime implicants:");
		for (int j = 0; j < PI.size(); j++) {
			char c = 'A';
			for (int i = 0; i < nOfV; i++) {
				if (PI.elementAt(j).charAt(i) == '1') {
					System.out.print(c++);
				} else if (PI.elementAt(j).charAt(i) == '0') {
					System.out.print((c++) + "'");
				} else {
					c++;
				}
			}
			System.out.println("");
		}
	}
}
