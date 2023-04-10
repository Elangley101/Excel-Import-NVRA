public class NvraRecord {
    private int[] nums;
    private String[] strings;

    public NvraRecord(int[] nums, String[] strings) {
        this.nums = nums;
        this.strings = strings;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int size = nums.length + strings.length;
        for (int i = 0, j = 0, k = 0; i < size; i++) {
            if (i != 3 && i != 11 && i != 12) {
                builder.append(nums[j++]);
            } else {
                builder.append(strings[k++]);
            }
            if (i < size - 1) {
                builder.append(';');
            }
        }
        return builder.toString();
    }
}
