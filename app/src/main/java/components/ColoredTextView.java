package components;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.seckin.sdictionary.SearchScreen;
import com.example.seckin.sdictionary.SingleWordScreen;

import org.w3c.dom.Text;

import controller.DatabaseHandler;

/**
 * Created by seckin on 7/5/2016.
 */
public class ColoredTextView extends TextView {
    private static int colorCounter=0;
    private static String[] colorCodes=new String[]{"#07bbdf","#cafff7","#262275","#17999b","#09cee4"};
    public ColoredTextView(Context context) {
        super(context);
        setStyle();

    }

    public ColoredTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStyle();

    }

    public ColoredTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setStyle();

    }

    private void setStyle(){
        this.setPadding(20,50,0,50);
        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        this.setTextColor(Color.parseColor("black"));
        this.setBackgroundColor(Color.parseColor(colorCodes[colorCounter]));
        colorCounter=(colorCounter+1)%5;
        this.setClickable(true);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db=new DatabaseHandler(getContext());
                TextView tv = (TextView) v;
                db.increaseClickedCount(tv.getText().toString().trim());
                Intent intent = new Intent(getContext(),SingleWordScreen.class);
                intent.putExtra("wordName",tv.getText().toString());
                getContext().startActivity(intent);
            }
        });
    }
}
