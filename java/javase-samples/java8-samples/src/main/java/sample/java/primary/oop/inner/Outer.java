//package sample.java.primary.oop.inner;
//
///*
//内部类定义在局部时，
//1，不可以被成员修饰符修饰
//2，可以直接访问外部类中的成员，因为还持有外部类中的引用。
//	但是不可以访问它所在的局部中的变量。只能访问被final修饰的局部变量。
//*/
//class Outer2 {
//	int x = 3;
//
//	void method(final int a) {
//		final int y = 4;
//		class Inner {
//			void function() {
//				System.out.println(x);
//				System.out.println(y);
//			}
//		}
//
//		new Inner().function();
//
//	}
//}
//
//public class InnerClassDemo {
//	public static void main(String[] args) {
//		Outer out = new Outer();
//		out.method(6);
//	}
//}
