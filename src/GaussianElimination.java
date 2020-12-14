import java.util.Arrays;

class GaussianElimination extends Thread {

    private final int size = 3;
    private double[][] matrix;
    private double[] solutionVector;
    private final double[] answers = new double[size];
    private final SubMatrixThread []threads = new SubMatrixThread[size];

    public static void main(String[] args) {
        GaussianElimination gaussianElimination = new GaussianElimination();
        gaussianElimination.setMatrix(new double[][]{{1, -2, 1}, {2, 2, -1}, {4, -1, 1}});
        gaussianElimination.setSolutionVector(new double[]{0, 3, 5});
        gaussianElimination.GEAlgorithmSequential();
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public void setSolutionVector(double[] solutionVector) {
        this.solutionVector = solutionVector;
    }

    public void GEAlgorithmSequential() {
        printArray();

        for (int row = 0; row < size; row++) {
            double value = matrix[row][row];
            for (int col = row + 1; col < size; col++) {
                matrix[row][col] /= value;
            }

            solutionVector[row] /= value;
            matrix[row][row] = 1.0;

            for (int innerRow = row + 1; innerRow < size; innerRow++) {
                threads[innerRow] = new SubMatrixThread(this, innerRow, row);
                threads[innerRow].start();
            }

            for (int innerRow = row + 1; innerRow < size; innerRow++) {
                try {
                    threads[innerRow].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        printArray();

        for (int back = size - 1; back >= 0; back--) {
            answers[back] = solutionVector[back];
            for (int i = back - 1; i >= 0; i--) {
                solutionVector[i] -= answers[back] * matrix[i][back];
            }
        }
        System.out.println(Arrays.toString(solutionVector));
    }

    public double[][] GetMatrix() {
        return matrix;
    }

    public double[] GetSolutionVector() {
        return solutionVector;
    }

    public int getSize() {
        return size;
    }

    public void printArray() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + "   ");
            }
            System.out.print("| " + solutionVector[i]);
            System.out.println();
        }
        System.out.println();
    }
}