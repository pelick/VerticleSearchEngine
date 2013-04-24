package dcd.academic.recommend;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dcd.academic.util.StdOutUtil;

public class PageRank {
	private static final double ALPHA = 0.85;
	private static final double DISTANCE = 0.0000001;
	private static final double MUL = 10;
	
	public static int SIZE;
	public static List<List<Double>> s;
	
	PageRank(List<List<Double>> s) {
		this.SIZE = s.get(0).size();
		this.s = s;
	}
	
	public static void doPagerank() {
		List<Double> q = new ArrayList<Double>();
		for (int i = 0; i < SIZE; i ++) {
			q.add(new Random().nextDouble()*MUL);
		}
		System.out.println("初始的向量q为:");
		printVec(q);
		System.out.println("初始的矩阵G为:");
		printMatrix(getG(ALPHA));
		List<Double> pageRank = calPageRank(q, ALPHA);
		System.out.println("PageRank为:");
		printVec(pageRank);
		System.out.println();
	}

	/**
	 * 打印输出一个矩阵
	 * 
	 * @param m
	 */
	public static void printMatrix(List<List<Double>> m) {
		for (int i = 0; i < m.size(); i++) {
			for (int j = 0; j < m.get(i).size(); j++) {
				System.out.print(m.get(i).get(j) + ", ");
			}
			System.out.println();
		}
	}

	/**
	 * 打印输出一个向量
	 * 
	 * @param v
	 */
	public static void printVec(List<Double> v) {
		for (int i = 0; i < v.size(); i++) {
			System.out.print(v.get(i) + ", ");
		}
		System.out.println();
	}

	/**
	 * 获得一个初始的随机向量q
	 * 
	 * @param n
	 *            向量q的维数
	 * @return 一个随机的向量q，每一维是0-5之间的随机数
	 */
	public static List<Double> getInitQ(int n) {
		Random random = new Random();
		List<Double> q = new ArrayList<Double>();
		for (int i = 0; i < n; i++) {
			q.add(new Double(5 * random.nextDouble()));
		}
		return q;
	}

	/**
	 * 计算两个向量的距离
	 * 
	 * @param q1
	 *            第一个向量
	 * @param q2
	 *            第二个向量
	 * @return 它们的距离
	 */
	public static double calDistance(List<Double> q1, List<Double> q2) {
		double sum = 0;

		if (q1.size() != q2.size()) {
			return -1;
		}

		for (int i = 0; i < q1.size(); i++) {
			sum += Math.pow(q1.get(i).doubleValue() - q2.get(i).doubleValue(),
					2);
		}
		return Math.sqrt(sum);
	}

	/**
	 * 计算pagerank
	 * 
	 * @param q1
	 *            初始向量
	 * @param a
	 *            alpha的值
	 * @return pagerank的结果
	 */
	public static List<Double> calPageRank(List<Double> q1, double a) {

		List<List<Double>> g = getG(a);
		List<Double> q = null;
		while (true) {
			q = vectorMulMatrix(g, q1);
			double dis = calDistance(q, q1);
			System.out.println(dis);
			if (dis <= DISTANCE) {
				System.out.println("q1:");
				printVec(q1);
				System.out.println("q:");
				printVec(q);
				break;
			}
			q1 = q;
		}
		return q;
	}

	/**
	 * 计算获得初始的G矩阵
	 * 
	 * @param a
	 *            为alpha的值，0.85
	 * @return 初始矩阵G
	 */
	public static List<List<Double>> getG(double a) {
		List<List<Double>> aS = numberMulMatrix(s, a);
		List<List<Double>> nU = numberMulMatrix(getU(), (1 - a) / SIZE);
		List<List<Double>> g = addMatrix(aS, nU);
		return g;
	}

	/**
	 * 计算一个矩阵乘以一个向量
	 * 
	 * @param m
	 *            一个矩阵
	 * @param v
	 *            一个向量
	 * @return 返回一个新的向量
	 */
	public static List<Double> vectorMulMatrix(List<List<Double>> m,
			List<Double> v) {
		if (m == null || v == null || m.size() <= 0
				|| m.get(0).size() != v.size()) {
			return null;
		}

		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < m.size(); i++) {
			double sum = 0;
			for (int j = 0; j < m.get(i).size(); j++) {
				double temp = m.get(i).get(j).doubleValue()
						* v.get(j).doubleValue();
				sum += temp;
			}
			list.add(sum);
		}

		return list;
	}

	/**
	 * 计算两个矩阵的和
	 * 
	 * @param list1
	 *            第一个矩阵
	 * @param list2
	 *            第二个矩阵
	 * @return 两个矩阵的和
	 */
	public static List<List<Double>> addMatrix(List<List<Double>> list1,
			List<List<Double>> list2) {
		List<List<Double>> list = new ArrayList<List<Double>>();
		if (list1.size() != list2.size() || list1.size() <= 0
				|| list2.size() <= 0) {
			return null;
		}
		for (int i = 0; i < list1.size(); i++) {
			list.add(new ArrayList<Double>());
			for (int j = 0; j < list1.get(i).size(); j++) {
				double temp = list1.get(i).get(j).doubleValue()
						+ list2.get(i).get(j).doubleValue();
				list.get(i).add(new Double(temp));
			}
		}
		return list;
	}

	/**
	 * 计算一个数乘以矩阵
	 * 
	 * @param s
	 *            矩阵s
	 * @param a
	 *            double类型的数
	 * @return 一个新的矩阵
	 */
	public static List<List<Double>> numberMulMatrix(List<List<Double>> s,
			double a) {
		List<List<Double>> list = new ArrayList<List<Double>>();

		for (int i = 0; i < s.size(); i++) {
			list.add(new ArrayList<Double>());
			for (int j = 0; j < s.get(i).size(); j++) {
				double temp = a * s.get(i).get(j).doubleValue();
				list.get(i).add(new Double(temp));
			}
		}
		return list;
	}

	/**
	 * 初始化U矩阵，全1
	 * 
	 * @return U
	 */
	public static List<List<Double>> getU() {
		List<Double> row = new ArrayList<Double>();
		for (int i = 0; i < SIZE; i ++) {
			row.add(new Double(1));
		}

		List<List<Double>> s = new ArrayList<List<Double>>();
		for (int j = 0; j < SIZE; j ++) {
			s.add(row);
		}
		return s;
	}
}
