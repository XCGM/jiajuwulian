package com.xcgmym.jiajuwulian;

public class Install
{
	public static void main(String[] arg)
	{
		ShuJuKu sjk = new ShuJuKu();

		sjk.execute("CREATE TABLE jiedian(bianhao int NOT NULL, mingcheng text, chuchangriqi text, gujian text)");
		sjk.execute("CREATE TABLE shebei(bianhao int NOT NULL, jiedianid int, mingcheng text, chuchangriqi text, bangdingriqi text, leixing text, gujian text)");
		sjk.execute("CREATE TABLE yonoghu(bianhao int NOT NULL, mingcheng text, password text)");
		sjk.execute("CREATE TABLE yonghujiedian(bianhao int NOT NULL, yonghuid int, jiedianid int, bangdingriqi text)");

		sjk.close();
	}
}
