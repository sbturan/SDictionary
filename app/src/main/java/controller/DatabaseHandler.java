package controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.WordModel;

/**
 * Created by seckin on 7/4/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wordsDb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_WORDS = "words";
    private static final String NAME = "name";
    private static final String officalDefinition = "officalDefinition";
    private static final String unOfficalDefinition = "unOfficalDefinition";
    private static final String exampleSentences = "exampleSentences";
    private static final String searchCount = "searchCount";

    private static List<WordModel> listOfWords;
    private static ArrayList<String> listOfWordsNames;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
                + NAME + " TEXT PRIMARY KEY," + officalDefinition + " TEXT,"
                + unOfficalDefinition + " TEXT," + exampleSentences + " TEXT," + searchCount + " INTEGER )";
        db.execSQL(CREATE_WORDS_TABLE);
        ImportWords(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);

        // Create tables again
        onCreate(db);
    }

    public WordModel getWordByName(String name) {
        String quesry = "SELECT * FROM " + TABLE_WORDS + " WHERE " + NAME + "='" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(quesry, null);

        if (cursor.moveToFirst()) {
            WordModel model = new WordModel(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), Integer.valueOf(cursor.getString(4)));

            return model;
        } else return null;


    }

    public void addWord(WordModel word) {
        SQLiteDatabase db = this.getReadableDatabase();
        addWord(word, db);
        this.listOfWordsNames = null;
        this.listOfWords = null;
    }

    public void addWord(WordModel word, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(NAME, word.getName().substring(0, 1).toUpperCase() + word.getName().substring(1));
        values.put(officalDefinition, word.getOfficalDefinition());
        values.put(unOfficalDefinition, word.getUnOfficalDefinition());
        values.put(exampleSentences, word.getExampleSentences());
        values.put(searchCount, 0);
        db.insert(TABLE_WORDS, null, values);

    }


    public void increaseClickedCount(String wordName) {
        String query = "UPDATE " + TABLE_WORDS + " SET " + searchCount + "=" + searchCount + "+1 WHERE " + NAME + "='" + wordName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        this.listOfWordsNames = null;


    }

    public ArrayList<String> getAllWordsNameList() {

        if (listOfWordsNames == null) {
            listOfWordsNames = new ArrayList<>();
            String quesry = "SELECT " + NAME + " FROM " + TABLE_WORDS + " ORDER BY " + searchCount + " DESC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(quesry, null);
            if (cursor.moveToFirst()) {
                do {

                    listOfWordsNames.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

        }

        return listOfWordsNames;

    }

    public List<WordModel> getAllModels() {
        if (listOfWords == null) {
            listOfWords = new ArrayList<>();
            String quesry = "SELECT * FROM " + TABLE_WORDS + " ORDER BY " + searchCount + " DESC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(quesry, null);
            if (cursor.moveToFirst()) {
                do {
                    WordModel word = new WordModel(cursor.getString(0),
                            cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), Integer.valueOf(cursor.getString(4)));

                    // Adding contact to list
                    listOfWords.add(word);
                } while (cursor.moveToNext());
            }
        }
        return listOfWords;

    }

    public void ImportWords(SQLiteDatabase db) {


        WordModel wm;
        wm = new WordModel("border", "a strip that goes around or along the edge of something, often as decoration", "when 2 countries touch each other", " Germany border with Denmark", 0);
        addWord(wm, db);
        wm = new WordModel("suit", "a jacket and trousers or a jacket and skirt that are made from the same material"
                , "Something you like,something approprate", "People chane foods to suit their tastes", 0);
        addWord(wm, db);
        wm = new WordModel("ingredient", "one of the parts of something successful"
                , "parts of something", "The food is home cooked using fresh ingredients", 0);
        addWord(wm, db);
        wm = new WordModel("seasoning", "a substance, especially salt or pepper, that is added to food to improve its flavour"
                , "spices used in cooking to give flavur", "taste the sauce and adjust the seasoning", 0);
        addWord(wm, db);
        wm = new WordModel("appeal", "a request to the public for money, information, or help"
                , "something that attracts you", "Football has popular appeal", 0);
        addWord(wm, db);
        wm = new WordModel("order", "a request to make, supply, or deliver food or goods"
                , "ask for something / command", "Dont't have to take orders from you or anyone else", 0);
        addWord(wm, db);
        wm = new WordModel("recommend", "suggest,advice"
                , "", "I recommend that you buy a powerful computer", 0);
        addWord(wm, db);
        wm = new WordModel("accompany", "to go with someone or to be provided or exist at the same time as something"
                , "", "Chilren must be accompanied by an adult", 0);
        addWord(wm, db);
        wm = new WordModel("serving", " employed at the present time in a particular organization, especially the armed forces"
                , "the size of food you recive", "The bag contains six servings of pretzels", 0);
        addWord(wm, db);
        wm = new WordModel("stir", "to mix a liquid or other substance by moving an object such as a spoon in a circular pattern"
                , "", " Add the cream and stir thoroughly", 0);
        addWord(wm, db);
        wm = new WordModel("journey", "an occasion when you travel from one place to another"
                , "", "We had a long journey ahead of us", 0);
        addWord(wm, db);
        wm = new WordModel("raw", "that food has not been cooked, or has not been thoroughly cooked"
                , "", "The chicken is still raw", 0);
        addWord(wm, db);
        wm = new WordModel("suppose", " to think that something is likely to be true"
                , "used to show that you think something is true, although you wish that it were not", "Her new book is supposed to be " +
                "(= generally people think it is) very good/I suppose (that) all the tickets will be sold by now", 0);
        addWord(wm, db);
        wm = new WordModel("spread", "to (cause to) cover, reach, or have an effect on a wider or increasing area"
                , "go out", "Evidence suggested that the virus was spreading very quickly among the heterosexual community.", 0);
        addWord(wm, db);
        wm = new WordModel("conveyor", "a continuous moving strip or surface that is used for transporting objects from one place to another"
                , "", "Two types of machines are used, one in which the conveyor trough was straight and 45inc", 0);
        addWord(wm, db);
        wm = new WordModel("vinegar", "a sharp-tasting liquid, made especially from sour wine, malt, or cider, that is used" +
                " to add flavour to or to preserve food"
                , "", "Would you like oil and vinegar on your salad?", 0);
        addWord(wm, db);
        wm = new WordModel("misleading", "causing someone to believe something that is not true"
                , "", "Adverts must not create a misleading impression", 0);
        addWord(wm, db);
        wm = new WordModel("modest", "not large in size or amount, or not expensive"
                , "Person that does not like to talk about themselves, their achievements, or their abilities even if " +
                "they are successful", "Peter is genıinely modest about his achievements", 0);
        addWord(wm, db);
        wm = new WordModel("contradict", "of people) to say the opposite of what someone else has said, or (of one fact or statement)" +
                " to be so different from another fact or statement that one of them must be wrong"
                , "", "He did not dare contradict his parents", 0);
        addWord(wm, db);
        wm = new WordModel("Apostrophe", "the symbol ’ used in writing to show when a letter or a number has been left out," +
                " as in I'm (= I am) or '85 (= 1985), " +
                "or that is used before or after s to show possession, as in Helen's house or babies' hands:"
                , "", "", 0);
        addWord(wm, db);
        wm = new WordModel("mourn", "to feel or express great sadness, especially because of someone's death"
                , "", "She was still mourning for her brother", 0);
        addWord(wm, db);
        wm = new WordModel("leftovers", "the food that remains after a meal"
                , "", "some leftover chicken from last night's meal", 0);
        addWord(wm, db);
        wm = new WordModel("embarrassed", "feeling ashamed or shy"
                , "ashamed of something and worried about what people will think of you",
                "I have never felt so embarressed in my entire life", 0);
        addWord(wm, db);
        wm = new WordModel("faultline", "a line on a rock surface or the ground that traces a geological fault"
                , "",
                "The fault lines of imperfect peace deals are already showing", 0);
        addWord(wm, db);
        wm = new WordModel("strained", "If a relationship is strained, problems are spoiling it"
                , "not relaxed or friendly",
                "A good radio script should be able to engage the listener", 0);
        addWord(wm, db);
        wm = new WordModel("pastries", "a food made from a mixture of flour, fat, and water, rolled flat and either wrapped" +
                " around or put over or under other foods, and then baked"
                , "sweet bread like croissants",
                "Ann makes delicious pastry - you should try her apple pie.", 0);
        addWord(wm, db);
        wm = new WordModel("inevitable", "something you cannot ignore or avoid"
                , "",
                "War now seems almost inevitable", 0);
        addWord(wm, db);
        wm = new WordModel("savor", "enjoy something"
                , "",
                "He wanted to savor his time with Henrietta and their grown children", 0);
        addWord(wm, db);
        wm = new WordModel("items", "things,stuff"
                , "",
                "Several items of clothing (= clothes) lay on the floor", 0);
        addWord(wm, db);
        wm = new WordModel("grab", "take quickly"
                , "",
                "A mugger grabbed her handbag as she was walking across the park", 0);
        addWord(wm, db);
        wm = new WordModel("rushoff", "leave quickly"
                , "",
                " I'm sorry, but I will have to rush off from this meeting before it's over", 0);
        addWord(wm, db);
        wm = new WordModel("reflect", "If a surface reflects light, heat, sound, or an image, it sends the light, etc. back and does not absorb it"
                , "the same as anoter object",
                "He saw himself reflected in the water/mirror/shop window", 0);
        addWord(wm, db);
        wm = new WordModel("distinct", "clearly noticeable; that certainly exists"
                , "unique, one-of-a-kind",
                "They were classified into two distinct groups", 0);
        addWord(wm, db);
        wm = new WordModel("messy", "untidy"
                , "",
                "His bedroom's always messy", 0);
        addWord(wm, db);

        wm = new WordModel("dizzy", " feeling as if everything is turning around, and that you are not able to balance and may fall down"
                , "",
                "I felt dizzy with excitement as I went up to collect the award", 0);
        addWord(wm, db);
        wm = new WordModel("harpoon", "a long, heavy spear (= a long, sharp weapon) attached to a rope, used for killing large fish or whales"
                , "",
                "It is taken with the harpoon and its oil is one of the commercial products of the Amazon valley", 0);
        addWord(wm, db);

        wm = new WordModel("nest", "a structure built by birds or insects to leave their eggs in to develop, and by " +
                "some other animals to give birth or live in"
                , "",
                "Cuckoos are famous for laying their eggs in the nests of other birds", 0);
        addWord(wm, db);

        wm = new WordModel("awkward", "difficult to use, do, or deal with"
                , "moving in a way that is not natural, relaxed, or attractive",
                "It's an awkward corner, so take it slowly/His movements were slow and awkward", 0);
        addWord(wm, db);

        wm = new WordModel("damp", "slightly wet, especially in a way that is not pleasant or comfortable"
                , "",
                "The grass is still damp", 0);
        addWord(wm, db);

        wm = new WordModel("fog", "a weather condition in which very small drops of water come together" +
                " to form a thick cloud close to the land or seaocean, making it difficult to see"
                , "",
                "Mist, fog, and snow are common in this area", 0);
        addWord(wm, db);

        wm = new WordModel("spoil", "to destroy or reduce the pleasure, interest, or beauty of something"
                , "",
                "He tried not to let the bad news spoil his evening", 0);
        addWord(wm, db);

        wm = new WordModel("pledge", "a serious or formal promise, especially one to give money or to " +
                "be a friend, or something that you give as a sign that you will keep a promise"
                , "",
                "I give you this ring as a pledge of my everlasting love for you", 0);
        addWord(wm, db);

        wm = new WordModel("Kidnapping", "taking someone by force and detaining them for money"
                , "",
                "", 0);
        addWord(wm, db);
        wm = new WordModel("mugging", "attacking and robbing somebody"
                , "",
                "Police are concerned that mugging is on the increase", 0);
        addWord(wm, db);
        wm = new WordModel("arson", "the crime of intentionally starting a fire in order to damage or destroy something, especially a building"
                , "",
                "A cinema was burned out in north London last night. Police suspect arson", 0);
        addWord(wm, db);
        wm = new WordModel("assault", "a violent attack"
                , "attacking someone physically",
                "He was charged with sexual assault", 0);
        addWord(wm, db);

        wm = new WordModel("burglary", "the crime of illegally entering a building and stealing things"
                , "stealing from someone's house or business",
                "Several men were questioned by police yesterday about the burglary.", 0);
        addWord(wm, db);

        wm = new WordModel("suburbs", "an area on the edge of a large town or city where people who " +
                "work in the town or city often live"
                , "",
                "Box Hill is a suburb of Melbourne", 0);
        addWord(wm, db);
        wm = new WordModel("southpaw", "a person who uses their left hand to do most things"
                , "",
                "", 0);
        addWord(wm, db);
        wm = new WordModel("asterisk", "the symbol *"
                , "",
                "", 0);
        addWord(wm, db);
        wm = new WordModel("outlived", "to live or exist longer than someone or something"
                , "",
                "He outlived all of his brothers", 0);
        addWord(wm, db);
        wm = new WordModel("grave", "a place in the ground where a dead person is buried"
                , "serious",
                "He visits his mother's grave every Sunday", 0);
        addWord(wm, db);
        wm = new WordModel("rampant", "(of something bad) getting worse quickly and in an uncontrolled way"
                , "everywhere",
                "Disease is rampant in the overcrowded city", 0);
        addWord(wm, db);
        wm = new WordModel("sweat", "the clear, salty liquid that you pass through your skin"
                , "",
                "The dancers were dripping with/pouring with sweat after a morning's rehearsal", 0);
        addWord(wm, db);
        wm = new WordModel("efficacious", "effective"
                , "",
                "", 0);
        addWord(wm, db);

        wm = new WordModel("vowels", "a, e, i, o, and u"
                , "",
                "The vowels in English are a, e, i, o, and u", 0);
        addWord(wm, db);

        wm = new WordModel("consonants", "letters that are not vowels"
                , "b,c,d,f,g,....",
                "She's got some unpronounceable name that seems to be all consonants", 0);
        addWord(wm, db);
        wm = new WordModel("thrill", "a feeling of extreme excitement, usually caused by something pleasant"
                , "",
                "It gave me a real thrill to see her again after so many years", 0);
        addWord(wm, db);
        wm = new WordModel("beast", "an animal, especially a large or wild one"
                , "",
                "The room wasn't fit for man or beast", 0);
        addWord(wm, db);

        wm = new WordModel("malevolent", "causing or wanting to cause harm or evil"
                , "",
                "I could feel his malevolent gaze as I walked away", 0);
        addWord(wm, db);
        wm = new WordModel("myriad", "a very large number of something"
                , "",
                "", 0);
        addWord(wm, db);
        wm = new WordModel("anthropology", "the study of the human race, its culture and society, and " +
                "its physical development"
                , "",
                "", 0);
        addWord(wm, db);
        wm = new WordModel("anthropology", "a collection of artistic works that have a similar form or subject, often those" +
                " considered to be the best"
                , "Collection of poems,stories",
                "This Bob Dylan anthology includes some rare recordings of his best songs", 0);
        addWord(wm, db);
        wm = new WordModel("transgress", "to break a law or moral rule"
                , "to do someyhing wrong(crime)",
                "Those are the rules, and anyone who transgresses will be severely punished.", 0);
        addWord(wm, db);

        wm = new WordModel("excited", "feeling very happy and enthusiastic"
                , "",
                "Are you getting excited about your holiday?", 0);
        addWord(wm, db);


        wm = new WordModel("bounded", "certain or extremely likely to happen"
                , "",
                "You're bound to feel nervous about your interview", 0);
        addWord(wm, db);
        wm = new WordModel("informal", "(of situations) not formal or official, or (of clothing, " +
                "behaviour, speech) suitable when you are with friends and family but not for official occasions"
                , "",
                "He's the ideal sort of teacher - direct, friendly, and informal", 0);
        addWord(wm, db);
        wm = new WordModel("sentimental", "of or prompted by feelings of tenderness, sadness, or nostalgia"
                , "",
                "It's a cheap ring but it has great sentimental value for me.", 0);
        addWord(wm, db);
        wm = new WordModel("sedentary", " involving little exercise or physical activity"
                , "",
                "My doctor says I should start playing sport because my lifestyle is too sedentary", 0);
        addWord(wm, db);
        wm = new WordModel("shed", " a small building, usually made of wood, used for storing things"
                , "to get rid of, to throw away",
                "uk a garden/bicycle shed", 0);
        addWord(wm, db);

        wm = new WordModel("slurred", "to pronounce the sounds of a word in a way that is wrong or not clear"
                , "",
                "Her speech was slurred but she still denied she was drunk", 0);
        addWord(wm, db);

        wm = new WordModel("deprive", "to take something, especially something necessary or pleasant, away from someone"
                , "not getting, not received",
                "You can't function properly when you're deprived of sleep", 0);
        addWord(wm, db);

        wm = new WordModel("occurrence", "something that happens"
                , "",
                "Street-fights are an everyday occurrence in this area of the city", 0);
        addWord(wm, db);

        wm = new WordModel("excerpt", "a short part taken from a speech, book, film, etc."
                , "",
                "An excerpt from her new thriller will appear in this weekend's magazine", 0);
        addWord(wm, db);

        wm = new WordModel("superintendent", "a person who is in charge of work done in a particular " +
                "department, office, etc., or who is responsible for keeping a building or place in good condition"
                , "",
                "In the US, a school superintendent is in charge of the schools in a particular area.", 0);
        addWord(wm, db);

        wm = new WordModel("bachelor", "a man who has never married"
                , "",
                "He remained a bachelor until he was well into his 40s", 0);
        addWord(wm, db);

        wm = new WordModel("mortuary", "a building, or a room in a hospital, etc. where dead bodies are" +
                " kept before they are sent to be buried or burned, sometimes so that they can be examined"
                , "",
                "", 0);
        addWord(wm, db);

        wm = new WordModel("dough", " flour mixed with water and often yeast, fat, or sugar, so that it is ready for baking"
                , "",
                "She kneaded the dough and left it to rise", 0);
        addWord(wm, db);

        wm = new WordModel("bough", "a large branch of a tree"
                , "",
                "", 0);
        addWord(wm, db);

        wm = new WordModel("rough", "not even or smooth, often because of being in bad condition"
                , "",
                "It was a rough mountain road, full of stones and huge holes", 0);
        addWord(wm, db);

        wm = new WordModel("solid", " hard or firm, keeping a clear shape"
                , "hard to touch",
                "a solid object", 0);
        addWord(wm, db);

        wm = new WordModel("shrewd", "having or based on a clear understanding and good judgment of a situation, resulting in an advantage"
                , "nothing  more than exactly what you need",
                "It was a shrewd move to buy your house just before property prices started to rise", 0);
        addWord(wm, db);

        wm = new WordModel("gather", " to collect several things, often from different places or people"
                , "",
                "We gathered our things together and left quickly", 0);
        addWord(wm, db);

        wm = new WordModel("burst", "to break open or apart suddenly, or to make something do this"
                , "",
                "I hate it when balloons burst.", 0);
        addWord(wm, db);

        wm = new WordModel("scold", "to speak to someone angrily because you disapprove of their behaviour"
                , "",
                "His mother scolded him for breaking her favourite vase", 0);
        addWord(wm, db);

        wm = new WordModel("loon", "a large North American bird that eats fish"
                , "",
                "", 0);
        addWord(wm, db);
        wm = new WordModel("fortune", "a large amount of money, goods, property, etc"
                , "",
                "This dress cost a (small) fortune", 0);
        addWord(wm, db);
        wm = new WordModel("companionship", "the enjoyment of spending time with other people"
                , "",
                "I lived on my own for a while but I missed the companionship of others", 0);
        addWord(wm, db);
        wm = new WordModel("petite", "If a woman or girl is petite, she is small and thin in an attractive way"
                , "small and slender",
                "She was dark and petite, as all his wives had been", 0);
        addWord(wm, db);

        wm = new WordModel("dye", "to change the colour of something using a special liquid"
                , "",
                "For a change, why not dye your T-shirts?", 0);
        addWord(wm, db);


        wm = new WordModel("spouse", "a person's husband or wife"
                , "",
                "In 60 percent of the households surveyed both spouses went out to work", 0);
        addWord(wm, db);

        wm = new WordModel("chatterbox", "a person, especially a child, who talks a lot"
                , "",
                "Your sister's a real chatterbox!", 0);
        addWord(wm, db);

        wm = new WordModel("impulsive", "showing behaviour in which you do things suddenly without any planning and without considering the effects they may have"
                , "",
                "Don't be so impulsive - think before you act", 0);
        addWord(wm, db);

        wm = new WordModel("tactful", "careful not to say or do anything that could upset someone"
                , "",
                "Mentioning his baldness wasn't very tactful", 0);
        addWord(wm, db);

        wm = new WordModel("ambiguous", "having or expressing more than one possible meaning, sometimes intentionally"
                , "",
                "His reply to my question was somewhat ambiguous", 0);
        addWord(wm, db);

        wm = new WordModel("stereotype", "a set idea that people have about what someone or something is like, especially an idea that is wrong"
                , "",
                "He doesn't conform to/fit/fill the national stereotype of a Frenchman", 0);
        addWord(wm, db);

        wm = new WordModel("infant", "a baby or a very young child"
                , "",
                "a newborn infant", 0);
        addWord(wm, db);

        wm = new WordModel("self-assured", "having confidence in your own abilities"
                , "",
                "The interview showed her as a self-assured and mature student", 0);
        addWord(wm, db);

        wm = new WordModel("vengeful", "expressing a strong wish to punish someone who has harmed you or your family or friends"
                , "",
                "She sprayed red paint all over his car in one last vengeful act before leaving him for good", 0);
        addWord(wm, db);

        wm = new WordModel("spatial", "relating to the position, area, and size of things"
                , "",
                "This task is designed to test children's spatial awareness (= their understanding of where things are in relation to other things)", 0);
        addWord(wm, db);

        wm = new WordModel("put-down", "an unkind remark that makes someone seem silly"
                , "",
                "One of the big put-downs of the presidential election campaign was the comment that he was \"no Jack Kennedy\".", 0);
        addWord(wm, db);

        wm = new WordModel("adversary", "an enemy"
                , "",
                "He saw her as his main adversary within the company", 0);
        addWord(wm, db);

        wm = new WordModel("sarcasm", "the use of remarks that clearly mean the opposite of what they say, made in order to hurt someone's feelings or to criticize something in a humorous way"
                , "",
                "\"You have been working hard,\" he said with heavy sarcasm, as he looked at the empty page.", 0);
        addWord(wm, db);

        wm = new WordModel("harsh", "unpleasant, unkind, cruel, or more severe than is necessary"
                , "",
                "The children had had a harsh upbringing", 0);
        addWord(wm, db);

        wm = new WordModel("subtle", " not loud, bright, noticeable, or obvious in any way"
                , "small but important",
                "The room was painted a subtle shade of pink", 0);
        addWord(wm, db);

        wm = new WordModel("captive", "a person or animal whose ability to move or act freely is limited by being closed in a space; a prisoner, especially a person held by the enemy during a war"
                , "",
                "When the town was recaptured, we found soldiers who had been captives for several years", 0);
        addWord(wm, db);

        wm = new WordModel("intersect", " (of lines, roads, etc.) to cross one another"
                , "",
                "The roads intersect near the bridge", 0);
        addWord(wm, db);

        wm = new WordModel("rim", "the outer, often curved or circular, edge of something"
                , "",
                "The rim of the cup was chipped and broken", 0);
        addWord(wm, db);

        wm = new WordModel("self-conscious", "nervous or uncomfortable because you are worried about what people think about you or your actions"
                , "",
                "He looked uncomfortable, like a self-conscious adolescent", 0);
        addWord(wm, db);

        wm = new WordModel("convenient", "suitable for your purposes and causing no difficulty for your schedule or plans"
                , "",
                "Would 3 o’clock be a convenient time to meet?", 0);
        addWord(wm, db);

        wm = new WordModel("strength", "the ability to do things that demand physical effort, or the degree to which something is strong or powerful"
                , "",
                "Making baskets requires skill more than physical strength", 0);
        addWord(wm, db);

        wm = new WordModel("discourage", "to cause someone to feel less confident or less hopeful"
                , "",
                "She sometimes got discouraged about her social life, which was going nowhere, she felt", 0);
        addWord(wm, db);

        wm = new WordModel("settle", "to get or to become comfortable"
                , "",
                "He settled back in his chair and took out a book", 0);
        addWord(wm, db);

        wm = new WordModel("fist", "a hand with the fingers turned in tightly and the thumb held against them"
                , "",
                "She clenched her fist.", 0);
        addWord(wm, db);

        wm = new WordModel("egalitarian", "based on the idea that people are equally important and should have the same rights and opportunities"
                , "",
                "an egalitarian society.", 0);
        addWord(wm, db);

        wm = new WordModel("crumble", "to break into small pieces"
                , "",
                "That old wall is starting to crumble", 0);
        addWord(wm, db);

        wm = new WordModel("devastate", "to cause great damage or suffering to something or someone, or to violently destroy a place"
                , "",
                "I was so devastated I was crying constantly.", 0);
        addWord(wm, db);

        wm = new WordModel("feather", "one of the long, light objects that cover a bird’s body, having soft fibers along each side of a thin, stiff, central stem"
                , "",
                "", 0);
        addWord(wm, db);


        wm = new WordModel("shell", "the hard outer covering of nuts, eggs, a few vegetables, and some animals"
                , "",
                "Turtles, snails, and crabs all have shells to protect them", 0);
        addWord(wm, db);

        wm = new WordModel("judge", "a person who is in charge of a court of law"
                , "",
                "The judge dismissed the charge after a preliminary hearing", 0);
        addWord(wm, db);

        wm = new WordModel("wit", "the ability to use words in an amusing and intelligent way, or a person who has this ability"
                , "",
                "The warmth and wit of her literary style bring the subject to life", 0);
        addWord(wm, db);

        wm = new WordModel("brevity", "the use of few words"
                , "",
                "The essays were written with admirable brevity", 0);
        addWord(wm, db);

        wm = new WordModel("tie", "to fasten together two pieces of string or other long, thin material, or to hold together with string, rope, etc"
                , "",
                "This dress ties at the back", 0);
        addWord(wm, db);

        wm = new WordModel("toe", "any of the five long, thin parts at the end of the foot, similar to the fingers of the hand "
                , "",
                "I broke a toe when I caught my foot in a door", 0);
        addWord(wm, db);

        wm = new WordModel("robe", "a long, loose-fitting piece of clothing, esp. one worn at home"
                , "",
                "I had on pajamas and a robe", 0);
        addWord(wm, db);

        wm = new WordModel("sloppy", "messy or lacking care or attention"
                , "",
                "sloppy clothes", 0);
        addWord(wm, db);

        wm = new WordModel("virtually", " almost"
                , "",
                "Their twins are virtually identical.", 0);
        addWord(wm, db);
        wm = new WordModel("self-fulfilment", "a feeling of satisfaction that you have achieved what you wanted"
                , "",
                "When the options are unemployment or a boring job, having babies can seem like the only means of self-fulfilment", 0);
        addWord(wm, db);

        wm = new WordModel("viable", "able to exist, perform as intended, or succeed"
                , "",
                "The company had to seek other ways to remain viable", 0);
        addWord(wm, db);

        wm = new WordModel("influence", "to cause someone to change a behavior, belief, or opinion, or to cause something to be changed"
                , "",
                "She was influenced by the common-sense views of her grandparents", 0);
        addWord(wm, db);

        wm = new WordModel("inference", "a belief or opinion that you develop from the information that you know"
                , "",
                "", 0);
        addWord(wm, db);

        wm = new WordModel("stingy", "not generous, or unwilling to spend money"
                , "",
                "Some bankers are stingy in lending to small businesses", 0);
        addWord(wm, db);

        wm = new WordModel("intuition", "an ability to understand or know something without needing to think about it or use reason to discover it, or a feeling that shows this ability"
                , "",
                "You should trust your intuition in making your decision", 0);
        addWord(wm, db);

    }
}
