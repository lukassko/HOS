package com.app.hos.tests.units;

import org.junit.Test;

public class TestClass {

	@Test
	public void test () {
		B b = new B();
		A b2a = (A)b;
		b2a.print();
	}
	
	class A {
		public void print() {
			System.out.println("Class A");
		}
	}
	
	class B extends A  {
		public void print() {
			System.out.println("Class B");
		}
	}
}
