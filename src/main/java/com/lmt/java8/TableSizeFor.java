public class TableSizeFor {
	/*
	 * 此方法为java8中的HashMap构造方法中取阈值得方法，返回一个int类型的最近大于cap的2的幂次
	 * 
	 */
	 static final int MAXIMUM_CAPACITY = 1 << 30;
	 public static int tableSizeFor(int cap){
	 int n = cap - 1;
     n |= n >>> 1;
     n |= n >>> 2;
     n |= n >>> 4;
     n |= n >>> 8;
     n |= n >>> 16;
     return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	 }
}
