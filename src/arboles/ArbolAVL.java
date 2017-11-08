/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboles;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author david
 */
public class ArbolAVL {

    public int BAL;
    public int DER;
    public int INFO;
    public int IZQ;
    public int MAXIMO;
    public int[][] a;
    public ArrayList<int[]> cursor;
    public int n;

    public ArbolAVL() {
        this.MAXIMO = 50;
        this.INFO = 0;
        this.BAL = 1;
        this.IZQ = 2;
        this.DER = 3;
        this.cursor = new ArrayList();
        this.a = (int[][]) Array.newInstance(Integer.TYPE, new int[]{this.MAXIMO, 6});
        init_cursor(this.a);
    }
    
    public void obtenerN(){
        this.n = this.a[0][this.IZQ];
    }

    public int disp(int[][] a) {
        int i = a[0][this.DER];
        if (i == 0) {
            return -1;
        }
        a[0][this.DER] = a[i][this.DER];
        return i;
    }

    public void init_cursor(int[][] a) {
        a[0][this.INFO] = 0;
        int[] iArr = a[0];
        int i = this.IZQ;
        a[0][this.BAL] = 0;
        iArr[i] = 0;
        int i2 = 0;
        while (i2 < this.MAXIMO) {
            iArr = a[i2];
            a[i2][5] = 0;
            iArr[4] = 0;
            a[i2][this.BAL] = 0;
            a[i2][this.DER] = i2 + 1;
            i2++;
        }
        a[i2 - 1][this.DER] = 0;
    }

    public int ins_avl(int n) {
        int nuevo = disp(this.a);
        if (nuevo == -1) {
            return -2;
        }
        this.a[nuevo][this.INFO] = n;
        int[] iArr = this.a[nuevo];
        int i = this.BAL;
        int[] iArr2 = this.a[nuevo];
        int i2 = this.IZQ;
        this.a[nuevo][this.DER] = 0;
        iArr2[i2] = 0;
        iArr[i] = 0;
        if (this.a[0][this.IZQ] == 0) {
            this.a[0][this.IZQ] = nuevo;
            return 0;
        }
        int s;
        int altura;
        int q = 0;
        int pp = 0;
        int pivote = this.a[0][this.IZQ];
        int p = pivote;
        int llave = this.a[nuevo][this.INFO];
        while (p != 0) {
            if (this.a[p][this.BAL] != 0) {
                pp = q;
                pivote = p;
            }
            if (llave == this.a[p][this.INFO]) {
                liberar(this.a, nuevo);
                return -1;
            }
            q = p;
            if (llave < this.a[p][this.INFO]) {
                p = this.a[p][this.IZQ];
            } else {
                p = this.a[p][this.DER];
            }
        }
        if (llave < this.a[q][this.INFO]) {
            this.a[q][this.IZQ] = nuevo;
        } else {
            this.a[q][this.DER] = nuevo;
        }
        if (llave < this.a[pivote][this.INFO]) {
            s = this.a[pivote][this.IZQ];
            altura = 1;
        } else {
            s = this.a[pivote][this.DER];
            altura = -1;
        }
        p = s;
        while (p != nuevo) {
            if (llave < this.a[p][this.INFO]) {
                this.a[p][this.BAL] = 1;
                p = this.a[p][this.IZQ];
            } else {
                this.a[p][this.BAL] = -1;
                p = this.a[p][this.DER];
            }
        }
        if (this.a[pivote][this.BAL] == 0) {
            this.a[pivote][this.BAL] = altura;
        } else if (this.a[pivote][this.BAL] + altura == 0) {
            this.a[pivote][this.BAL] = 0;
        } else {
            int[] saux = new int[1];
            if (altura == 1) {
                if (this.a[s][this.BAL] == 1) {
                    r_derecha(this.a, pivote, s);
                } else {
                    saux[0] = s;
                    dr_derecha(this.a, pivote, saux);
                    s = saux[0];
                }
            } else if (this.a[s][this.BAL] == -1) {
                r_izquierda(this.a, pivote, s);
            } else {
                saux[0] = s;
                dr_izquierda(this.a, pivote, saux);
                s = saux[0];
            }
            if (pp == 0) {
                this.a[0][this.IZQ] = s;
            } else if (this.a[pp][this.IZQ] == pivote) {
                this.a[pp][this.IZQ] = s;
            } else {
                this.a[pp][this.DER] = s;
            }
        }
        return 0;
    }

