/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arboles;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class ArbolBinario {
    
    private int tam;
    private int alt;
    private int[][] aux;
    public int n;
    
      
    public void obtenerN(){
        this.n = aux[0][0];
    }
    

    public ArbolBinario() {
        this.tam = 50;
        this.aux = (int[][]) Array.newInstance(Integer.TYPE, new int[]{this.tam, 7});
        for (int i = 0; i < this.tam; i++) {
            this.aux[i][0] = 0;
            this.aux[i][1] = i + 1;
            this.aux[i][2] = 0;
            this.aux[i][3] = 0;
            this.aux[i][4] = 0;
            this.aux[i][5] = 0;
            this.aux[i][6] = 0;
        }
        this.aux[this.tam - 1][1] = 0;
        this.aux[0][0] = 0;
    }

    public int nodo(int info) {
        int sig = this.aux[0][1];
        if (sig == 0) {
            return -1;
        }
        this.aux[0][1] = this.aux[sig][1];
        this.aux[sig][0] = info;
        for (int k = 1; k < 7; k++) {
            this.aux[sig][k] = 0;
        }
        return sig;
    }

    public void liberar(int p, int[][] cur) {
        for (int k = 0; k < 7; k++) {
            cur[p][k] = 0;
        }
        cur[p][1] = cur[0][1];
        cur[0][1] = p;
    }

    public int insertarNum(int n) {
        int baja = 0;
        if (this.aux[0][0] == 0) {
            this.aux[0][0] = nodo(n);
        return 1;
        }
        int q = this.aux[0][0];
        int p = q;
        while (q != 0 && this.aux[p][0] != n) {
            p = q;
            baja++;
            if (n < this.aux[p][0]) {
                q = this.aux[q][1];
            } else {
                q = this.aux[q][2];
            }
        }
        if (this.aux[p][0] == n) {
            return -1;
        }
        int result;
        if (n < this.aux[p][0]) {
            result = insIzq(p, n);
        } else {
            result = insDer(p, n);
        }
        if (this.alt >= baja) {
            return result;
        }
        this.alt = baja;
        return result;
    }

    public int insIzq(int p, int n) {
        int nuevo = nodo(n);
        if (nuevo == -1) {
            return -2;
        }
        this.aux[p][1] = nuevo;
        return 1;
    }

    public int insDer(int p, int n) {
        int nuevo = nodo(n);
        if (nuevo == -1) {
            return -2;
        }
        this.aux[p][2] = nuevo;
        return 1;
    }

    public int retirar_nodo(int n) {
        int p = this.aux[0][0];
        int q = 0;
        int encontro = 0;
        while (p != 0 && encontro == 0) {
            if (this.aux[p][0] == n) {
                encontro = 1;
            } else {
                q = p;
                if (n < this.aux[p][0]) {
                    p = this.aux[p][1];
                } else {
                    p = this.aux[p][2];
                }
            }
        }
        if (encontro == 0) {
            return -1;
        }
        borrar_nodo(q, p, this.aux);
        return 1;
    }

    public void borrar_nodo(int q, int p, int[][] cur) {
        int r;
        if (cur[p][1] == 0) {
            r = cur[p][2];
        } else if (cur[p][2] == 0) {
            r = cur[p][1];
        } else {
            int s = p;
            r = cur[p][2];
            int t = cur[r][1];
            while (t != 0) {
                s = r;
                r = t;
                t = cur[t][1];
            }
            if (p != s) {
                cur[s][1] = cur[r][2];
                cur[r][2] = cur[p][2];
            }
            cur[r][1] = cur[p][1];
        }
        if (q == 0) {
            cur[0][0] = r;
        } else if (p == cur[q][1]) {
            cur[q][1] = r;
        } else {
            cur[q][2] = r;
        }
        liberar(p, cur);
    }

    public void inorden(int p, String[] dt) {
        if (p != 0) {
            inorden(this.aux[p][1], dt);
            dt[0] = dt[0] + this.aux[p][0] + ",";
            inorden(this.aux[p][2], dt);
        }
    }

    public void alturaarbol(int p, int[] alto, int h) {
        if (p != 0) {
            alturaarbol(this.aux[p][1], alto, h + 1);
            if (alto[0] < h) {
                alto[0] = h;
            }
            alturaarbol(this.aux[p][2], alto, h + 1);
        }
    }

    public void preorden(int p, String[] dt) {
        if (p != 0) {
            dt[0] = dt[0] + this.aux[p][0] + ",";
            preorden(this.aux[p][1], dt);
            preorden(this.aux[p][2], dt);
        }
    }

    public void posorden(int p, String[] dt) {
        if (p != 0) {
            posorden(this.aux[p][1], dt);
            posorden(this.aux[p][2], dt);
            dt[0] = dt[0] + this.aux[p][0] + ",";
        }
    }
    
}
