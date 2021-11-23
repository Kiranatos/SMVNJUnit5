package net.kiranatos.examples;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

/* Это не работает в NetBeans (перешел на Эклипс -> дальнейшие отличия не проверял):
    - все названия методов должны начинаться с test иначе не запустятся.
    - .PER_METHOD, .PER_CLASS никак не влияет. Создается одна сущность для всех 
методовов, хотя по дефолту должна быть разная. Static methods не запускаются.
    - Абсолютно все методы независимо от аннотаций, запускаются в разнобой, а 
должны хаотично вызываться только методы с @Test
    - @DisplayName, @Disabled тоже не работает    */

@TestInstance(TestInstance.Lifecycle.PER_METHOD) /*One class instance create for
all methods. Methods with BeforeAll and AfterAll can be non-static*/
/* @TestInstance(TestInstance.Lifecycle.PER_METHOD) - by default. Different 
class instances create for different methods. Methods with BeforeAll and AfterAll must be static*/
public class MathOperationsTest {
    String str1 = "(non-static filed)";
    static String str2 = "(static filed)";
    TestInfo testInfo;
    TestReporter testReporter;
        
    public MathOperationsTest() {
        System.out.println("JUnit constructor");
    }
    
    @BeforeAll
    public static void testSetUpClass() {
        System.out.println("\nJUnit method with BeforeAll annotation\n");
    }
    
    @AfterAll
    public static void testTearDownClass() {
        System.out.println("\nJUnit method with AfterAll annotation\n");
    }
    
    
    @BeforeEach
    public void testSetUp(TestInfo testInfo, TestReporter testReporter) { // це інтерфейси, використовують Dependency Injection
        System.out.println("\nJUnit method with BeforeEach annotation; this=" + this);
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        testReporter.publishEntry("Running " + testInfo.getDisplayName() + " with tags " + testInfo.getTags());
    }
    
    @AfterEach
    public void testTearDown() {
        System.out.println("JUnit method with AfterEach annotation; this=" + this + "\n");        
    }

    
    
    /**
     * Test of add method, of class MathOperations.
     */
    @Test
    public void testAdd_intArr() {
        System.out.println("JUnit method1 with Test annotation -> static method MathOperations.add; this=" + this);
        int expResult = 30;
        int result = MathOperations.add(10, 10, 10);
        assertEquals(expResult, result);
        assertAll(
        		() -> assertEquals(expResult, result),
        		() -> assertEquals(expResult, result),
        		() -> assertEquals(expResult, result)
        		);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class MathOperations.
     */
    @Test
    public void testAdd_int() {
        System.out.println("JUnit method2 with Test annotation -> non-static method mathOperations.add; this=" + this);
        MathOperations instance = new MathOperations(100);
        MathOperations expResult = new MathOperations(1100);
        MathOperations result = instance.add(1000);
        //assertEquals(expResult, result);
        assertEquals(expResult.toString(), result.toString());
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getResult method, of class MathOperations.
     */
    @Test
    @DisplayName("Название теста в консоле (это можно поцепить и на класс) должно быть изменено аннотацией, в NetBeans не работает")
    public void testGetResult() {
        System.out.println("JUnit method3 with Test annotation -> non-static method mathOperations..getResult; this=" + this);
        MathOperations instance = new MathOperations(999);        
        int result = instance.getResult();
        assertEquals(999, result);        
        //fail("The test case is a prototype.");
    }    
    
    @Test
    public void testGetString() {
        System.out.println("JUnit method4 with Test annotation -> non-static method mathOperations.getString; this=" + this);        
        MathOperations instance = new MathOperations(999);
        assertEquals("прапор", instance.getString("пра"));        
        //fail("The test case is a prototype.");
    }
    
    // Exceptions:
    @Test
    public static void testGetExceptions() {
        System.out.println("JUnit method5 with Test annotation -> static method MathOperations.getExceptions");        
        assertThrows(ArithmeticException.class, () -> MathOperations.getExceptions("ArithmeticException"), "Сообщение об ошибке ArithmeticException");
        assertThrows(NullPointerException.class, () -> MathOperations.getExceptions("NullPointerException"), "Сообщение об ошибке NullPointerException");
    }
    
    @Test
    @Disabled // Отключает метод, чтобы не проверяло
    public void testDisabled() {
        System.out.println("JUnit method6 with Test annotation; this=" + this);                
        fail(" все упало ");
    }
        
    @Test 
    // Condition Execution: 
    @EnabledOnOs(OS.LINUX)	// запускать данный тест, только на Линукс
    @EnabledOnJre(JRE.JAVA_8) // запускать данный тест, только на 8-й джаве    
    //@EnabledIfSystemProperty(matches = "", named = "") // не знаю, что делает это условие
    //@EnabledIfEnvironmentVariable(matches = "", named = "") // не знаю, что делает это условие
    public void test_Fail() {
    	boolean isServerUp = true;    	
    	assumeFalse(isServerUp);
    	assumeTrue(!isServerUp); /*Assumptions Condition Execution, не путать с Assertion-ами, похоже на аннотации @EnabledOnOs, @Disabled
    	блокирует выполнение метода, если не выполнено some условие (Напр.Сервер не start)*/
    	
    	String str = ". It is Lazy init. ";
        System.out.println("JUnit test: demonstration of messages in fail cases");
        assertEquals(20, MathOperations.add(10, 10, 10), "повідомлення із assertEquals #1"); // Демонстарция того, як отображаются комменты при падении теста.
        assertEquals(30, MathOperations.add(10, 10, 10), "повідомлення із assertEquals #2 appears when test fail");
        assertEquals(10, MathOperations.add(10, 10, 10), () -> "повідомлення із assertEquals #3. Через лямбду" + str + "Appears when test fail");
        
        fail("повідомлення методу .fail()");
    }
    
    // Можно сгрупировать методы в Nested class
    @Nested
    class NestedClassExample{
    	
    	@Test
        @DisplayName("метод1 внутри Nested Class")
        public void testGetResult1() {            
            MathOperations instance = new MathOperations(999);        
            int result = instance.getResult();
            assertEquals(999, result);        
    	}    	
    	
    	@Test
        @DisplayName("метод2 внутри Nested Class")
        public void testGetResult2() {            
            MathOperations instance = new MathOperations(999);        
            int result = instance.getResult();
            assertEquals(999, result);        
        }
    }
    
    @Tag("useless") //чтобы эта аннотация влияла на код, нужно в Run Configuration>JUnit>Test>Include and exclude tags выставить необходимые теги
    @RepeatedTest(3)
    public void test(RepetitionInfo repInfo) {
    	System.out.println("Повторяющийся метод. " + repInfo.getCurrentRepetition() + " из " + repInfo.getTotalRepetitions());
    	repInfo.getCurrentRepetition();
    	assertEquals(20, MathOperations.add(5, 5, 10));
    }	 
}