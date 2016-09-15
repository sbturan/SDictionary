package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.seckin.sdictionary.R;

import model.Question;
import model.WordModel;
import widget.Widget;

/**
 * Created by seckin on 7/14/2016.
 */
public class UpdateWidgetService  extends Service {
    private static final String LOG = "de.vogella.android.widget.example";
    public static String ON_CLICK_ACTION = "OnClickAction";
    public static String ON_CLICK_ACTION_2 = "2OnClickAction";
    public static String ON_CLICK_ACTION_3 = "3OnClickAction";
    public static String ON_CLICK_ACTION_4 = "4OnClickAction";
    static int[] allWidgetIds=new int[0];
    //public static String currentAnswer="";



    @Override
    public void onStart(Intent intent, int startId) {

        allWidgetIds = intent
                .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);


        createQuestion(this
                .getApplicationContext());

        super.onStart(intent, startId);
    }

    private  void  createQuestion(Context context) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//                ComponentName thisWidget = new ComponentName(getApplicationContext(),
//                                MyWidgetProvider.class);
//                int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {
            // create some random data



                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.widget_layout);
                remoteViews. setInt(R.id.buttonA, "setBackgroundColor",Color.TRANSPARENT);
                remoteViews.setInt(R.id.buttonB, "setBackgroundColor",
                        Color.TRANSPARENT);
                remoteViews.setInt(R.id.buttonC, "setBackgroundColor",
                        Color.TRANSPARENT);
                remoteViews.setInt(R.id.buttonD, "setBackgroundColor",
                        Color.TRANSPARENT);

                // Set the text

                Question question=getARandomQuestion(context);
                remoteViews.setTextViewText(R.id.question,
                        question.getQuestionText());
                remoteViews.setTextViewText(R.id.buttonA,question.getChoise1());
                remoteViews.setTextViewText(R.id.buttonB,question.getChoise2());
                remoteViews.setTextViewText(R.id.buttonC,question.getChoise3());
                remoteViews.setTextViewText(R.id.buttonD,question.getChoise4());


                // Register an onClickListener
                Intent clickIntent = new Intent(context,
                        Widget.class);
                clickIntent.setAction(ON_CLICK_ACTION+String.valueOf(widgetId));
                Intent clickIntent2 = new Intent(context,
                        Widget.class);
                clickIntent2.setAction(ON_CLICK_ACTION_2+String.valueOf(widgetId));
                Intent clickIntent3 = new Intent(context,
                        Widget.class);
                clickIntent3.setAction(ON_CLICK_ACTION_3+String.valueOf(widgetId));
                Intent clickIntent4 = new Intent(context,
                        Widget.class);
                clickIntent4.setAction(ON_CLICK_ACTION_4+String.valueOf(widgetId));




                    //clickIntent.removeExtra("answer");clickIntent2.removeExtra("answer");clickIntent3.removeExtra("answer");clickIntent4.removeExtra("answer");
                 if(question.getAnswer().equals(question.getChoise1())){
                     Widget.currentAnswer=ON_CLICK_ACTION;

                }else
                 if(question.getAnswer().equals(question.getChoise2())){
                     Widget.currentAnswer=ON_CLICK_ACTION_2;

                }else if(question.getAnswer().equals(question.getChoise3())){
                     Widget.currentAnswer=ON_CLICK_ACTION_3;


                }else if(question.getAnswer().equals(question.getChoise4())){
                     Widget.currentAnswer=ON_CLICK_ACTION_4;

                }

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, 0, clickIntent,0);
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(
                        context, 0, clickIntent2,0);
                PendingIntent pendingIntent3 = PendingIntent.getBroadcast(
                        context, 0, clickIntent3,0);
                PendingIntent pendingIntent4 = PendingIntent.getBroadcast(
                        context, 0, clickIntent4,0);
                remoteViews.setOnClickPendingIntent(R.id.buttonA, pendingIntent);
                remoteViews.setOnClickPendingIntent(R.id.buttonB, pendingIntent2);
                remoteViews.setOnClickPendingIntent(R.id.buttonC, pendingIntent3);
                remoteViews.setOnClickPendingIntent(R.id.buttonD, pendingIntent4);


                appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }
        stopSelf();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void resetQuestion(Context applicationContext){
    createQuestion(applicationContext);

    }

    private Question getARandomQuestion(Context context){

        DatabaseHandler db= new DatabaseHandler(context);
        List<WordModel> allModels = db.getAllModels();
        int size=allModels.size();
        Random rd= new Random();
        int current=rd.nextInt(size);
        WordModel wm=allModels.get(current);
        int choise2=rd.nextInt(size);
        int choise3=rd.nextInt(size);
        int choise4=rd.nextInt(size);

        while(choise2==current){
            choise2=rd.nextInt(size);
        }
        while(choise3==current||choise2==choise3){
            choise3=rd.nextInt(size);
        }
        while(choise4==current||choise4==choise3||choise2==choise4){
            choise4=rd.nextInt(size);
        }

        List<String> choises=new ArrayList<>();
        choises.add(allModels.get(current).getName());
        choises.add(allModels.get(choise2).getName());
        choises.add(allModels.get(choise3).getName());
        choises.add(allModels.get(choise4).getName());
        Collections.shuffle(choises);
        String questionText=allModels.get(current).getOfficalDefinition();
        questionText+=allModels.get(current).getUnOfficalDefinition().length()==0?"":" ("+allModels.get(current).getUnOfficalDefinition()+")";
        Question question=new Question(questionText,
                choises.get(0),
                choises.get(1),
                choises.get(2),
                choises.get(3),
                allModels.get(current).getName());

     return question;
    }
}
