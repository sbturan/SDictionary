package widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.storage.OnObbStateChangeListener;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.seckin.sdictionary.R;

import controller.UpdateWidgetService;

/**
 * Created by seckin on 7/13/2016.
 */
public class Widget extends AppWidgetProvider {
    public static String currentAnswer="";

    private static boolean answered=false;
        @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        // TODO Auto-generated method stub
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                Widget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        // Build the intent to call the service
        Intent intent = new Intent(context.getApplicationContext(),
                UpdateWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

        // Update the widgets via the service
        context.startService(intent);
    }

    private void colorAnswer(String answer, RemoteViews remoteViews){

        if(answer.trim().equals(UpdateWidgetService.ON_CLICK_ACTION)){
            remoteViews.setInt(R.id.buttonA, "setBackgroundColor",
                    Color.GREEN);
        }else
        if(answer.trim().equals(UpdateWidgetService.ON_CLICK_ACTION_2)){
            remoteViews.setInt(R.id.buttonB, "setBackgroundColor",
                    Color.GREEN);
        }else
        if(answer.trim().equals(UpdateWidgetService.ON_CLICK_ACTION_3)){
            remoteViews.setInt(R.id.buttonC, "setBackgroundColor",
                    Color.GREEN);
        }else
        if(answer.trim().equals(UpdateWidgetService.ON_CLICK_ACTION_4)){
            remoteViews.setInt(R.id.buttonD, "setBackgroundColor",
                    Color.GREEN);
        }

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);
        String answer=this.currentAnswer; //intent.getStringExtra("answer");
        if(answered){
            answered=false;
            UpdateWidgetService ser=new UpdateWidgetService();
            ser.resetQuestion(context);
            return;
        }else{
            answered=true;
        }
        if (intent.getAction().startsWith(UpdateWidgetService.ON_CLICK_ACTION)) {

            int widgetId = Integer.parseInt(intent.getAction().substring(UpdateWidgetService.ON_CLICK_ACTION.length()));
            if(answer.equals(UpdateWidgetService.ON_CLICK_ACTION)){
                remoteViews.setInt(R.id.buttonA, "setBackgroundColor",
                      Color.GREEN);
            }else{
                remoteViews.setInt(R.id.buttonA, "setBackgroundColor",
                        Color.RED);
                colorAnswer(answer,remoteViews);
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }else if(intent.getAction().startsWith(UpdateWidgetService.ON_CLICK_ACTION_2)){
            int widgetId = Integer.parseInt(intent.getAction().substring(UpdateWidgetService.ON_CLICK_ACTION_2.length()));
            if(answer.equals(UpdateWidgetService.ON_CLICK_ACTION_2)){
                remoteViews.setInt(R.id.buttonB, "setBackgroundColor",
                        Color.GREEN);
            }else{
                remoteViews.setInt(R.id.buttonB, "setBackgroundColor",
                        Color.RED);
                colorAnswer(answer,remoteViews);
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        else if(intent.getAction().startsWith(UpdateWidgetService.ON_CLICK_ACTION_3)){
            int widgetId = Integer.parseInt(intent.getAction().substring(UpdateWidgetService.ON_CLICK_ACTION_3.length()));
            if(answer.equals(UpdateWidgetService.ON_CLICK_ACTION_3)){
                remoteViews.setInt(R.id.buttonC, "setBackgroundColor",
                        Color.GREEN);
            }else{
                remoteViews.setInt(R.id.buttonC, "setBackgroundColor",
                        Color.RED);
                colorAnswer(answer,remoteViews);
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        else if(intent.getAction().startsWith(UpdateWidgetService.ON_CLICK_ACTION_4)){
            int widgetId = Integer.parseInt(intent.getAction().substring(UpdateWidgetService.ON_CLICK_ACTION_4.length()));
            if(answer.equals(UpdateWidgetService.ON_CLICK_ACTION_4)){
                remoteViews.setInt(R.id.buttonD, "setBackgroundColor",
                        Color.GREEN);
            }else{
                remoteViews.setInt(R.id.buttonD, "setBackgroundColor",
                        Color.RED);
                colorAnswer(answer,remoteViews);
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }

    }

}
