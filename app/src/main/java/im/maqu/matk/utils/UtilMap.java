/*

 */
package im.maqu.matk.utils;

import java.util.Map;

public class UtilMap {
    /**
     * 判定map是否为空
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static<K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

    /**
     * 判定map是否为非空
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static<K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    /**
     * 获取map的数量
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static<K, V> int getCount(Map<K, V> map) {
        return map == null ? 0 : map.size();
    }
}