    public void r_derecha(int[][] a, int p, int q) {
        int[] iArr = a[p];
        int i = this.BAL;
        a[q][this.BAL] = 0;
        iArr[i] = 0;
        a[p][this.IZQ] = a[q][this.DER];
        a[q][this.DER] = p;
    }

    public void r_izquierda(int[][] a, int p, int q) {
        int[] iArr = a[p];
        int i = this.BAL;
        a[q][this.BAL] = 0;
        iArr[i] = 0;
        a[p][this.DER] = a[q][this.IZQ];
        a[q][this.IZQ] = p;
    }

    public void dr_derecha(int[][] a, int p, int[] q) {
        int r = a[q[0]][this.DER];
        a[q[0]][this.DER] = a[r][this.IZQ];
        a[r][this.IZQ] = q[0];
        a[p][this.IZQ] = a[r][this.DER];
        a[r][this.DER] = p;
        switch (a[r][this.BAL]) {
            case -1:
                a[q[0]][this.BAL] = 1;
                a[p][this.BAL] = 0;
                break;
            case 0:
                int[] iArr = a[q[0]];
                int i = this.BAL;
                a[p][this.BAL] = 0;
                iArr[i] = 0;
                break;
            case 1:
                a[q[0]][this.BAL] = 0;
                a[p][this.BAL] = -1;
                break;
        }
        a[r][this.BAL] = 0;
        q[0] = r;
    }

    public void dr_izquierda(int[][] a, int p, int[] q) {
        int r = a[q[0]][this.IZQ];
        a[q[0]][this.IZQ] = a[r][this.DER];
        a[r][this.DER] = q[0];
        a[p][this.DER] = a[r][this.IZQ];
        a[r][this.IZQ] = p;
        switch (a[r][this.BAL]) {
            case -1:
                a[q[0]][this.BAL] = 0;
                a[p][this.BAL] = 1;
                break;
            case 0:
                int[] iArr = a[q[0]];
                int i = this.BAL;
                a[p][this.BAL] = 0;
                iArr[i] = 0;
                break;
            case 1:
                a[q[0]][this.BAL] = -1;
                a[p][this.BAL] = 0;
                break;
        }
        a[r][this.BAL] = 0;
        q[0] = r;
    }

    public int consultar(int[][] a, int n) {
        int enc = 0;
        int p = a[0][this.IZQ];
        while (p != 0 && enc == 0) {
            if (n < a[p][this.INFO]) {
                p = a[p][this.IZQ];
            } else if (n > a[p][this.INFO]) {
                p = a[p][this.DER];
            } else {
                enc = 1;
            }
        }
        if (enc != 1) {
            return -1;
        }
        return p;
    }

    public void liberar(int[][] a, int p) {
        a[p][this.DER] = a[0][this.DER];
        a[0][this.DER] = p;
    }

    public void bal_der(int[][] a, int q, int[] t, int[] terminar) {
        switch (a[q][this.BAL]) {
            case -1:
                t[0] = a[q][this.DER];
                switch (a[t[0]][this.BAL]) {
                    case -1:
                        r_izquierda(a, q, t[0]);
                        return;
                    case 0:
                        a[q][this.DER] = a[t[0]][this.IZQ];
                        a[t[0]][this.IZQ] = q;
                        a[t[0]][this.BAL] = 1;
                        terminar[0] = 1;
                        return;
                    case 1:
                        dr_izquierda(a, q, t);
                        return;
                    default:
                        return;
                }
            case 0:
                a[q][this.BAL] = -1;
                terminar[0] = 1;
                return;
            case 1:
                a[q][this.BAL] = 0;
                return;
            default:
                return;
        }
    }

