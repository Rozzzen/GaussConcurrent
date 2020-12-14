public class SubMatrixThread extends Thread {

    GaussianElimination gaussianElimination;
    int innerRow;
    int row;

    public SubMatrixThread(GaussianElimination gaussianElimination, int innerRow, int row) {
        this.gaussianElimination = gaussianElimination;
        this.innerRow = innerRow;
        this.row = row;
    }

    @Override
    public void run() {
        double innerValue = gaussianElimination.GetMatrix()[innerRow][row];
        for (int innerCol = row + 1; innerCol < gaussianElimination.getSize(); innerCol++) {
            gaussianElimination.GetMatrix()[innerRow][innerCol] -= innerValue * gaussianElimination.GetMatrix()[row][innerCol];
        }
        gaussianElimination.GetSolutionVector()[innerRow] -= gaussianElimination.GetMatrix()[innerRow][row] * gaussianElimination.GetSolutionVector()[row];
        gaussianElimination.GetMatrix()[innerRow][row] = 0.0;
    }
}