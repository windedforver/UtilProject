package com.lmt.sudoku;

public class Sudoku {  
      
    public static void main(String[] args) {  
//        Scanner sc=new Scanner(System.in);
    	 int[][]sudo={{0,0,5,3,0,0,0,0,0},
	    			  {8,0,0,0,0,0,0,2,0},
	    			  {0,7,0,0,1,0,5,0,0},
	    			  {4,0,0,0,0,5,3,0,0},
	    			  {0,1,0,0,7,0,0,0,6},
	    		 	  {0,0,3,2,0,0,0,8,0},
	    		 	  {0,6,0,5,0,0,0,0,9},
	    			  {0,0,4,0,0,0,0,3,0},
	    			  {0,0,0,0,0,9,2,0,0}};
        long startTime = System.currentTimeMillis();
        while(true){  
//            int[][]a=new int[9][9];  
            boolean[][] cols = new boolean[9][9];  
            boolean[][] rows = new boolean[9][9];  
            boolean[][] blocks = new boolean[9][9];//九个区的九个数字  
  
            for (int i = 0; i < sudo.length; i++) {  
                for (int j = 0; j < sudo.length; j++) {  
//                	sudo[i][j]=sc.nextInt();  
                    if(sudo[i][j]!=0){  
                        int k = i/3*3+ j/3;//划分九个区,这里以行优先，自己也可以列优先  
                        int val=sudo[i][j]-1;  
                          rows[i][val] = true;  //第i行的val设为true表示第i行已经出现过val+1
                            cols[j][val] = true; //第j列的val设为true表示第j列已经出现过val+1 
                            blocks[k][val] = true; //第k行的val设为true表示第k区已经出现过val+1 
                    }  
                }  
            }//数据装载完毕    
//            sc.close();
            DFS(sudo, cols, rows, blocks);
            if(!CheckSudo(sudo)){
            	System.out.println("输入数组错误，不存在答案！");
            	return ;
            }
            long endTime = System.currentTimeMillis();
            for (int i = 0; i < 9; i++) {  
                for (int j = 0; j < 8; j++) {  
                    System.out.print(sudo[i][j]+" ");  
                }  
                System.out.println(sudo[i][8]);  
            }  
            System.out.println("消耗时间："+(endTime-startTime)+"ms");
            return ;
        }  
    }  
   public static boolean DFS(int[][] a,boolean[][] cols,boolean[][] rows,boolean[][] blocks) {  
        for (int i = 0; i < 9; i++) {  
            for (int j = 0; j < 9; j++) {  
                if(a[i][j]==0){  
                    int k=i/3*3+j/3; //判断a[i][j]在哪个区中 
                    for (int l = 0; l < 9; l++) {  
                        if(!rows[i][l]&&!cols[j][l]&&!blocks[k][l]){//判断数字l+1都没有在行列块中出现  
                               rows[i][l] = cols[j][l] = blocks[k][l] = true;  
                                a[i][j] = 1 + l;//下标加1  
                                if(DFS(a, cols, rows, blocks)) return true;//递进则返回true  
                                rows[i][l] = cols[j][l] = blocks[k][l] = false;//递进失败则回溯  
                                a[i][j] = 0;    
                        }  
                    }  
                    return false;//a[i][j]==0时发现最后没有数字能填入到a[i][j]中，即该循环失败，返回false  
                }//the end of a[i][j]==0  
            }  
        }  
        return true;//没有a[i][j]==0,则返回true  
    }  
   public static boolean CheckSudo(int[][] sudo){
	   boolean[][] cols = new boolean[9][9];  
       boolean[][] rows = new boolean[9][9];  
       boolean[][] blocks = new boolean[9][9];//九个区的九个数字  

       for (int i = 0; i < sudo.length; i++) {  
           for (int j = 0; j < sudo.length; j++) {  
               if(sudo[i][j]!=0){  
                   int k = i/3*3+ j/3;//划分九个区,这里以行优先，自己也可以列优先  
                   int val=sudo[i][j]-1;  
                     rows[i][val] = true;  //第i行的val设为true表示第i行已经出现过val+1
                       cols[j][val] = true; //第j列的val设为true表示第j列已经出现过val+1 
                       blocks[k][val] = true; //第k行的val设为true表示第k区已经出现过val+1 
               }  
           }  
       }//数据装载完毕    
       for(int i = 0;i<9;i++){
    	   for(int j = 0;j<9;j++){
    		   int k =i/3*3+j/3;
    		   for(int l = 0;l<9;l++){
    			   if(!rows[i][l]||!cols[j][l]||!blocks[k][l])	//只要行、列、区数组中有一个数l不存在，则不是数独
    				   return false;
    		   }
    	   }
       }
	   return true;
   }
}