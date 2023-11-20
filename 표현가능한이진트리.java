class Solution {
    public int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];
        
        for (int i = 0; i < numbers.length; i++) {
            answer[i] = expressible(numbers[i]) ? 1 : 0;
        }
        
        return answer;
    }
    
    private boolean expressible(long number) {
        int height = findHeightOf(number);
        int length = (int) (Math.pow(2, height) - 1);
        
        int[] bits = toBinary(number, length);
        
        return expressible(bits, 0, length);
    }
    
    private int findHeightOf(long number) {
        int height = 1;
        while (Math.pow(2, Math.pow(2, height) - 1) - 1 < number) {
            height++;
        }
        
        return height;
    }
    
    private int[] toBinary(long number, int length) {
        int[] bits = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            bits[i] = (int) (number & 1L);
            number >>= 1;
        }
        
        return bits;
    }
    
    private boolean expressible(int[] bits, int left, int right) {
        if (left + 1 == right) {
            return true;
        }
        
        int root = (left + right) / 2;
        
        int leftRoot = (left + root) / 2;
        int rightRoot = (root + right) / 2;
        
        if (bits[root] == 0 && (bits[leftRoot] == 1 || bits[rightRoot] == 1)) {
            return false;
        }
        
        return expressible(bits, left, root) && expressible(bits, root + 1, right);
    }
}