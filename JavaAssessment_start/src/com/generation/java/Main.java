package com.generation.java;

import com.generation.model.Course;
import com.generation.model.EnrolledCourse;
import com.generation.model.Student;
import com.generation.service.CourseService;
import com.generation.service.StudentService;
import com.generation.utils.PrinterHelper;

import java.text.ParseException;
import java.util.*;

public class Main
{

    public static void main( String[] args )
        throws ParseException
    {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        Scanner scanner = new Scanner( System.in );
        int option;
        do
        {
            try{
            PrinterHelper.showMainMenu();
            option = scanner.nextInt();
            switch ( option )
            {
                case 1:
                    registerStudent( studentService, scanner );
                    break;
                case 2:
                    findStudent( studentService, scanner );
                    break;
                case 3:
                    gradeStudent( studentService, scanner );
                    break;
                case 4:
                    enrollStudentToCourse( studentService, courseService, scanner );
                    break;
                case 5:
                    showStudentsSummary( studentService, scanner );
                    break;
                case 6:
                    showCoursesSummary( courseService, scanner );
                    break;
                case 7:
                    showPassedCourses( studentService, scanner );
                    break;
                case 8:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number between 1 and 8.");
                    break;
            }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                option = 0;
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                option = 0;

            }
        }
        while ( option != 8 );
    }

    private static void enrollStudentToCourse( StudentService studentService, CourseService courseService,
                                               Scanner scanner )
    {
        System.out.println( "Insert student ID" );
        String studentId = scanner.next();
        Student student = studentService.findStudent( studentId );
        if ( student == null )
        {
            System.out.println( "Invalid Student ID" );
            return;
        }
        System.out.println( student );
        System.out.println( "Insert course ID" );
        String courseId = scanner.next();
        Course course = courseService.getCourse( courseId );
        if ( course == null )
        {
            System.out.println( "Invalid Course ID" );
            return;
        }
        System.out.println( course );
        studentService.enrollToCourse( studentId, course );
        System.out.println( "Student with ID: " + studentId + " enrolled successfully to " + courseId );

    }

    private static void showCoursesSummary( CourseService courseService, Scanner scanner )
    {
       courseService.showSummary();
    }

    private static void showStudentsSummary( StudentService studentService, Scanner scanner )
    {
        studentService.showSummary();
    }

    private static void gradeStudent( StudentService studentService, Scanner scanner )
    {
        Student student = getStudentInformation( studentService, scanner );
        System.out.println( "Enrolled course:" );

        //TODO Loop through the student enrolled courses, and use the scanner object to get the course ID to insert
        // the course grade
        if (student != null) {
            System.out.println("Enrolled courses:");
            List<EnrolledCourse> enrolledCourses =
                    (List<EnrolledCourse>) student.getEnrolledCourses();
            if (enrolledCourses.isEmpty()) {
                System.out.println("No courses enrolled yet.");
            } else {
                for (EnrolledCourse enrolledCourse : enrolledCourses) {
                    System.out.println(enrolledCourse);
                    System.out.println("Enter grade (1-6):");
                    int grade = scanner.nextInt();
                    if (grade < 1 || grade > 6) {
                        System.out.println("Invalid grade. Please enter a value between 1 and 6.");
                    } else {
                        enrolledCourse.setGrade(grade);
                        System.out.println("Grade for course " + enrolledCourse.getCode() +
                                " set to " + enrolledCourse.getGrade());
                    }
                }
            }
        }
    }

    private static Student getStudentInformation( StudentService studentService, Scanner scanner )
    {
        System.out.println( "Enter student ID: " );
        String studentId = scanner.next();
        Student student = studentService.findStudent( studentId );
        if ( student == null )
        {
            System.out.println( "Student not found" );
        }
        return student;
    }

    private static void findStudent( StudentService studentService, Scanner scanner )
    {
        Student student = getStudentInformation( studentService, scanner );
        if ( student != null )
        {
            System.out.println( "Student Found: " );
            System.out.println( student );
        }
    }

    private static void registerStudent( StudentService studentService, Scanner scanner ) throws ParseException
    {
        Student student = PrinterHelper.createStudentMenu( scanner );
        studentService.registerStudent( student );
    }

    private static void showPassedCourses(StudentService studentService, Scanner scanner ) {
        //TODO Loop through the student enrolled courses, and show all the passed courses
        Student student = getStudentInformation(studentService, scanner);

        if (student == null) {
            return;
        }
        List<EnrolledCourse> enrolledCourses = (List<EnrolledCourse>) student.getEnrolledCourses();
        if (enrolledCourses.isEmpty()) {
            System.out.println("The student has no enrolled courses.");
            return;
        }
        System.out.println("Passed courses:");
        for (EnrolledCourse enrolledCourse : enrolledCourses) {
            if (enrolledCourse.getGrade() >= 60) {
                Course course = enrolledCourse;
                System.out.println(course.getCode() + " - " + course.getName());

            }

        }
    }
}
