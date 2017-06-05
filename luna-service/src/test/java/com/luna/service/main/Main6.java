/**
 * COPYRIGHT@LAULYL
 */
package com.luna.service.main;

import java.io.StringReader;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * @author laulyl
 * @date 2017年6月5日 下午10:26:04
 * @description
 */
public class Main6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getSeggetText("*:*", true));

	}
	
	public static String getSeggetText(String text, boolean useSmart) {
		StringBuilder result = new StringBuilder();
		result.append("(");
		IKSegmenter ik = new IKSegmenter(new StringReader(text), useSmart);
		try {
			Lexeme word = null;
			while ((word = ik.next()) != null) {
				result.append(word.getLexemeText()).append(" ");
			}
			result.append(")");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return result.toString();
	}

}
