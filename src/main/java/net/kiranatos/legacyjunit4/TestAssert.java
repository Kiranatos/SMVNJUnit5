package net.kiranatos.legacyjunit4;

public class TestAssert {    
    public static void main(String[] args) {
        /*
        По умолчанию assert-ы отключены в JVM.
        Чтобы их включить в классах:    java -ea TestAssert
                                        java -enableasserions TestAssert
        Выключить:  java -da TestAssert
                    java -disableasserions TestAssert
        Включить для всего package и подпакетов:    java -ea com.kiranatos...
        Включить для всего package, кроме пакета:   java -ea -da:com.kiranatos.test...
        Включить для проекта в IDE: прописать -ea в NetBeans
        "Выполнить->Установить конфигурацию проекта->Настроить->Выполнение->Параметры VM"
        */
        int x = -10;
        assert x>=0 : "Test"; //Выброс исключения AssertionError со значением
        assert x>=0 : x; //Выброс исключения AssertionError со значением
        assert x>=0; //Выброс исключения AssertionError без сообщения
        AssertionError : assert x>=0 : "Моё сообщение x >= 0";
	}
}
