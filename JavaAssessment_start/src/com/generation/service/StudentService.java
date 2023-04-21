package com.generation.service;

import com.generation.model.Course;
import com.generation.model.EnrolledCourse;
import com.generation.model.Student;

import java.util.HashMap;

public class StudentService
{
    private final HashMap<String, Student> students = new HashMap<>();

    public void registerStudent( Student student )
    {
        //TODO Add new student to the students hashmap
        if (!students.containsKey(student.getId())) {
            this.students.put(student.getId(), student);
        }

    }

    public Student findStudent( String studentId )
    {
        //TODO Find the student from the Hashmap with the student id
        if (students.containsKey(studentId)) {
            return students.get(studentId);
        }
        return null;
    }

    public void enrollToCourse( String studentId, Course course )
    {
        //TODO check if students hashmap contains the studentsId, if not enroll student to the course
        Student student = findStudent(studentId);
        if (student != null) {
            if (!student.getEnrolledCourses().containsKey(course.getCode())) {
                EnrolledCourse enrolledCourse = new EnrolledCourse(course, 0.0);
                student.getEnrolledCourses().put(course.getCode(), enrolledCourse);
            }
        }

    }

    public void showSummary()
    {
        //TODO Loop through students hashmap and print out students' details including the enrolled courses
        for (Student student : students.values()) {
            System.out.println(student.toString());
            for (EnrolledCourse enrolledCourse : student.getEnrolledCourses().values()) {
                System.out.println(enrolledCourse.toString());
            }
        }
    }

    public HashMap<String, EnrolledCourse> enrolledCourses(Student student)
    {
        //TODO return a HashMap of all the enrolledCourses
        if (student.getEnrolledCourses().isEmpty()) {
        return null;
        }
        return student.getEnrolledCourses();
    }

    public String findEnrolledCourse(Student student, String courseId )
    {
        //TODO return the course enrolled by the student from the course Id
        EnrolledCourse enrolledCourse = student.getEnrolledCourses().get(courseId);
        if (enrolledCourse != null) {
            return enrolledCourse.getCode();
        }

        return null;
    }

    public void gradeStudent(Student student, Course course, double grade) {
        student.gradeCourse(course.getCode(), grade);
    }



    public HashMap<String, EnrolledCourse> getPassedCourses(Student student) {
        return student.findPassedCourses();
    }
}