    public void bal_izq(int[][] a, int q, int[] t, int[] terminar) {
        switch (a[q][this.BAL]) {
            case -1:
                a[q][this.BAL] = 0;
                return;
            case 0:
                a[q][this.BAL] = 1;
                terminar[0] = 1;
                return;
            case 1:
                t[0] = a[q][this.IZQ];
                switch (a[t[0]][this.BAL]) {
                    case -1:
                        dr_derecha(a, q, t);
                        return;
                    case 0:
                        a[q][this.IZQ] = a[t[0]][this.DER];
                        a[t[0]][this.DER] = q;
                        a[t[0]][this.BAL] = -1;
                        terminar[0] = 1;
                        return;
                    case 1:
                        r_derecha(a, q, t[0]);
                        return;
                    default:
                        return;
                }
            default:
                return;
        }
    }

    public int retira_avl(int n) {
        int[] t = new int[1];
        int[] terminar = new int[1];
        if (this.a[0][this.IZQ] == 0) {
            System.out.println("Archivo vacio \n");
            return -1;
        }
        Stack pila = new Stack();
        int encontro = 0;
        terminar[0] = 0;
        int p = this.a[0][this.IZQ];
        while (encontro == 0 && p != 0) {
            pila.push(Integer.valueOf(p));
            if (n < this.a[p][this.INFO]) {
                p = this.a[p][this.IZQ];
            } else if (n > this.a[p][this.INFO]) {
                p = this.a[p][this.DER];
            } else {
                encontro = 1;
            }
        }
        if (encontro == 0) {
            System.out.println("no existe");
            return -1;
        }
        int accion;
        t[0] = 0;
        p = ((Integer) pila.pop()).intValue();
        int llave = this.a[p][this.INFO];
        if (this.a[p][this.IZQ] == 0 && this.a[p][this.DER] == 0) {
            accion = 0;
        } else if (this.a[p][this.DER] == 0) {
            accion = 1;
        } else if (this.a[p][this.IZQ] == 0) {
            accion = 2;
        } else {
            accion = 3;
        }
        int q;
        if (accion == 0 || accion == 1 || accion == 2) {
            if (!pila.empty()) {
                q = ((Integer) pila.pop()).intValue();
                if (llave >= this.a[q][this.INFO]) {
                    switch (accion) {
                        case 0:
                        case 2:
                            this.a[q][this.DER] = this.a[p][this.DER];
                            bal_izq(this.a, q, t, terminar);
                            break;
                        case 1:
                            this.a[q][this.DER] = this.a[p][this.IZQ];
                            bal_izq(this.a, q, t, terminar);
                            break;
                        default:
                            break;
                    }
                }
                switch (accion) {
                    case 0:
                    case 1:
                        this.a[q][this.IZQ] = this.a[p][this.IZQ];
                        bal_der(this.a, q, t, terminar);
                        break;
                    case 2:
                        this.a[q][this.IZQ] = this.a[p][this.DER];
                        bal_der(this.a, q, t, terminar);
                        break;
                }
            }
            switch (accion) {
                case 0:
                    this.a[0][this.IZQ] = 0;
                    terminar[0] = 1;
                    break;
                case 1:
                    this.a[0][this.IZQ] = this.a[p][this.IZQ];
                    break;
                case 2:
                    this.a[0][this.IZQ] = this.a[p][this.DER];
                    break;
                default:
                    break;
            }
        }
        pila.push(Integer.valueOf(p));
        int r = p;
        p = this.a[r][this.DER];
        q = 0;
        while (this.a[p][this.IZQ] != 0) {
            pila.push(Integer.valueOf(p));
            q = p;
            p = this.a[p][this.IZQ];
        }
        int[] iArr = this.a[r];
        int i = this.INFO;
        llave = this.a[p][this.INFO];
        iArr[i] = llave;
        if (q != 0) {
            this.a[q][this.IZQ] = this.a[p][this.DER];
            bal_der(this.a, q, t, terminar);
        } else {
            this.a[r][this.DER] = this.a[p][this.DER];
            bal_izq(this.a, r, t, terminar);
        }
        ((Integer) pila.pop()).intValue();
        liberar(this.a, p);
        while (!pila.empty() && terminar[0] == 0) {
            q = ((Integer) pila.pop()).intValue();
            if (llave < this.a[q][this.INFO]) {
                if (t[0] != 0) {
                    this.a[q][this.IZQ] = t[0];
                    t[0] = 0;
                }
                bal_der(this.a, q, t, terminar);
            } else {
                if (t[0] != 0) {
                    this.a[q][this.DER] = t[0];
                    t[0] = 0;
                }
                bal_izq(this.a, q, t, terminar);
            }
        }
        if (t[0] != 0) {
            if (pila.empty()) {
                this.a[0][this.IZQ] = t[0];
            } else {
                q = ((Integer) pila.pop()).intValue();
                if (llave < this.a[q][this.INFO]) {
                    this.a[q][this.IZQ] = t[0];
                } else {
                    this.a[q][this.DER] = t[0];
                }
            }
        }
        return 1;
    }

