/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author david
 */
public class LogicaLienzo extends Canvas {

    public String postOrden;
    public String preOrden;
    public String inOrden;
    public String tempPosPre;
    public int[][] matriz;
    public int tam;
    public int ancho;
    public int alto;
    //public int terminado = 0;
    int i = 1;
    int j = 1;
    int mitadRaiz = 0;
    int mitad = 0;
    int mitAnch;
    int alt = 30;
    int temp;
    int colnodoCentral;
    

    public LogicaLienzo(String postOrden, String preOrden, String inOrden, int ancho, int alto) {
        this.postOrden = postOrden.substring(0, postOrden.length() - 1);
        this.preOrden = preOrden.substring(0, postOrden.length() - 1);
        this.inOrden = inOrden.substring(0, postOrden.length() - 1);
        this.ancho = ancho;
        this.alto = alto;
    }

    @Override
    public void paint(Graphics g) {
        mitAnch = this.ancho / 2;
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
//        g.drawString("InOrden: " + inOrden, 0, 15);
//        g.drawString("PostOrden: " + postOrden, 0, 30);
//        g.drawString("PreOrden: " + preOrden, 0, 45);
        crearMatriz();
        dibAr(1,1, g, mitAnch,alt, matriz.length, mitAnch, alt);
        //dibujarArbol(g);
    }

//    public void dibujarArbol(Graphics g) {
//        int mitadRaiz = 0;
//        int mitad = 0;
//        int mitAnch = this.ancho / 2;
//        int alt = 30;
//        int temp;
//        for (int i = 0; i < matriz.length; i++) {
//            for (int j = 0; j < matriz.length; j++) {
//                if (matriz[i][j] == 1 && i != 0 && j != 0) {
//                    if (i == 1) {
//                        g.drawString(String.valueOf(matriz[0][j]), mitAnch, alt);
//                        mitadRaiz = j;
//                        mitad = j;
//                        mitAnch = mitAnch / 2;
//                        alt = alt * 2;
//                    } else {
//                        if (j < mitad && j < mitadRaiz) {
//                            temp = mitad;
//                            mitad = j;
//                            g.drawLine(mitAnch * 2, alt / 2, mitAnch, alt);
//                            g.drawString(String.valueOf(matriz[0][j]), mitAnch, alt);
//                            mitAnch = mitAnch / 2;
//                            alt = alt * 2;
//                            if (j == 1) {
//                                mitad = temp;
//                            }
//                        } else if (j > mitad && j < mitadRaiz) {
//                            mitad = j;
//                            g.drawLine(mitAnch * 2, alt / 2, mitAnch * i, alt);
//                            g.drawString(String.valueOf(matriz[0][j]), mitAnch * i, alt);
//                            mitAnch = mitAnch / 2;
//                            alt = alt * 2;
//                        }
//                    }
//                }
//            }
//        }
//    }


    
    public void dibAr(int fila, int col, Graphics g, int x, int y, int pFin, int xPadre, int yPadre) {
        boolean terminado = false;
        final int insFila = fila;
        int insCol = col;
        final int insx = x;
        final int insxPadre = xPadre;
        final int insyPadre = yPadre;
        final int temp = insFila+1;
        final int insy = y;
        int tempy = insy*2;
        int derTempY = yPadre*2;
        int tempxIzq = insxPadre/2;
        int tempxDer = xPadre+(xPadre/2);
        
        while (!terminado) {
            if (insFila < matriz.length) {
                if (insCol < pFin) {
                    if (matriz[insFila][insCol] == 1) {
                        if(insFila == 1){
                            colnodoCentral = insCol;
                            g.drawString(String.valueOf(matriz[0][insCol]), insx, insy);
                            dibAr(temp, 1, g, tempxIzq, tempy, insCol, insx, insy);
//                            tempy = insy/2;
//                            tempxIzq = insxPadre*2;
//                            tempxDer = insxPadre-tempxIzq;
                            dibAr(temp, insCol, g, tempxDer, derTempY, matriz.length, xPadre, yPadre);
                            terminado = true;
                            break;
                        }else{
                            g.drawLine(insxPadre, insyPadre, insx, insy);
                            g.drawString(String.valueOf(matriz[0][insCol]),insx, insy);
                            if(insCol !=1 && insCol < matriz.length-1){
                                dibAr(temp, 1, g, insx/2, tempy, insCol, insx, insy);
//                                tempy = insy/2;
//                                tempxIzq = insxPadre*2;
//                                tempxDer = insxPadre-tempxIzq;
                                //dibAr(temp, insCol, g, (insx + (insx/2)), tempy, matriz.length, insx, insy);
                                dibAr(temp, insCol, g, tempxDer, derTempY, colnodoCentral, xPadre, yPadre);
                            }if (insCol !=1){
                                dibAr(temp, 1, g, insx/2, tempy, insCol, insx, insy);
//                                tempy = insy/2;
//                                tempxIzq = insxPadre*2;
//                                tempxDer = insxPadre-tempxIzq;
                            }if (insCol <= matriz.length-1){
                                //dibAr(temp, insCol, g, (insx + (insx/2)), tempy, matriz.length, insx, insy);
                                dibAr(temp, insCol, g, tempxDer, derTempY, colnodoCentral, xPadre, yPadre);
                            }
                            terminado = true;
                            break;
                        }
                    }else{
                        insCol++;
                    }
                }else{
                    terminado = true;
                    break;
                }
            } else {
                terminado = true;
                break;
            }

        }

    }
    
