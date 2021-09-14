package javaTest;

import static org.junit.Assert.*;

import org.junit.Test;

//** Book class
// => 맴버필드 3개 정의, 맴버필드 3개를 초기화하는 생성자를 만드세요 ~

class Book {
	String title;
	String author;
	int price;
	
	Book(String title, String author, int price) {
		this.title=title;
		this.author=author;
		this.price=price;
	} //생성자
	
	public boolean isBook(boolean b) {
		return b;
	} //isBook
} //Book

//** @ 종류
//=> @Before - @Test - @After
//=> @ 적용 메서드 : non static, void 로 정의 해야 함.

//** org.junit.Assert 가 지원하는 다양한 Test용 Method 
//1) assertEquals(a,b) : a와 b의 값(Value) 이 같은지 확인
//2) assertSame(a,b) : 객체 a와b가 같은 객체임(같은 주소) 을 확인
//3) assertTrue(a) : a가 참인지 확인
//4) assertNotNull(a) : a객체가 Null 이 아님을 확인
//5) assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인

public class ex01_BookTest {
	
	// 1) assertEquals(a,b)
	public void equalsTest() {
		Book b1 = new Book("죄 와 벌","톨스토이",99000);
		//assertEquals(b1.author,"톨스토이");
		assertEquals(b1.author,"홍길동");
		// True: 성공(그린라인) , False: 실패(오류, 레드라인)
	}
	// 2) assertSame(a,b) 
	public void sameTest() {
		Book b1 = new Book("죄 와 벌","톨스토이",99000);
		Book b2 = new Book("죄 와 벌","톨스토이",99000);
		Book b3 = new Book("홍길동전","허균",99000);
		//assertSame(b1,b2);  // false -> red
		b3=b2;
		assertSame(b3,b2); // true -> green
	}	
	// 3) assertTrue(a) 
	public void trueTest() {
		Book b1 = new Book("죄 와 벌","톨스토이",99000);
		assertTrue(b1.isBook(false)) ;  // red
	}	
	// 4) assertNotNull(a)
	public void notNullTest() {
		Book b1 = new Book("죄 와 벌","톨스토이",99000);
		Book b2 = null;
		assertNotNull(b1); // green
		//assertNotNull(b2); // red
	}
	@Test
	// 5) assertArrayEquals(a,b)
	public void arrayEqualsTest() {
		String[] a1 = new String[] {"가","나","다"};
		String[] a2 = new String[] {"가","나","다"};
		String[] a3 = new String[] {"가","다","나"};
		String[] a4 = new String[] {"가","다","라"};
		// 5.1) 두배열의 순서, 값 모두 동일 (a1, a2)
		//assertArrayEquals(a1, a2); // green
		// 5.2) 두배열의 순서는 다르고, 값 모두 동일 (a1, a3)
		//assertArrayEquals(a1, a3); // red 
		// 5.3) 모두 다른경우 (a1, a4) 
		assertArrayEquals(a1, a4); // red 
	}

} //BookTest
