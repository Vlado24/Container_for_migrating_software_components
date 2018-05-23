package cz.vutbr.fit.stud.xscesn00;

import java.io.Serializable;

public class BubbleSort implements Serializable {

    private int startingPos = 0;
    private int finishingPos = 6;
    private int arr[] =
            {6337, 1272, 4369, 8273, 9890, 6359, 3131, 9979, 3531, 3616, 8783, 7920, 7674, 8298, 289,
                    4094, 4992, 4007, 7365, 9436, 5723, 4008, 7343, 3520, 1217, 5738, 68, 4067, 4429, 3512, 1, 6561,
                    3882, 9590, 4867, 889, 8888, 1570, 8598, 2137, 1821, 3292, 8740, 4885, 6707, 5573, 2805, 9899, 7820, 6885,
                    5400, 651, 477, 8153, 6319, 6230, 9554, 4773, 5754, 7158, 2103, 8149, 1993, 1072, 4231, 5343, 1104,
                    1080, 590, 7867, 402, 89, 9134, 9046, 9508, 2501, 3053, 8109, 3672, 6089, 8979, 2325, 54, 8015, 596};

    public int getStartingPos() {
        return startingPos;
    }

    public void setStartingPos(int startingPos) {
        this.startingPos = startingPos;
    }

    public int getFinishingPos() {
        return finishingPos;
    }

    public void setFinishingPos(int finishingPos) {
        if (finishingPos >= arr.length) {
            this.finishingPos = arr.length;
        }
        this.finishingPos = finishingPos;
    }

    public void bubbleSort() {
        for (int i = startingPos; i < finishingPos - 1; i++) {
            for (int j = 0; j < finishingPos - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    // swap temp and arr[i]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
        }
        printArray(arr);

    }

    private void printArray(int arr[]) {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
}