    public void dibAr2(int fila, int col, int pFin, Graphics g, int x, int y, int exIzq, int exDer) {
        boolean terminado = false;
        int insFila = fila;
        //int insCol = col;
        //int insx = x;
        //int insy = y;
        int xIzq = x/2;
        int yIzq = y*2;
        int xDer = x+(x/2);
        int yDer = y*2;
       // int tempy = insy*2;
        
        while (!terminado) {
            if (fila < matriz.length) {
                //if (col < pFin) {
                    if (matriz[fila][col] == 1) {
                        g.drawString(String.valueOf(matriz[0][col]), x, y);
                        for(int i=0; i<matriz.length; i++){
                            for(int j=1; j<exIzq; j++){
                                if(matriz[i][j] == 1){
                                    g.drawLine(x, y, xIzq, yIzq);
                                    g.drawString(String.valueOf(matriz[0][j]), xIzq, yIzq);
                                    j = pFin;
                                    i = matriz.length;
                                }
                            }
                        }
                        for(int i =0; i<matriz.length; i++){
                            for(int j=exIzq+1; j<exDer; j++){
                                if(matriz[i][j] == 1){
                                    g.drawLine(x, y, xIzq, yIzq);
                                    g.drawString(String.valueOf(matriz[0][j]), xDer, yDer);
                                    j = pFin;
                                    i = matriz.length;
                                }
                            }
                        }
                    }else{
                        col++;
                    }
                //}else{
                    //terminado = true;
                    //break;
                //}
            } else {
                terminado = true;
                break;
            }

        }

    }
    

    public void crearMatriz() {
        String[] tempIn = this.inOrden.split(",");
        tam = tempIn.length;
        matriz = new int[tam + 1][tam + 1];

        if (preOrden != null) {
            for (int i = 0; i <= tam; i++) {
                for (int j = 0; j <= tam; j++) {
                    if (i == 0 && j != 0) {
                        matriz[i][j] = cortarCadena(inOrden)[j - 1];
                    }
                    if (i != 0 && j == 0) {
                        matriz[i][j] = cortarCadena(preOrden)[i - 1];
                    } else if (matriz[0][j] == matriz[i][0] && (i != 0 && j != 0)) {
                        matriz[i][j] = 1;
                    }
                    System.out.println(matriz[i][j]);

                }
                System.out.println("fin fila");
            }
        } else if (postOrden != null) {

        }

    }

    public int[] cortarCadena(String cadena) {
        String temp[];
        cadena = cadena.trim();
        temp = cadena.split(",");
        int[] tempval = new int[cadena.length()];
        for (int i = 0; i < temp.length; i++) {
            tempval[i] = Integer.valueOf(temp[i]);
        }
        return tempval;
    }

}
