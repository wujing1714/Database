package test;

import java.util.List;

import method.Analysis;
import method.Util;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String string=Util.getUrl();
		Analysis analysis=new Analysis();
		analysis.analysis(string);
	}

}
