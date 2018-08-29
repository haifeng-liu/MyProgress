布局文件中添加
 
 
 <com.liuhf.myprogress.progress.MyLineProgressView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@drawable/crirle"
        app:max="100"
        app:color="@color/colorAccent"
        app:progress="100"
        app:linorcri="circles"
        app:strokewidth="6"
        app:paddings="0"
        />
        
        
        自定义属性：
        max:最大值（默认100）
        color：进度条颜色
        progress：进度条值
        linorcri:直线型或圆环形(line,circles)
        strokewidth:直线或圆环宽度
        paddings：圆环与背景的边距
        
        代码中的方法：
        .settype(int type);直线型或圆环形(LINE,CIRCLE);
        .setcolor(int color);进度条颜色
        .setMax(int max);最大值（默认100）
        .setProgress(int progress);进度条的值
