package sample.spring.aop.xml.passingparams;

public class ThinkerImpl implements Thinker {
	private String thoughts;

	public void thinkOfSomething(String thoughts) {
		this.thoughts = thoughts;
		int i = 1 / 0;
		System.out.println("thinkOfSomething executed");
	}

	public String getThoughts() {
		return thoughts;
	}
}
