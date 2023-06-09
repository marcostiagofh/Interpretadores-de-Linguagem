/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

/**
 *
 * @author Marcos
 */
public class Matrix {
    private int matrix[][];
    private int rows;
    private int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        if(rows > 100 | cols > 100){
            System.out.println("Tamanho de matriz inválido. Linhas e colunas não podem ultrapassar o valor de 100.");
            System.exit(1);
        }
        if(rows < 0 | cols < 0){
            System.out.println("Tamanho de matriz inválido. Linhas e colunas não podem ser negativas.");
            System.exit(1);
        }
        matrix = new int[100][100];
    }
    
    public static Matrix iseq(int from, int to){
        int num_elementos = from - to + 1;        
        Matrix m = new Matrix(1,num_elementos);
        for(int i = 0; i <num_elementos; i++)
            m.matrix[0][i] = from - i;
        return m;
    }
    public static Matrix seq(int from, int to){
        int num_elementos = to - from + 1;
        Matrix m = new Matrix(1,num_elementos);
        for(int i = 0; i <num_elementos; i++)
            m.matrix[0][i] = from + i;
        return m;
    }
    public static Matrix id(int r, int c){
    Matrix m = new Matrix(r,c);
        for(int i = 0; i < r; i++)
            for(int j = 0; j < c; j++){
                if(i == j)
                   m.matrix[i][j] = 1;
                else
                   m.matrix[i][j] = 0;
            }
        return m;
    }
    public static Matrix rand(int r, int c){
        Matrix m = new Matrix(r,c);
        for(int i = 0; i < r; i++)
            for(int j = 0; j < c; j++)
                   m.matrix[i][j] = (int) (Math.random()* 4.0 + 1);
        return m;
    }
    public static Matrix fill(int r, int c, int v){
        Matrix m = new Matrix(r,c);
        for(int i = 0; i < r; i++)
            for(int j = 0; j < c; j++)
                   m.matrix[i][j] = v;
        return m;
    }
    public static Matrix nullMatrix(int r, int c){
        Matrix m = new Matrix(r,c);
        for(int i = 0; i < r; i++)
            for(int j = 0; j < c; j++)
                   m.matrix[i][j] = 0;
        return m;
    }
    public Matrix mul(int n){
        Matrix mat = new Matrix(this.rows, this.cols);
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j < this.cols; j++)
                   mat.matrix[i][j] = this.matrix[i][j] * n;
        return mat;
    }
    public Matrix mul(Matrix m){
        Matrix c = new Matrix(this.rows,this.cols);
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j < this.cols; j++)
                for(int k = 0; k < m.rows; k++)
                    c.matrix[i][j] += this.matrix[i][k] * m.matrix[k][j];
        return c;
    }
    public Matrix sum(Matrix m){        
        Matrix mat = new Matrix(this.rows,this.cols);
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j < this.cols; j++)
                   mat.matrix[i][j] = m.matrix[i][j] + this.matrix[i][j];
        return mat;
    }
    public Matrix transposed(){
        Matrix mat = new Matrix(this.rows,this.cols);
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j < this.cols; j++){                   
                   mat.matrix[i][j] = this.matrix[j][i];
            }
        return mat;
    }
    public Matrix opposed(){
        Matrix mat = new Matrix(this.rows,this.cols);
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j < this.cols; j++)
                   mat.matrix[i][j] = this.matrix[i][j] * -1; 
	return mat;
    }
    public int value(int r, int c){
        return this.matrix[r][c];
    }
    public int cols(){
        return this.cols;
    }
    public int rows(){
        return this.rows;
    }
    public int size(){
        return this.rows * this.cols;
    }
    public void show(){
        for(int i = 0; i < this.rows; i++){
            System.out.print("[");
            for(int j = 0; j < this.cols; j++)
                System.out.print(" " + this.matrix[i][j]);
            System.out.println(" ]");
        }
    }
    public void setValue(int r, int c, int v){
        this.matrix[r][c] = v;
    }
    public void setCols(int cols){
        this.cols = cols;
    }
    public void setRows(int rows){
        this.rows = rows;
    }
}
