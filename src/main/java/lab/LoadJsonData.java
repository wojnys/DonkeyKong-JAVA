package lab;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoadJsonData {
    Image playerImage = new Image(getClass().getResourceAsStream("/lab/mario-cropped.gif"));
    Image kongImage = new Image(getClass().getResourceAsStream("/lab/kong.gif"));
    Image princessImage = new Image(getClass().getResourceAsStream("/lab/princess.gif"));
    Image enemyImage = new Image(getClass().getResourceAsStream("/lab/enemy-1.gif"));
    Image platformImage = new Image(getClass().getResourceAsStream("/lab/platform.png"));
    Image ladderImage = new Image(getClass().getResourceAsStream("/lab/ladder.png"));
    Image oilBarrelImage = new Image(getClass().getResourceAsStream("/lab/oilBarrel.gif"));
    Image hammerImage = new Image(getClass().getResourceAsStream("/lab/hammer.png"));

    private Game game;
    private final List<DrawableUpdatable> objects = new ArrayList<DrawableUpdatable>();

    public LoadJsonData(Game game) {
        this.game = game;
    }

    public List<DrawableUpdatable> loadLevel(int level) {
        String strJson = JsonParserHelper.getJSONFromFile("level" + level + ".json");
        try {

            // Parse the JSON string
            JSONParser parser = new JSONParser();
            JSONObject mainJsonObject = (JSONObject) parser.parse(strJson);

            // Iterate over all keys in the main object
            for (Map.Entry<String, JSONArray> entry : (Iterable<Map.Entry<String, JSONArray>>) mainJsonObject.entrySet()) {
                String objectType = entry.getKey();
                JSONArray jsonArray = entry.getValue();

                System.out.println(objectType + ":");
                // Iterate over objects in the array
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    System.out.println("  Object " + (i + 1));

                    DrawableUpdatable drawableUpdatable = this.createDrawableUpdatableObject(objectType, jsonObject);
                    if (drawableUpdatable != null) {
                        objects.add(drawableUpdatable);
                    }

                    // Print properties
                    for (Map.Entry<String, Object> propertyEntry : (Iterable<Map.Entry<String, Object>>) jsonObject.entrySet()) {
                        String propertyName = propertyEntry.getKey();
                        Object propertyValue = propertyEntry.getValue();
                        System.out.println("    " + propertyName + ": " + propertyValue);
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objects;
    }

    public DrawableUpdatable createDrawableUpdatableObject(String objectType, JSONObject jsonObject) {
        switch (objectType) {
            case "player":
                return this.createDrawableUpdatableForHumanObjects(jsonObject, Player.class, this.playerImage);
            case "princess":
                return this.createDrawableUpdatableForHumanObjects(jsonObject, Princess.class, this.princessImage);
            case "oilBarrel":
                return this.createDrawableUpdatableForItemObjects(jsonObject, OilBarrel.class, this.oilBarrelImage);
            case "kong":
                return this.createDrawableUpdatableForHumanObjects(jsonObject, Kong.class, this.kongImage);
            case "hammer":
                return this.createDrawableUpdatableForItemObjects(jsonObject, Hammer.class, this.hammerImage);
            case "platforms":
                return this.createDrawableUpdatableForItemObjects(jsonObject, Platform.class, this.platformImage);
            case "ladders":
                return this.createDrawableUpdatableForItemObjects(jsonObject, Ladder.class, this.ladderImage);
        }

        return null;
    }

    private DrawableUpdatable createDrawableUpdatableForHumanObjects(JSONObject jsonObject, Class<? extends DrawableUpdatable> clazz, Image image) {
        Point2D position = new Point2D(getDoubleFromJson(jsonObject, "x"), getDoubleFromJson(jsonObject, "y"));
        Point2D size = new Point2D(getDoubleFromJson(jsonObject, "width"), getDoubleFromJson(jsonObject, "height"));
        Point2D velocity = new Point2D(getDoubleFromJson(jsonObject, "velocityX"), getDoubleFromJson(jsonObject, "velocityY"));

        try {
            // Get the appropriate constructor based on the class type
            Constructor<? extends DrawableUpdatable> constructor = clazz.getConstructor(Game.class, Point2D.class, Point2D.class, Point2D.class, Image.class);

            // Create an instance using the constructor
            return constructor.newInstance(game, position, size, velocity, image);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            // Handle the exception, e.g., log an error
            e.printStackTrace();
            return null;
        }
    }

    private DrawableUpdatable createDrawableUpdatableForItemObjects(JSONObject jsonObject, Class<? extends DrawableUpdatable> clazz, Image image) {
        Point2D position = new Point2D(getDoubleFromJson(jsonObject, "x"), getDoubleFromJson(jsonObject, "y"));
        Point2D size = new Point2D(getDoubleFromJson(jsonObject, "width"), getDoubleFromJson(jsonObject, "height"));

        try {
            // Get the appropriate constructor based on the class type
            Constructor<? extends DrawableUpdatable> constructor = clazz.getConstructor(Point2D.class, Point2D.class, Image.class);

            // Create an instance using the constructor
            return constructor.newInstance(position, size, image);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            // Handle the exception, e.g., log an error
            e.printStackTrace();
            return null;
        }
    }

    public List<DrawableUpdatable> getHammers(List<DrawableUpdatable> objs) {
        return objs.stream()
                .filter(obj -> obj instanceof Hammer)
                .collect(Collectors.toList());
    }

    // Helper method to extract double values from JSON
    private double getDoubleFromJson(JSONObject jsonObject, String key) {
        try {
            return ((Number) jsonObject.get(key)).doubleValue();
        } catch (ClassCastException | NullPointerException e) {
            // Handle the case where the value is not present or is not a number
            // You might want to provide a default value or log an error.
            System.err.println("Error reading '" + key + "' from JSON: " + e.getMessage());
            return 0.0; // Provide a default value or handle the error accordingly
        }
    }
}
