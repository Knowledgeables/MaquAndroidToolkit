package im.maqu.matk.utils;


import java.io.FileInputStream;
import java.security.MessageDigest;

import im.maqu.matk.constant.CstCharset;
import im.maqu.matk.constant.CstCrypt;


public class UtilMD5 {
	/**
     * 获取字符串MD5值
     * @param string
     * @return
     */
    public static String getStringMD5(String string) {
        return getStringMD5(string, CstCharset.UTF_8);
    }

    /**
     * 获取字符串MD5值
     * @param string
     * @param charset
     * @return
     */
    public static String getStringMD5(String string, String charset) {
        String md5Str = null;

        if(string != null) {
            try {
                byte[] bytes = MessageDigest.getInstance(CstCrypt.MD5).digest(
                        string.getBytes(charset));
                md5Str = UtilNumBase.bytesToHexString(bytes);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return UtilString.nullToEmpty(md5Str);
    }

	/**
	 * 获取文件获取md5值
	 * @param path 文件路径
	 * @return
	 */
	public static String getFileMD5(String path) {
        String md5Str = null;

		if(UtilFile.isFileExist(path)) {
            FileInputStream in = null;
            try {
                MessageDigest md5 = MessageDigest.getInstance(CstCrypt.MD5);
                in = new FileInputStream(path);
                byte[] buffer = new byte[8192];
                int length = 0;
                while ((length = in.read(buffer)) != -1) {
                    md5.update(buffer, 0, length);
                }

                md5Str = UtilNumBase.bytesToHexString(md5.digest());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                UtilStream.closeInputStream(in);
            }
        }

        return UtilString.nullToEmpty(md5Str);
    }


}
