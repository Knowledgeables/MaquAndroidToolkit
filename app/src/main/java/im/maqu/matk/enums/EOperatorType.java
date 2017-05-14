/*

 */

package im.maqu.matk.enums;

/**
 * @author : Maqu
 * @date   : 2014-3-17
 * @desc   : 网络运营商类型
 */
public enum EOperatorType {
	//未知运营商
	OPERATOR_UNKNOWN("UNKNOWN"),
	//移动
	OPERATOR_CMCC("CMCC"),
	//电信
	OPERATOR_CTC("CTC"),
	//联通
	OPERATOR_CUC("CUC");
	
	private String mValue;
	
	EOperatorType(String value) {
		this.mValue = value;
	}
	
	public String getValue() {
		return this.mValue;
	}
}
