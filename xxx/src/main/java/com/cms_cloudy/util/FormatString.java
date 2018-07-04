package com.cms_cloudy.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class FormatString {
	public static void main(String[] args) {
		List<String> l1 = new ArrayList<String>();
		l1.add("Q1");
		l1.add("Q3");
		System.out.println(numberToString(l1));
		List<String> l2 = new ArrayList<String>();
		l2.add("Q1");
		l2.add("Q3");
		l2.add("Q5");
		System.out.println(numberToString(l2));
		List<String> l3 = new ArrayList<String>();
		l3.add("Q1");
		l3.add("Q3");
		l3.add("Q5");
		l3.add("Q6");
		l3.add("Q7");
		l3.add("Q8");
		System.out.println(numberToString(l3));
		List<String> l4 = new ArrayList<String>();
		l4.add("Q1");
		l4.add("Q11");
		l4.add("Q1");
		l4.add("Q2");
		l4.add("Q3");
		l4.add("Q5");
		l4.add("Q6");
		l4.add("Q7");
		l4.add("Q8");
		l4.add("Q12");
		l4.add("Q13");
		l4.add("Q15");
		System.out.println(numberToString(l4));
	}

	public static String numberToString(List<String> references) {
		// 位号中字母相同的位号
		if (references.size() == 0) {
			return null;
		}
		String reference = references.get(0);
		int index = 0;// 记录数字开始的位置
		for (int i = 0; i < reference.length(); i++) {
			char c = reference.charAt(i);
			if (Character.isDigit(c)) {
				index = i;
				break;
			}
		}
		final int in = index;
		Collections.sort(references, new Comparator<String>() {
			public int compare(String o1, String o2) {
				int pnum1 = 0;
				int pnum2 = 0;
				if (!StringUtils.isEmpty(o1)) {
					pnum1 = Integer.valueOf(o1.substring(in, o1.length()));
				}
				if (!StringUtils.isEmpty(o2)) {
					pnum2 = Integer.valueOf(o2.substring(in, o2.length()));
				}
				return pnum1 - pnum2;
			}
		});
		reference = references.get(0);
		String prefix = reference.substring(0, index);// 记录位号的字母前缀
		Integer suffix = Integer.valueOf(reference.substring(index, reference.length()));// 最小的后缀数字
		Integer lastS = suffix;
		List<String> sl = new ArrayList<String>();// 记录生成好的位号
		for (int i = 1; i < references.size(); i++) {
			String r = references.get(i);
			String s = r.substring(index, r.length());
			if (Integer.valueOf(s) - 1 == lastS) {// 此时的后缀是连续的
				if (i == references.size() - 1) {// 最后一个元素
					String su = prefix + suffix + "-" + references.get(i);
					sl.add(su);
				}
				lastS = Integer.valueOf(s);
			} else {// 重新记录最小值，前面连续的数字生成相关字符串
				if (Integer.valueOf(references.get(i - 1).substring(index, references.get(i - 1).length())) == suffix) {
					sl.add(references.get(i - 1));
				} else {
					String su = prefix + suffix + "-" + references.get(i - 1);
					sl.add(su);
				}
				suffix = Integer.valueOf(s);
				lastS = Integer.valueOf(s);
				if (i == references.size() - 1) {// 最后一个元素
					sl.add(r);
				}
			}

		}
		if (references.size() == 1) {
			sl.add(reference);
		}
		return sl.toString().replaceAll("\\[", "").replaceAll("\\]", "");
	}
}
