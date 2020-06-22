package org.sqlToDoc;

/** 
 * Description: ����db�ĵ���bean
 * 
 * @author jiujiya
 * @version 1.0 
 */
public class DbDocBean {
	
	// �ĵ�ģ��
	public String[] styleList = new String[] {};
	// �ĵ�����
	public String projectText = "���ݿ�����ĵ�";
	// �ĵ�·��
	public String pathText = "";
	// ���ݿ�IP
	public String dbIpText = "";
	// �˿ں�
	public String dbPortText = "3306";
	// �û���
	public String dbUserText = "root";
	// ����
	public String dbPsText = "";
	// ���ݿ�����
	public String dbNameText = "";
	// ���ݿ����ӳ�
	public String driverClassName = "com.mysql.jdbc.Driver";
	// ftlģ�����·��
	public String ftlBasePath = "";
	
	/**
	 * @param url ���ݿ�url
	 */
	public void setDbUrl(String url) {
		url = url.substring(13);
		dbIpText = url.split(":")[0];
		url = url.substring(dbIpText.length() + 1);
		dbPortText = url.split("/")[0];
		url = url.substring(dbPortText.length() + 1);
		dbNameText = url.split("\\?")[0];
	}
}
