package net.kiranatos.legacyjunit4;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

/* Eclipse: Оскільки це легасі-код, то вибрав RunAs>RunConfiguration>JUnit>НазваКласу>Test>TestRunner>JUnit 4
 * Використана залежність junit-vintage-engine, тому імпорти співпадають з JUnit 4 :
 *  - статичний імпорт import static org.junit.Assert.*; дозволяє писати assertEquals без назви класа Assert.
 *   - по конвенції назви методів мають бути "shouldНазвание" */

public class MainTest {
	
	public MainTest() {	}
	
	/*************************************************************************************/        
    //методы с аннотацией @Before будут выполнятся перед каждым методом с @Test
    @Before
    public void runT1() {}
    //методы с аннотацией @BeforeClass выполятся один раз перед выполнением методом с @Test
    @BeforeClass
    public static void runT2() {}
    /* @After - соответсвенно после каждого тестового случая
     * @AfterClass - соответсвенно один раз после всех
     * @AfterClass, @BeforeClass - должны быть статические!!!     */
    
    /*************************************************************************************/

	@Test
	public void shouldAdd() {
		Assert.assertEquals(5, Main.add(2, 3)); // 2+3 = 5
	}
	
	/*************************************************************************************/
    
    @Test
    public void testAdd() {
        double res = Main.add(3,7);
        if (res != 10 ) Assert.fail();  // тест сфейлился
        Assert.assertTrue(res == 10);   // проверяет, является ли результат выражения верным
        //Assert.assertFalse(res == 10);  // 
        //Assert.assertNull(res);         // результатом выражения есть null
        Assert.assertNotNull(res);    // результат выражения отличен от null
        Assert.assertEquals(10, res, 0); // ожидаемый результат и полученный результат совпадают
        //Assert.assertSame(exp, this); // ожидаемый и полученный объекты это один и тот же объект
        //Assert.fail(message); // метод генерирует исключение AssertionError - добавляем туда, куда не должен дойти ход выполнения программы
    }
    
    /*************************************************************************************/
    
    @Ignore  //тест будет проигнорирован или просто удалите аннотацию @Test
    @Test
    public void testIgnore() { }
    
    /*************************************************************************************/
    
    @Test(expected = ArrayIndexOutOfBoundsException.class) /* тестирование возможного выброса исключения */
    public void shouldThrowException1() {
        Main.get(11);  // сделать так, чтобы выбросило исключение
    }    
    
    /* Второй вариант указания исключения: */    
    @Rule
    public final ExpectedException exp = ExpectedException.none();
    //писать не нужно: @Test(expected = ArrayIndexOutOfBoundsException.class)
    @Test
    public void shouldThrowException2() {
        exp.expect(ArrayIndexOutOfBoundsException.class);
        Main.get(11);  
    }
    
    /*************************************************************************************/    
    /* Первый способ указания времени, для конкретного теста:*/
    @Test(timeout = 1000) /* 1000ms = 1s  Если тест работает дольше указанного времени, то тест не пройден */
    public void testN() { while(true) {} }
    
    /*Другой способ указать ограниченное время для всех тестов: */
    @Rule
    public Timeout time = new Timeout(1000, TimeUnit.MILLISECONDS);        
}

/* http://www.javenue.info/post/19 Тестирование Java кода с помощью JUnit 2006-01-02 Туториалы по программированию

В нашем современном мире IDE умеют находить и просто запускать тесты в проекте. Но что делать, если вы хотите запустить 
их вручную с помощью программного кода. Для этого можно воспользоваться Runner'ом. 
Бывают текстовый - junit.textui.TestRunner, графические версии - junit.swingui.TestRunner, junit.awtui.TestRunner.
Но чуть более современный метод - это использование класса JUnitCore. Добавьте следующий метод main в класс MathFuncTest:

    public static void main(String[] args) throws Exception {
        JUnitCore runner = new JUnitCore();
        Result result = runner.run(MathFuncTest.class);
        System.out.println("run tests: " + result.getRunCount());
        System.out.println("failed tests: " + result.getFailureCount());
        System.out.println("ignored tests: " + result.getIgnoreCount());
        System.out.println("success: " + result.wasSuccessful());
    }

И результат выполнения:

    run tests: 3
    failed tests: 0
    ignored tests: 1
    success: true

В более ранних версиях JUnit для написания тестового класса нужно было создать наследника 
junit.framework.TestCase. Затем необходимо было определить конструктор, принимающий в качестве 
параметра String - название метода - и передать его родительскому классу. Каждый тестовый метод 
обязан был начинаться с префикса test. Для инициализации и освобождения ресурсов использовались 
методы setUp и tearDown. Короче ужас. Ну а сейчас все просто, да. */