package junitTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

// ** 클래스 Car 작성
// => 필드 3개   :  String name, String  color,  int price  
// => 생성자 1개 :  위 3개의 필드를 초기화 한다 	

class Car {
	String name;
	String color;
	int price;

	Car(String name, String color, int price) {
		this.name = name;
		this.color = color;
		this.price = price;
	}// 생성자
}// car

public class CarTest {
	
	@Test
	// 2.1) testMethod1 
	// => 위 Car 의 인스턴스를 c1, c2 각각 2개 생성
	// => color 의 값의 동일성을 확인하는 테스트 
	public void testMethod1() {
	    Car c1 = new Car("아반떼","White",1800);
	    Car c2 = new Car("K3","White",1900);
	    //assertEquals(c1.color,"White"); //True(Green)
	    //assertEquals(c2.color,"White"); //True(Green)
	    assertEquals(c1.color,"Black"); //False(Red)
	    assertEquals(c2.color,"Red"); //False(Red)
	}
	
	@Test
	// 2.2) testMethod2
	// => 위 Car 의 인스턴스를 각각 c1, c2 라는 이름으로 2개 생성 
	// => 두 인스턴스의 동일성 을 확인하는 테스트
	public void testMethod2() {
		//False(RedLine)
		//Car c1 = new Car("아반떼","White",1800);
	    //Car c2 = new Car("K3","White",1900);
	    //assertSame(c1,c2);
	    
		//True(GreenLine)
	    Car c1 = new Car("아반떼","White",1800);
	  	Car c2 = new Car("아반떼","White",1800);
	  	c1=c2;
	  	assertSame(c1,c2);    
	}
	
	@Test
	// 2.3) testMethod3
	// => 데이터를 각각 2개씩 가진 Car Type 의 배열을 2개 정의
	// => 이 두 배열의 동일성을확인하는 테스트
	public void testMethod3() {
		Car c1 = new Car("아반떼","White",1800);
		Car c2 = new Car("K5","White",2500);
		Car c3 = new Car("아반떼","White",1800);
		Car c4 = new Car("K5","White",2500);
		
		Car[] a1 = new Car [] {c1,c2}; 
		Car[] a2 = new Car [] {c3,c4};
		assertArrayEquals(a1, a2);
	}

}//class
