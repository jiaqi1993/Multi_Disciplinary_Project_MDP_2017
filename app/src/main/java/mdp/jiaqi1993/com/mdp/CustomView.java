package mdp.jiaqi1993.com.mdp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by vinu on 2/9/17.
 */




public class CustomView extends View{

    private Rect rectangle;
    private ArrayList<ArrayList<Rect>> rectangles;
    private Paint paint,fill;
    private Canvas canvas;
    private ArrayList<String> colors;
    int left=0;
    int top=0;
    int right=100;
    int bottom=100;
    GridLayout gv;

    public CustomView(Context context) {
        super(context);

        paint=new Paint();
        fill=new Paint();
        rectangles=new ArrayList<ArrayList<Rect>>();
        colors=new ArrayList<>();

        canvas=new Canvas();
        canvas.drawColor(Color.WHITE);
        int x = 50;
        int y = 50;


        int left=0;
        int top=0;
        int right=100;
        int bottom=100;

        String hex="E4843";

        //check hexadecimal conversion
        String s = new BigInteger(hex,16).toString(2);
        //Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
        String[] a=s.split("");

        int j=0;
        for(int i=0;i<s.length();i++)
        {
            if(a[i].equals("0")){
                colors.add("#ffffff");

            }
            else {
                colors.add("#000000");
            }
        }

        int length= colors.size();


        int counter=0;



    }

   // @Override
   protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        int k=0;

       /*rectangle = new Rect(left, top, right, bottom);
       paint.setStyle(Paint.Style.STROKE);
       paint.setColor(Color.BLACK);
       paint.setStrokeWidth(10);
       canvas.drawRect(rectangle,paint);
       fill.setColor(Color.parseColor(colors.get(0)));
       fill.setStyle(Paint.Style.FILL);
       canvas.drawRect(rectangle, fill);
*/



       for(int i=0;i<4;i++) {
           ArrayList<Rect> list=new ArrayList<Rect>();
           //rectangles.add(list);
            for (int j = 0; j < 4; j++) {
                rectangle = new Rect(j * left, i*top, (j + 1) * right, (i+1)*bottom);

                //set border


                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(5);
                canvas.drawRect(rectangle,paint);

                //set fill color
                /*fill.setColor(Color.RED);
                fill.setStyle(Paint.Style.FILL);
                canvas.drawRect(rectangle, fill);*/
                //rectangles.add(rectangle);
                list.add(rectangle);
                //rectangles.get(i).add(rectangle);
                k++;
                // canvas.drawRect(j*100,top,(j+1)*100,bottom,paint);
            }
            rectangles.add(list);
           // top = top + right;
            //bottom = bottom + right;
        }

       // canvas.drawRect(rectangle,paint);

       //canvas.drawRect(rectangle1, paint);
   }
    public void onCreate(){

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        //Toast.makeText(getContext(),"inside", Toast.LENGTH_LONG).show();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            {
                /*for(Rect rect:rectangles)
                {
                    if(rect.contains((int) x,(int) y)){
                        Toast.makeText(getContext(),"touched "+ x+ " touched "+ y, Toast.LENGTH_LONG).show();

                    }
                }*/
                /*for (int i = 0; i < rectangles.size(); i++)
                {
                    for (int j = 0; j < rectangles.get(i).size(); j++)
                    {
                        if(rectangles.get(i).get(j).contains((int) x,(int)y)){
                            Toast.makeText(getContext(),"touched ("+ i + ","+ j +")", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }*/


            }


        }



        return true;
    }
}
