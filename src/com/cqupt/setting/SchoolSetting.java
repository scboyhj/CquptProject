package com.cqupt.setting;

import java.util.ArrayList;
import java.util.List;

public final class SchoolSetting {

	private static List<String> institutes = new ArrayList<String>();
	static {
		institutes.add("���ѧԺ");
		institutes.add("�����ѧԺ");
		institutes.add("��ýѧԺ");
		institutes.add("ͨ��ѧԺ");
	}
	private static List<String> kindOfCourse = new ArrayList<String>();
	static {
		kindOfCourse.add("����");
		kindOfCourse.add("��ѡ");
		kindOfCourse.add("ѧԺ��ѡ");
		kindOfCourse.add("ȫУ��ѡ");
	}

	public static List<String> getInstitutes() {
		return institutes;
	}

	public static List<String> getkindOfCourse() {
		return kindOfCourse;
	}
}
