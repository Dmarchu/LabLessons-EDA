package iinf2.otros.percentil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Percentil {
    private static final String reset = "\u001B[0m";
    private static final String red = "\u001B[31m";
    private static final String green = "\u001B[32m";

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Double> grades = new ArrayList<>();
        System.out.print("Introduzca su nota: ");
        Scanner kbd = new Scanner(System.in); // 7.14
        double media = 0, numporencima = 0, myGrade = Double.parseDouble(kbd.nextLine());
        System.out.print("Introduzca en nombre del fichero txt de sus datos: ");
        String filename = kbd.nextLine();
        Scanner in = new Scanner(new File("C:\\Users\\david\\Desktop\\Poli[Archivos]\\2º IINF (2024-2025)\\2o Cuatrimestre\\Pruebas\\src\\iinf2\\otros\\percentil\\" + filename + ".txt"));
        System.out.println();
        while (in.hasNextLine()) {
            String grade = "0";
            try { grade = in.nextLine().split("\t")[1].replace(",", ".");
            } catch (Exception ignored) {}
            if (grade.charAt(0) == '.') grade = "0" + grade;
            double adding = Double.parseDouble(grade);
            grades.add(adding);
            media += adding;
            if (adding < myGrade) numporencima++;
        }
        double numnotas = grades.size();
        double percentile = (numporencima / numnotas) * 100;
        media /= numnotas;
        String subject = filename.substring(0, 3).toUpperCase(), exam = filename.charAt(3) + "º Parcial",
                type = (filename.substring(4).equals("pract")) ? "Prácticas" : "Teoría";
        System.out.println("Tu nota en el " + exam + " de " + type + " de " + subject + " es: " + ((myGrade > 5) ? green : red) + myGrade + reset);
        System.out.println("Media General: " + ((media > 5) ? green : red) + String.format("%.2f", media) + reset + " (sobre " + (int) numnotas + " alumnos)");
        System.out.println("Percentil: Por encima del " + ((percentile > 70) ? green : "") + String.format("%.2f", percentile) + "%" + reset + " (" + (int) numporencima + " alumnos)");
    }
}
