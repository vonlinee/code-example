package code.example.java.primary.oop.inherit;

public class Child extends Parent {
	public Child() {
		super.set1(10);
		System.out.println(this.object == super.object);
	}
}