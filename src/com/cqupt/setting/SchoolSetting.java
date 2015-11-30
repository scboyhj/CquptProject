package com.cqupt.setting;

import java.util.ArrayList;
import java.util.List;

public final class SchoolSetting {

	private static List<String> institutes = new ArrayList<String>();
	static {
		institutes.add("软件学院");
		institutes.add("计算机学院");
		institutes.add("传媒学院");
		institutes.add("通信学院");
	}
	private static List<String> kindOfCourse = new ArrayList<String>();
	static {
		kindOfCourse.add("必修");
		kindOfCourse.add("限选");
		kindOfCourse.add("学院任选");
		kindOfCourse.add("全校任选");
	}

	public static int findInstitutesPositionByName(String name) {

		for (int i = 0; i < institutes.size(); i++) {
			String str = institutes.get(i);
			if (str.equals(name)) {
				return i;
			}
		}

		return 0;
	}

	public static int findKindOfCoursePositionByName(String name) {

		for (int i = 0; i < kindOfCourse.size(); i++) {
			String str = kindOfCourse.get(i);
			if (str.equals(name)) {
				return i;
			}
		}

		return 0;
	}

	public static List<String> getInstitutes() {
		return institutes;
	}

	public static List<String> getkindOfCourse() {
		return kindOfCourse;
	}
}
