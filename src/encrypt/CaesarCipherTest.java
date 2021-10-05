package encrypt;
import static org.junit.Assert.*;
import org.junit.Test;

public class CaesarCipherTest {

    @Test
    public void testDefaultConstructor(){

        //act
        var obj = new CaesarCipher();
        //assert
        assertNotNull(obj);
    }

    @Test
    public void testEncrypt(){
        //arrange
        var testString = "test";
        var testString2 = "test";
        //act
        var caesar = new CaesarCipher();
        var newString = caesar.encrypt(testString);

        //assert
        assertNotEquals(testString2,newString);
    }

    @Test
    public void testDecrypt(){
        //arrange
        var testString = "test";
        var testString2 = "test";
        //act
        var caesar = new CaesarCipher();
        var newString = caesar.encrypt(testString);
        var oldString = caesar.decrypt(newString);

        //assert
        assertEquals(testString2,oldString);
    }

}