    public void alturaarbol(int p, int[] alto, int h) {
        if (p != 0) {
            alturaarbol(this.a[p][this.IZQ], alto, h + 1);
            if (alto[0] < h) {
                alto[0] = h;
            }
            alturaarbol(this.a[p][this.DER], alto, h + 1);
        }
    }

    public void inorden(int p, String[] dt) {
        if (p != 0) {
            inorden(this.a[p][this.IZQ], dt);
            dt[0] = dt[0] + this.a[p][this.INFO] + ",";
            inorden(this.a[p][this.DER], dt);
        }
    }

    public void preorden(int p, String[] dt) {
        if (p != 0) {
            dt[0] = dt[0] + this.a[p][this.INFO] + ",";
            preorden(this.a[p][this.IZQ], dt);
            preorden(this.a[p][this.DER], dt);
        }
    }

    public void posorden(int p, String[] dt) {
        if (p != 0) {
            posorden(this.a[p][this.IZQ], dt);
            posorden(this.a[p][this.DER], dt);
            dt[0] = dt[0] + this.a[p][this.INFO] + ",";
        }
    }

    public void imp_cursor(int[][] a) {
        for (int i = 0; i < 20; i++) {
            System.out.println(a[i][this.INFO] + " " + a[i][this.BAL] + " " + a[i][this.IZQ] + " " + a[i][this.DER]);
        }
    }
    
    public static void main(String args[]) {
        ArbolAVL avl = new ArbolAVL();
        avl.ins_avl(2);
        avl.ins_avl(3);
        avl.ins_avl(7);
        avl.ins_avl(1);
        avl.ins_avl(10);
        avl.ins_avl(9);
        avl.ins_avl(15);
        avl.ins_avl(12);
        
        int p = avl.a[0][avl.IZQ];
        
        String[] inOrden = new String[1];
        String[] postOrden = new String[1];
        String[] preOrden = new String[1];
        
        inOrden[0] = " ";
        postOrden[0] = " ";
        preOrden[0] = " ";
        
        
        avl.posorden(p, postOrden);
        avl.preorden(p, preOrden);
        avl.inorden(p, inOrden);
        
        System.out.println("inorden: " + inOrden[0]);
        System.out.println("postorden: " + postOrden[0]);
        System.out.println("preorden: " + preOrden[0]);
        
        
    }

}
