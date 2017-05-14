/*

 */

package im.maqu.matk.utils;


public class UtilArray {
	/**
	 * 判断数组数据是否为空
	 * @param array
	 * @return
	 */
	public static<T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断数组数据是否为非空
	 * @param array
	 * @return
	 */
	public static<T> boolean isNotEmpty(T[] array) {
		return !isEmpty(array);
	} 
	
	
	/**
	 * 获取数组数据长度
	 * @param array
	 * @return
	 */
	public static<T> int getCount(T[] array) {
		return array == null ? 0 : array.length;
	} 
	
}
