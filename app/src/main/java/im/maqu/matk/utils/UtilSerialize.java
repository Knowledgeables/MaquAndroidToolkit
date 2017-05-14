/*

 *
@author : Maqu
 */
package im.maqu.matk.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author : Maqu
 * @date   : 2013-9-19
 * @desc   : 序列化/反序列化工具类
 * */
public class UtilSerialize {

    /**
     * 从文件中反序列化为对象
     * 
     * @param filePath
     * @return
     * @throws
     */
    public static Object deserialization(String filePath) {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(filePath));
            Object o = in.readObject();
            in.close();
            return o;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ClassNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            UtilStream.closeInputStream(in);
        }
    }

    /**
     * 把对象序列化到文件
     * 
     * @param filePath
     * @param obj
     * @return
     * @throws
     */
    public static void serialization(String filePath, Object obj) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(obj);
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            UtilStream.closeOutputStream(out);
        }
    }
}
